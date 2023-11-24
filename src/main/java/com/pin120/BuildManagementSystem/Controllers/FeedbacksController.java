package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Models.Employee;
import com.pin120.BuildManagementSystem.Models.Feedback;
import com.pin120.BuildManagementSystem.Services.EmployeesService;
import com.pin120.BuildManagementSystem.Services.FeedbacksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/feedbacks")
@Controller
public class FeedbacksController {
    @Autowired
    private FeedbacksService feedbacksService;
    @Autowired
    private EmployeesService employeesService;

    @GetMapping("/main/{employeeId}")
    public String index(Model model, @PathVariable("employeeId") Long employeeId) {
        if (!employeesService.existsById(employeeId)) {
            return "redirect:/employees/main";
        }

        List<Feedback> feedbacks = feedbacksService.getEmployeeFeedbacks(employeeId);

        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("employeeId", employeeId);

        return "feedbacks/main";
    }

    @GetMapping("/new/{employeeId}")
    public String add(Model model, @PathVariable("employeeId") Long employeeId) {
        if (!employeesService.existsById(employeeId)) {
            return "redirect:/employees/main";
        }
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("employeeId", employeeId);
        return "feedbacks/new";
    }

    @PostMapping("/new/{employeeId}")
    public String add(@ModelAttribute @Valid Feedback feedback, BindingResult bindingResult, @RequestParam("employeeId") Long employeeId, Model model) {
        Optional<Employee> employee = employeesService.getOneById(employeeId);
        if (employee.isEmpty()) {
            return "redirect:/employees/main";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeId", employeeId);
            return "feedbacks/new";
        }

        feedback.setEmployee(employee.get());
        feedback.setAddDate(new Date(new java.util.Date().getTime()));

        if(feedback.getFeedbackText().equals("")){
            feedback.setFeedbackText(null);
        }

        feedbacksService.save(feedback);

        return "redirect:/feedbacks/main/" + employeeId;
    }

    @GetMapping("/delete/{feedbackId}/{employeeId}")
    public String delete(@PathVariable("feedbackId") Long feedbackId, @PathVariable("employeeId") Long employeeId) {
        if (!feedbacksService.existById(feedbackId) || !employeesService.existsById(employeeId)) {
            return "redirect:/main/employees";
        }

        feedbacksService.deleteById(feedbackId);

        return "redirect:/feedbacks/main/" + employeeId;
    }
}

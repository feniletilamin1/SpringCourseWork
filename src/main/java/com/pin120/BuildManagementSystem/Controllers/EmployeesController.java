package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Helpers.ImageHelper;
import com.pin120.BuildManagementSystem.Models.BuildObject;
import com.pin120.BuildManagementSystem.Models.Employee;
import com.pin120.BuildManagementSystem.Services.BuildObjectService;
import com.pin120.BuildManagementSystem.Services.EmployeesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/employees")
@Controller
public class EmployeesController {
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private BuildObjectService buildObjectService;
    @GetMapping("/main")
    public String index(Model model) {
        List<Employee> employees = employeesService.getAll();
        model.addAttribute("employees", employees);
        model.addAttribute("listCount", employees.size());
        model.addAttribute("freeEmployeesListCount", employeesService.freeEmployeesCount());
        model.addAttribute("busyEmployeesListCount", employeesService.busyEmployeesCount());
        return "employees/main";
    }

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("employee", new Employee());
        return "employees/new";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        Optional<Employee> employee =
                employeesService.getOneById(id);
        if (employee.isEmpty()) {
            return "redirect:/employees/main";
        }
        model.addAttribute("employee", employee.get());
        return "employees/edit";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Optional<Employee> employeeOptional =
                employeesService.getOneById(id);
        if (employeeOptional.isEmpty()) {
            return "redirect:/employees/main";
        }
        model.addAttribute("selectedEmployee", employeeOptional.get());
        return "employees/details";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute @Valid Employee employee, BindingResult bindingResult, @RequestParam MultipartFile image, @RequestParam String post) throws IOException, URISyntaxException {
        if(bindingResult.hasErrors()) {
            return "employees/new";
        }

        String fileName = ImageHelper.generateUniqName() + ".jpeg";
        ImageHelper.loadImage(fileName, "/employeesPhotos/", image);
        employee.setPhoto("/employeesPhotos/" + fileName);

        employee.setPost(post);
        employee.setStatus("Свободен");

        employeesService.save(employee);
        return "redirect:/employees/main";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute @Valid Employee employee, BindingResult bindingResult, @RequestParam Optional<MultipartFile> image, @RequestParam String post) throws IOException, URISyntaxException {
        if(bindingResult.hasErrors()) {
            return "employees/edit";
        }

        Optional<Employee> oldEmployee = employeesService.getOneById(employee.getId());

        if(oldEmployee.isEmpty()) {
            return "redirect:/employees/main";
        }

        if(image.get().getSize() != 0) {
            ImageHelper.deleteImage(oldEmployee.get().getPhoto());
            String fileName = ImageHelper.generateUniqName() + ".jpeg";
            ImageHelper.loadImage(fileName, "/employeesPhotos/", image.get());
            employee.setPhoto("/employeesPhotos/" + fileName);
        }
        else {
            employee.setPhoto(oldEmployee.get().getPhoto());
        }

        employee.setPost(post);
        employee.setStatus(oldEmployee.get().getStatus());
        employee.setFeedbacks(oldEmployee.get().getFeedbacks());
        employee.setBuildObjectsHistory(oldEmployee.get().getBuildObjectsHistory());

        if(oldEmployee.get().getBuildObject() != null) {
            employee.setBuildObject(oldEmployee.get().getBuildObject());
        }

        employeesService.save(employee);
        return "redirect:/employees/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) throws URISyntaxException {
        if (employeesService.existsById(id)) {
            Optional<Employee> employee = employeesService.getOneById(id);
            employeesService.deleteById(id);
            ImageHelper.deleteImage(employee.get().getPhoto());
        }
        return "redirect:/employees/main";
    }

    @GetMapping("/foremanObjects/{foremanId}")
    public String foremanObjects(Model model, @PathVariable("foremanId") Long foremanId) {
        Optional<Employee> foreman = employeesService.getOneById(foremanId);

        if(foreman.isEmpty() || !foreman.get().getPost().equals("Прораб")) {
            return "redirect:/employees/main";
        }

        List<BuildObject> buildObjects = employeesService.getForemanObjects(foremanId);

        model.addAttribute("buildObjects", buildObjects);

        return "employees/foremanHistory";
    }
}

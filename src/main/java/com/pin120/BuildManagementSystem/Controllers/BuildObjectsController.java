package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Models.BuildObject;
import com.pin120.BuildManagementSystem.Models.Client;
import com.pin120.BuildManagementSystem.Models.Employee;
import com.pin120.BuildManagementSystem.Services.BuildObjectService;
import com.pin120.BuildManagementSystem.Services.ClientsService;
import com.pin120.BuildManagementSystem.Services.EmployeesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/objects")
@Controller
public class BuildObjectsController {
    @Autowired
    private BuildObjectService buildObjectService;
    @Autowired
    private ClientsService clientsService;
    @Autowired
    private EmployeesService employeesService;

    @GetMapping("/main")
    public String index(Model model) {
        List<BuildObject> buildObjects = buildObjectService.getAll();
        model.addAttribute("buildObjects", buildObjects);
        model.addAttribute("listCount", buildObjects.size());
        model.addAttribute("finishedBuildObjectsCount", buildObjectService.finishedBuildObjectsCount());
        model.addAttribute("inWorkBuildObjectsCount", buildObjectService.inWorkBuildObjectsCount());
        return "objects/main";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("buildObject", new BuildObject());
        model.addAttribute("foremanList", employeesService.getForemanList());
        return "objects/new";
    }

    @PostMapping("/new")
    public String add(Model model, @ModelAttribute @Valid BuildObject buildObject, BindingResult bindingResult, @RequestParam Long clientId, @RequestParam Long foremanId) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientsService.getAll());
            model.addAttribute("foremanList", employeesService.getForemanList());
            return "objects/new";
        }

        Optional<Client> client = clientsService.getOneById(clientId);
        if (client.isEmpty()) {
            return "redirect:/objects/main";
        }

        Optional<Employee> employee = employeesService.getOneById(foremanId);
        if (employee.isEmpty()) {
            return "redirect:/objects/main";
        }

        buildObject.setClient(client.get());
        buildObject.setStatus("В работе");
        buildObject.setForemanHistory(employee.get());

        buildObject = buildObjectService.save(buildObject);

        employee.get().setStatus("Занят");
        employee.get().setBuildObject(buildObject);
        employeesService.save(employee.get());

        return "redirect:/objects/main";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute BuildObject buildObject) {
        Optional<BuildObject> oldBuildObject = buildObjectService.getOneById(buildObject.getId());
        if (oldBuildObject.isEmpty()) {
            return "redirect:/objects/main";
        }

        buildObject.setEmployees(oldBuildObject.get().getEmployees());
        buildObject.setObjectPhotos(oldBuildObject.get().getObjectPhotos());
        buildObject.setStatus(oldBuildObject.get().getStatus());
        buildObject.setObjectCategory(oldBuildObject.get().getObjectCategory());

        buildObjectService.save(buildObject);

        return "redirect:/objects/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<BuildObject> buildObject =
                buildObjectService.getOneById(id);
        if (buildObject.isEmpty()) {
            return "redirect:/employees/main";
        }

        model.addAttribute("buildObject", buildObject.get());
        return "objects/edit";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        Optional<BuildObject> buildObjectOptional =
                buildObjectService.getOneById(id);
        if (buildObjectOptional.isEmpty()) {
            return "redirect:/objects/main";
        }
        model.addAttribute("selectedBuildObject", buildObjectOptional.get());
        model.addAttribute("foreman", buildObjectService.getForemanObject(buildObjectOptional.get().getId()));
        return "objects/details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        if (buildObjectService.existsById(id)) {
            buildObjectService.deleteById(id);
        }
        return "redirect:/objects/main";
    }

    @GetMapping("/finish/{id}")
    public String finish(@PathVariable("id") Long id) {
        if (buildObjectService.existsById(id)) {
            BuildObject buildObject = buildObjectService.getOneById(id).get();
            buildObject.setStatus("Завершён");
            buildObjectService.setEmployeesFree(id);
            buildObjectService.save(buildObject);
        }
        return "redirect:/objects/main";
    }

    @GetMapping("/employees/{buildObjectId}")
    public String employeesList(Model model, @PathVariable("buildObjectId") Long buildObjectId) {
        Optional<BuildObject> buildObject = buildObjectService.getOneById(buildObjectId);
        if (buildObject.isEmpty()) {
            return "redirect:/objects/main";
        }

        List<Employee> employees = buildObjectService.getObjectEmployees(buildObjectId);
        model.addAttribute("employees", employees);
        model.addAttribute("status", buildObject.get().getStatus());
        model.addAttribute("buildObjectId", buildObjectId);
        return "objects/listEmployees";
    }

    @GetMapping("/addEmployee/{buildObjectId}")
    public String addEmployee(Model model, @PathVariable("buildObjectId") Long buildObjectId) {
        if (!buildObjectService.existsById(buildObjectId)) {
            return "redirect:/objects/main";
        }

        List<Employee> freeEmployees = employeesService.getFreeEmployees();
        model.addAttribute("employees", freeEmployees);
        model.addAttribute("buildObjectId", buildObjectId);
        return "objects/addEmployeeOnObject";
    }

    @GetMapping("addEmployee/{employeeId}/{buildObjectId}")
    public String addEmployee(@PathVariable("employeeId") Long employeeId, @PathVariable("buildObjectId") Long buildObjectId) {
        Optional<BuildObject> buildObject = buildObjectService.getOneById(buildObjectId);
        Optional<Employee> employee = employeesService.getOneById(employeeId);

        if(employee.isEmpty() || buildObject.isEmpty()) {
            return "redirect:/objects/main";
        }

        employee.get().setStatus("Занят");
        employee.get().setBuildObject(buildObject.get());
        employeesService.save(employee.get());

        return "redirect:/objects/employees/{buildObjectId}";
    }

    @GetMapping("deleteEmployeeFromObject/{employeeId}/{buildObjectId}")
    public String deleteEmployeeFromObject(@PathVariable("employeeId") Long employeeId, @PathVariable("buildObjectId") Long buildObjectId) {
        Optional<BuildObject> buildObject = buildObjectService.getOneById(buildObjectId);
        Optional<Employee> employee = employeesService.getOneById(employeeId);

        if(employee.isEmpty() || buildObject.isEmpty()) {
            return "redirect:/objects/main";
        }

        employee.get().setStatus("Свободен");
        employee.get().setBuildObject(null);
        employeesService.save(employee.get());

        return "redirect:/objects/employees/{buildObjectId}";
    }
}
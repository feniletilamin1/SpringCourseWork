package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Helpers.ImageHelper;
import com.pin120.BuildManagementSystem.Models.BuildObject;
import com.pin120.BuildManagementSystem.Models.Employee;
import com.pin120.BuildManagementSystem.Services.BuildObjectService;
import com.pin120.BuildManagementSystem.Services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("action", "add");
        return "employees/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        Optional<Employee> employee =
                employeesService.getOneById(id);
        if (employee.isEmpty()) {
            return "redirect:/employees/main";
        }
        model.addAttribute("action", "update");
        model.addAttribute("employee", employee.get());
        BuildObject buildObject = employee.get().getBuildObject();
        if(buildObject != null) {
            model.addAttribute("buildObjectId", buildObject.getId());
        }
        return "employees/form";
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
    public String add(@ModelAttribute Employee employee, @RequestParam Optional<MultipartFile> image, @RequestParam Optional<String> post, @RequestParam Optional<Long> buildObjectId) throws IOException, URISyntaxException {
        if(image.get().getSize() != 0) {
            String fileName = ImageHelper.generateUniqName() + ".jpeg";
            ImageHelper.loadImage(fileName, "/employeesPhotos/", image.get());
            employee.setPhoto("/employeesPhotos/" + fileName);
        }
        if(employee.getId() == null) {
            employee.setPost(post.get());
            employee.setStatus("Свободен");
        }
        else if(buildObjectId.isPresent()) {
            Optional<BuildObject> buildObject = buildObjectService.getOneById(buildObjectId.get());
            employee.setBuildObject(buildObject.get());
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
}

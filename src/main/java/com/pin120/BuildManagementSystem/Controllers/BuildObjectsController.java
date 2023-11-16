package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Helpers.DateHelper;
import com.pin120.BuildManagementSystem.Models.BuildObject;
import com.pin120.BuildManagementSystem.Models.Client;
import com.pin120.BuildManagementSystem.Models.Employee;
import com.pin120.BuildManagementSystem.Services.BuildObjectService;
import com.pin120.BuildManagementSystem.Services.ClientsService;
import com.pin120.BuildManagementSystem.Services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String add(Model model){
        model.addAttribute("clients", clientsService.getAll());
        model.addAttribute("buildObject", new BuildObject());
        model.addAttribute("action", "add");
        model.addAttribute("foremanList", employeesService.getForemanList());
        return "objects/form";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute BuildObject buildObject, @RequestParam Long clientId, @RequestParam String startDateWork,
                      @RequestParam String endDateWork, @RequestParam String category, @RequestParam Optional<Long> foremanId){
        Optional<Client> client = clientsService.getOneById(clientId);
        buildObject.setStartDate(DateHelper.stringToCalendar(startDateWork));
        buildObject.setEndDate(DateHelper.stringToCalendar(endDateWork));

        buildObject.setClient(client.get());
        buildObject.setObjectCategory(category);

        if(buildObject.getId() != null) {
            Optional<BuildObject> oldBuildObject = buildObjectService.getOneById(buildObject.getId());
            buildObject.setEmployees(oldBuildObject.get().getEmployees());
        }
        else {
            buildObject.setStatus("В работе");
        }

        buildObject = buildObjectService.save(buildObject);

        if(buildObject.getEmployees() == null) {
            Optional<Employee> employee = employeesService.getOneById(foremanId.get());
            employee.get().setStatus("Занят");
            employee.get().setBuildObject(buildObject);
            employeesService.save(employee.get());
        }
        return "redirect:/objects/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Optional<BuildObject> buildObject =
                buildObjectService.getOneById(id);
        if (buildObject.isEmpty()) {
            return "redirect:/employees/main";
        }
        model.addAttribute("action", "update");
        model.addAttribute("buildObject", buildObject.get());
        model.addAttribute("category", buildObject.get().getObjectCategory());
        model.addAttribute("clientId", buildObject.get().getClient().getId());
        model.addAttribute("startDateWork", DateHelper.calendarToString(buildObject.get().getStartDate()));
        model.addAttribute("endDateWork", DateHelper.calendarToString(buildObject.get().getEndDate()));
        return "objects/form";
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
}

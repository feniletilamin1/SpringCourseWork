package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Models.Client;
import com.pin120.BuildManagementSystem.Services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/clients")
@Controller
public class ClientsController {
    @Autowired
    private ClientsService clientsService;

    @GetMapping("/main")
    public String index(Model model) {
        List<Client> clients = clientsService.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("listCount", clients.size());
        return "clients/main";
    }

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("client", new Client());
        model.addAttribute("action", "add");
        return "clients/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        Optional<Client> client =
                clientsService.getOneById(id);
        if (client.isEmpty()) {
            return "redirect:/clients/main";
        }
        model.addAttribute("action", "update");
        model.addAttribute("client", client.get());
        return "clients/form";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Optional<Client> optionalClient =
                clientsService.getOneById(id);
        if (optionalClient.isEmpty()) {
            return "redirect:/clients/main";
        }
        model.addAttribute("selectedClient", optionalClient.get());
        return "clients/details";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute Client client){
        if(client.getId() != null) {
            Optional<Client> oldClient = clientsService.getOneById(client.getId());
            client.setOrders(oldClient.get().getOrders());
            client.setBuildObjects(oldClient.get().getBuildObjects());
        }
        clientsService.save(client);
        return "redirect:/clients/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        if (clientsService.existsById(id)) {
            clientsService.deleteById(id);
        }
        return "redirect:/clients/main";
    }
}

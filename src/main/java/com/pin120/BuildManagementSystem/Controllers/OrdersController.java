package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Models.Client;
import com.pin120.BuildManagementSystem.Models.Order;
import com.pin120.BuildManagementSystem.Services.ClientsService;
import com.pin120.BuildManagementSystem.Services.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequestMapping("/orders")
@Controller
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ClientsService clientsService;

    @GetMapping("/main")
    public String index(Model model) {
        List<Order> orders = ordersService.getAll();
        model.addAttribute("orders", orders);
        model.addAttribute("listCount", orders.size());
        return "orders/main";
    }

    @GetMapping("/new")
    public String add(Model model){
        List<Client> clients = clientsService.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("order", new Order());
        return "orders/new";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        Optional<Order> order =
                ordersService.getOneById(id);
        if (order.isEmpty()) {
            return "redirect:/orders/main";
        }
        Optional<Client> client = clientsService.getOneById(order.get().getClient().getId());
        model.addAttribute("order", order.get());
        model.addAttribute("clientId", client.get().getId());
        return "orders/edit";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Optional<Order> orderOptional =
                ordersService.getOneById(id);
        if (orderOptional.isEmpty()) {
            return "redirect:/orders/main";
        }
        model.addAttribute("selectedOrder", orderOptional.get());
        return "orders/details";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute @Valid Order order, BindingResult bindingResult, @RequestParam Long clientId, Model model){
        if(bindingResult.hasErrors()) {
            List<Client> clients = clientsService.getAll();
            model.addAttribute("clients", clients);
            return "orders/new";
        }

        order.setAddDate(new Date(new java.util.Date().getTime()));
        Optional<Client> client = clientsService.getOneById(clientId);
        if (client.isEmpty()) {
            return "redirect:/orders/main";
        }
        order.setClient(client.get());
        ordersService.save(order);
        return "redirect:/orders/main";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute @Valid Order order, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "orders/edit";
        }
        Optional<Order> oldOrder = ordersService.getOneById(order.getId());
        if (oldOrder.isEmpty()) {
            return "redirect:/orders/main";
        }
        order.setAddDate(oldOrder.get().getAddDate());
        order.setClient(oldOrder.get().getClient());
        ordersService.save(order);
        return "redirect:/orders/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        if (ordersService.existsById(id)) {
            ordersService.deleteById(id);
        }
        return "redirect:/orders/main";
    }
}

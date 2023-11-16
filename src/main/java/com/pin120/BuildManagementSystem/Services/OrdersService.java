package com.pin120.BuildManagementSystem.Services;

import com.pin120.BuildManagementSystem.Models.Order;
import com.pin120.BuildManagementSystem.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    @Autowired
    private OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAll(){
        return (List<Order>) orderRepository.findAll(); //реализовали метод внедренного бина
    }

    public Optional<Order> getOneById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }
}

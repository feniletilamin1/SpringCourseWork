package com.pin120.BuildManagementSystem.Services;

import com.pin120.BuildManagementSystem.Models.Employee;
import com.pin120.BuildManagementSystem.Repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {
    @Autowired
    private EmployeesRepository employeesRepository;

    public void save(Employee employee) {
        employeesRepository.save(employee);
    }

    public List<Employee> getAll(){
        return (List<Employee>) employeesRepository.findAll();
    }

    public Optional<Employee> getOneById(Long id) {
        return employeesRepository.findById(id);
    }

    public void deleteById(Long id) {
        employeesRepository.deleteById(id);
    }

    public Long freeEmployeesCount() {
        return employeesRepository.freeEmployeesCount();
    }
    public Long busyEmployeesCount() { return employeesRepository.busyEmployeesCount(); }
    public Boolean existsById(Long id) { return employeesRepository.existsById(id); }

    public List<Employee> getForemanList() {
        return employeesRepository.getForemanList();
    }
}

package com.pin120.BuildManagementSystem.Repositories;

import com.pin120.BuildManagementSystem.Models.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.status = 'Свободен'")
    Long freeEmployeesCount();

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.status = 'Занят'")
    Long busyEmployeesCount();

    @Query("SELECT e FROM Employee e WHERE e.post='Прораб' and e.status='Свободен'")
    List<Employee> getForemanList();

    @Query("SELECT e FROM Employee e WHERE e.post='Рабочий' and e.status='Свободен'")
    List<Employee> getFreeEmployees();
}

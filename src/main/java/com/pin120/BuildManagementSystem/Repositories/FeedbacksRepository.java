package com.pin120.BuildManagementSystem.Repositories;

import com.pin120.BuildManagementSystem.Models.Feedback;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbacksRepository extends CrudRepository<Feedback, Long> {
    @Query("SELECT f FROM Feedback f WHERE f.employee.id = :employeeId")
    List<Feedback> getEmployeeFeedbacks(Long employeeId);
}

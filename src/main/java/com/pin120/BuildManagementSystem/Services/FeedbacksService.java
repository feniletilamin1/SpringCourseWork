package com.pin120.BuildManagementSystem.Services;

import com.pin120.BuildManagementSystem.Models.Feedback;
import com.pin120.BuildManagementSystem.Repositories.FeedbacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbacksService {
    @Autowired
    private FeedbacksRepository feedbacksRepository;

    public List<Feedback> getEmployeeFeedbacks(Long employeeId){
        return feedbacksRepository.getEmployeeFeedbacks(employeeId);
    }

    public boolean existById(Long employeeId) {
        return feedbacksRepository.existsById(employeeId);
    }

    public void save(Feedback feedback) {
        feedbacksRepository.save(feedback);
    }

    public void deleteById(Long feedbackId) {
        feedbacksRepository.deleteById(feedbackId);
    }
}

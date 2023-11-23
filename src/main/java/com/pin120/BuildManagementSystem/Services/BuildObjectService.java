package com.pin120.BuildManagementSystem.Services;

import com.pin120.BuildManagementSystem.Models.BuildObject;
import com.pin120.BuildManagementSystem.Models.Employee;
import com.pin120.BuildManagementSystem.Repositories.BuildObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildObjectService {
    @Autowired
    private BuildObjectsRepository buildObjectsRepository;

    public BuildObject save(BuildObject buildObject){
        return buildObjectsRepository.save(buildObject);
    }

    public List<BuildObject> getAll(){
        return (List<BuildObject>) buildObjectsRepository.findAll();
    }

    public Optional<BuildObject> getOneById(Long id){
        return buildObjectsRepository.findById(id);
    }

    public void deleteById(Long id) {
        buildObjectsRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return buildObjectsRepository.existsById(id);
    }

    public Long finishedBuildObjectsCount() {
        return buildObjectsRepository.finishedBuildObjectsCount();
    }

    public Long inWorkBuildObjectsCount() {
        return buildObjectsRepository.inWorkBuildObjectsCount();
    }
    public Employee getForemanObject(Long buildObjectId) {
        return buildObjectsRepository.getForemanObject(buildObjectId);
    }
    public void setEmployeesFree(Long id) {
        buildObjectsRepository.setEmployeesFree(id);
    }

    public List<Employee> getObjectEmployees(Long buildObjectId) {
        return buildObjectsRepository.getObjectEmployees(buildObjectId);
    }
}

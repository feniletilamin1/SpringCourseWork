package com.pin120.BuildManagementSystem.Repositories;

import com.pin120.BuildManagementSystem.Models.BuildObject;
import com.pin120.BuildManagementSystem.Models.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildObjectsRepository extends CrudRepository<BuildObject, Long> {
    @Query("SELECT COUNT(b) FROM BuildObject b WHERE b.status = 'В работе'")
    Long inWorkBuildObjectsCount();

    @Query("SELECT COUNT(b) FROM BuildObject b WHERE b.status = 'Завершён'")
    Long finishedBuildObjectsCount();

    @Query("SELECT e FROM Employee e WHERE e.post = 'Прораб' and e.buildObject.id = :buildObjectId")
    Employee getForemanObject(Long buildObjectId);

    @Transactional
    @Modifying
    @Query("UPDATE Employee SET buildObject = null, status = 'Свободен' WHERE buildObject.id = :buildObjectId")
    void setEmployeesFree(Long buildObjectId);
}

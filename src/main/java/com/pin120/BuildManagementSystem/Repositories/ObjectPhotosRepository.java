package com.pin120.BuildManagementSystem.Repositories;

import com.pin120.BuildManagementSystem.Models.ObjectPhoto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectPhotosRepository extends CrudRepository<ObjectPhoto, Long> {
    @Query("SELECT p FROM ObjectPhoto p WHERE p.buildObject.id=:id")
    List<ObjectPhoto> getObjectPhotos(Long id);
}

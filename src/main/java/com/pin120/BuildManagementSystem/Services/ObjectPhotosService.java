package com.pin120.BuildManagementSystem.Services;

import com.pin120.BuildManagementSystem.Models.ObjectPhoto;
import com.pin120.BuildManagementSystem.Repositories.ObjectPhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectPhotosService {
    @Autowired
    private ObjectPhotosRepository objectPhotoRepository;

    public void save(ObjectPhoto objectPhoto) {
        objectPhotoRepository.save(objectPhoto);
    }
    public Optional<ObjectPhoto> findOneById(Long id) {
        return objectPhotoRepository.findById(id);
    }
    public List<ObjectPhoto> getObjectPhotos(Long buildObjectId){
        return (List<ObjectPhoto>) objectPhotoRepository.getObjectPhotos(buildObjectId);
    }
    public void deleteById(Long id) {
        objectPhotoRepository.deleteById(id);
    }
    public Boolean existsById(Long id) { return objectPhotoRepository.existsById(id); }
}

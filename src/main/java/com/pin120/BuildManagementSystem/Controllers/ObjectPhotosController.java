package com.pin120.BuildManagementSystem.Controllers;

import com.pin120.BuildManagementSystem.Helpers.ImageHelper;
import com.pin120.BuildManagementSystem.Models.BuildObject;
import com.pin120.BuildManagementSystem.Models.ObjectPhoto;
import com.pin120.BuildManagementSystem.Services.BuildObjectService;
import com.pin120.BuildManagementSystem.Services.ObjectPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequestMapping("/objects/photos")
@Controller
public class ObjectPhotosController {
    @Autowired
    private ObjectPhotosService objectPhotoService;
    @Autowired
    private BuildObjectService buildObjectService;
    @GetMapping("/main/{id}")
    public String index(Model model, @PathVariable("id") Long id) {
        List<ObjectPhoto> buildObjectsPhotos = objectPhotoService.getObjectPhotos(id);
        model.addAttribute("photos", buildObjectsPhotos);
        model.addAttribute("buildObjectId", id);
        return "objects/photos";
    }
    @GetMapping("/new/{buildObjectId}")
    public String add(Model model, @PathVariable("buildObjectId") Long buildObjectId){
        model.addAttribute("photo", new ObjectPhoto());
        if(!buildObjectService.existsById(buildObjectId)){
            return "redirect:/objects/main";
        }
        model.addAttribute("buildObjectId", buildObjectId);
        model.addAttribute("objectPhoto", new ObjectPhoto());
        return "objects/addPhoto";
    }

    @PostMapping("/new/{buildObjectId}")
    public String add(@ModelAttribute ObjectPhoto objectPhoto, @PathVariable("buildObjectId") Long buildObjectId, @RequestParam MultipartFile image) throws URISyntaxException, IOException {
        Optional<BuildObject> buildObject = buildObjectService.getOneById(buildObjectId);
        if(buildObject.isEmpty()) {
            return "redirect:/objects/main";
        }
        objectPhoto.setAddDate(Calendar.getInstance());
        objectPhoto.setBuildObject(buildObject.get());
        String fileName = ImageHelper.generateUniqName() + ".jpeg";
        ImageHelper.loadImage(fileName, "/objectPhotos/", image);
        objectPhoto.setPhotoPath("/objectPhotos/" + fileName);
        objectPhotoService.save(objectPhoto);
        return "redirect:/objects/photos/main/" + buildObjectId;
    }

    @GetMapping("/delete/{photoId}/{buildObjectId}")
    public String delete(@PathVariable("photoId") Long photoId, @PathVariable("buildObjectId") Long buildObjectId) throws URISyntaxException {
        if(!objectPhotoService.existsById(photoId) | !buildObjectService.existsById(buildObjectId)){
            return "redirect:/objects/main";
        }
        ObjectPhoto objectPhoto = objectPhotoService.findOneById(photoId).get();
        objectPhotoService.deleteById(photoId);
        ImageHelper.deleteImage(objectPhoto.getPhotoPath());
        return "redirect:/objects/photos/main/" + buildObjectId;
    }
}

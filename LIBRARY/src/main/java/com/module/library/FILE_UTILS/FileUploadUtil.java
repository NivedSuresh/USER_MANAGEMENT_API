package com.module.library.FILE_UTILS;

import com.module.library.EXCEPTIONS.InvalidStateException;
import com.module.library.MODELS.UserEntity;
import com.module.library.SERVICES.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
public class FileUploadUtil {

    private static final String IMAGE_UPLOAD_FOLDER = "/Users/apple/Desktop/USER_MANAGEMENT/USER_MANAGEMENT_ANGULAR/src/assets/pictures";
    UserService userService;

    public FileUploadUtil(UserService userService) {
        this.userService = userService;
    }

    public String uploadFile(MultipartFile file, Map<String, Object> claims){
        File uploadDir = new File(IMAGE_UPLOAD_FOLDER);
        if(!uploadDir.exists()) uploadDir.mkdirs();

        String fileName = null;

        try{
            String fileExtension = file.getName().substring(file.getName().lastIndexOf('.'));
            UserEntity user = userService.findByEmail((String)claims.get("sub"));
            fileName = user.getId().concat(fileExtension);
            Path destination = Path.of(IMAGE_UPLOAD_FOLDER, fileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            user.setPicture(fileName);
            userService.saveUser(user);
            return fileName;
        }catch (Exception e){
            e.printStackTrace();
            deleteImageFromFile(fileName);
            throw new InvalidStateException("Unable to upload Image, ".concat(e.getMessage()));
        }
    }

    public void deleteImageFromFile(String imagesUrl){
        File file = new File(IMAGE_UPLOAD_FOLDER+"/"+imagesUrl);
        file.delete();
    }

}

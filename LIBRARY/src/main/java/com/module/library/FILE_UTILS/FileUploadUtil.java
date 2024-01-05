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
import java.util.Objects;

@Service
public class FileUploadUtil {

    private static final String IMAGE_UPLOAD_FOLDER = "/Users/apple/Desktop/USER_MANAGEMENT/USER_MANAGEMENT_ANGULAR/src/assets/pictures";
    UserService userService;

    public FileUploadUtil(UserService userService) {
        this.userService = userService;
    }

    public UserEntity uploadFile(MultipartFile file, Map<String, Object> claims){
        File uploadDir = new File(IMAGE_UPLOAD_FOLDER);
        if(!uploadDir.exists()) uploadDir.mkdirs();

        String fileName = null;

        try{
            String fileExtension = getFileExtension(file);
            UserEntity user = userService.findByEmail((String)claims.get("sub"));
            fileName = user.getId().concat(fileExtension);

            Path destination = Path.of(IMAGE_UPLOAD_FOLDER, fileName);

            if(user.getPicture()!=null){
                Path existingFilePath = Path.of(IMAGE_UPLOAD_FOLDER, user.getPicture());
                if (Files.exists(existingFilePath)) Files.delete(existingFilePath);
            }

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            user.setPicture(fileName);

            return userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            deleteImageFromFile(fileName);
            throw new InvalidStateException("Unable to upload Image, ".concat(e.getMessage()));
        }
    }

    private static String getFileExtension(MultipartFile file) {
        String contentType = file.getContentType();
        if(!Objects.equals(contentType, "image/png") && !Objects.equals(contentType, "image/jpg")
                                                        && !Objects.equals(contentType, "image/jpeg"))
            throw new InvalidStateException("Invalid content type, cannot accept file of type "
                                            .concat(contentType!=null ? contentType : "empty"));

        return "."+contentType.substring(file.getContentType().lastIndexOf('/')+1);
    }

    public void deleteImageFromFile(String imagesUrl){
        File file = new File(IMAGE_UPLOAD_FOLDER+"/"+imagesUrl);
        file.delete();
    }

}

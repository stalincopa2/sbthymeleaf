package net.osgg.crudthymeleaf.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

@Service
public class PictureService {
	
	@Value("${upload.path}")
	public String uploadDir;

    public void uploadPicture(MultipartFile file, UUID id) {
      try {
    	 Path copyLocation = Paths.get(uploadDir + File.separator + id.toString()+".jpg");
    	  Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            
      } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                + ". Please try again!");
       }
    }
    
    public void deletePicture(UUID id) {
        try {
          Path fileToDeletePath = Paths.get(uploadDir + File.separator + id.toString()+".jpg");
      	  Files.deleteIfExists(fileToDeletePath);
        } catch (Exception e) {
              e.printStackTrace();
         }
     }
}

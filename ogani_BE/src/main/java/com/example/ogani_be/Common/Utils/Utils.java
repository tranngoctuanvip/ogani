package com.example.ogani_be.Common.Utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
@Component
public class Utils {
    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    public void isValid(MultipartFile image){
        String[] o = {".png",".JPEG","JPG","jpg"};
        boolean isvali = false;
        for (String img : o){
            if (image.getOriginalFilename().endsWith(img)){
                isvali = true;
            }
        }
        if (!isvali){
            throw new RuntimeException("Định dạng ảnh không đúng");
        }
    }
    @SneakyThrows
    public String imageName(MultipartFile image){
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if(!Files.exists(Utils.CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))){
            Files.createDirectories(Utils.CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = Utils.CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(Objects.requireNonNull(image.getOriginalFilename()));
        try(OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }
        String req = "http://localhost:8088/" + imagePath.resolve(image.getOriginalFilename());
        return req.replace("\\","/");
    }
}

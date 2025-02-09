package sportshop.web.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${upload.directory}")
    private String uploadDir;  // Set this in your application.properties

    public String saveFile(MultipartFile file) throws IOException {
        // Resolve the absolute path (relative to project directory)
        Path path = Paths.get(uploadDir, file.getOriginalFilename()).toAbsolutePath();
        
        // Ensure the directory exists, if not, create it
        Files.createDirectories(path.getParent());

        // Save the file in the directory
        file.transferTo(path);

        return path.toString();  // Return the full path to the uploaded file
    }

    public void deleteFile(String filePath) {
        // Delete the file based on the path
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}

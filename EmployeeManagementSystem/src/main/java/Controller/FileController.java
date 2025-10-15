package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.FileSizeExceededException;
import com.example.demo.exception.InvalidFileTypeException;
import com.example.demo.model.FileType;

@RestController
@RequestMapping("/files")
public class FileController {

    // Base upload folder (default: "Uploads" inside project folder)
    @Value("${file.upload-dir:Uploads}")//Property Injection
    private String uploadDir;

    //  Upload file
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //Upload Endpoint
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") FileType type) {

        Map<String, Object> response = new HashMap<>();

        //  Empty file check
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Please select a valid file.");
        }

        //  Extract file extension
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        }

        //  Validate file type using Enum
        //Validate extension
        if (!type.getAllowedExtensions().contains(fileExtension)) {
            throw new InvalidFileTypeException(
                    "Invalid file type for " + type.name() +
                    ". Allowed types: " + type.getAllowedExtensions());
        }

        // Validate size(1mb) 
        long maxSize = 1 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new FileSizeExceededException("File size exceeds 1MB limit.");
        }

        try {
            //  Create folders
            File baseFolder = new File(System.getProperty("user.dir") + File.separator + uploadDir);
            if (!baseFolder.exists()) baseFolder.mkdirs();

            File typeFolder = new File(baseFolder, type.name());
            if (!typeFolder.exists()) typeFolder.mkdirs();

            //  Save the file
            String filePath = typeFolder.getAbsolutePath() + File.separator + file.getOriginalFilename();
            try (FileOutputStream fout = new FileOutputStream(filePath)) {
                fout.write(file.getBytes());
            }
            
            
            //  Prepare response
            response.put("message", "File uploaded successfully!");
            response.put("fileName", file.getOriginalFilename());
            response.put("fileType", type.name());
            response.put("filePath", filePath);

            return ResponseEntity.ok(response);
            //Error handling
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading file: " + e.getMessage(), e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getFiles(
            @RequestParam(value = "type", required = false) String typeParam) {

        Map<String, Object> response = new HashMap<>();
        File baseFolder = new File(uploadDir);

        File folderToList;
        if (typeParam != null && !typeParam.isBlank()) {
            try {
                FileType type = FileType.valueOf(typeParam.toUpperCase());
                folderToList = new File(baseFolder, type.name());
            } catch (IllegalArgumentException e) {
                response.put("message", "Invalid type specified: " + typeParam);
                response.put("files", List.of());
                return ResponseEntity.badRequest().body(response);
            }
        } else {
            folderToList = baseFolder;
        }

        System.out.println("Checking folder: " + folderToList.getAbsolutePath());

        if (!folderToList.exists()) {
            response.put("message", "No such folder: " + folderToList.getName());
            response.put("files", List.of());
            return ResponseEntity.ok(response);
        }

        String[] filesArray = folderToList.list();
        if (filesArray == null || filesArray.length == 0) {
            response.put("message", "No files found in folder: " + folderToList.getName());
            response.put("files", List.of());
            return ResponseEntity.ok(response);
        }

        response.put("message", "Files retrieved successfully.");
        response.put("files", Arrays.asList(filesArray));
        return ResponseEntity.ok(response);
    }

    
    
}

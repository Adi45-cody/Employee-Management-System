package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    
    //update
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updateFile(
            @RequestParam("file") MultipartFile newFile,
            @RequestParam("type") FileType type,
            @RequestParam("oldFileName") String oldFileName) {
		
    	 Map<String, Object> response = new HashMap<>();

    	    File baseFolder = new File(System.getProperty("user.dir") + File.separator + uploadDir);
    	    File typeFolder = new File(baseFolder, type.name());

    	    File oldFile = new File(typeFolder, oldFileName);

    	    // Check existence
    	    if (!oldFile.exists()) {
    	        throw new RuntimeException("Old file not found: " + oldFileName);
    	    }

    	    // Validate extension
    	    String newFileName = newFile.getOriginalFilename();
    	    String newExt = newFileName.substring(newFileName.lastIndexOf(".") + 1).toLowerCase();

    	    if (!type.getAllowedExtensions().contains(newExt)) {
    	        throw new InvalidFileTypeException("Invalid file type for update. Allowed: " + type.getAllowedExtensions());
    	    }

    	    // Delete old and save new
    	    oldFile.delete();

    	    try (FileOutputStream fout = new FileOutputStream(new File(typeFolder, newFileName))) {
    	        fout.write(newFile.getBytes());
    	    } catch (IOException e) {
    	        throw new RuntimeException("Error while updating file: " + e.getMessage());
    	    }

    	    response.put("message", "File updated successfully!");
    	    response.put("oldFile", oldFileName);
    	    response.put("newFile", newFileName);
    	    response.put("type", type.name());

    	    return ResponseEntity.ok(response);
    	
    }
    
    
 // download file
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("type") FileType type,
            @RequestParam("fileName") String fileName) {

        // Define the file path (base + folder + filename)
        File baseFolder = new File(System.getProperty("user.dir") + File.separator + uploadDir);
        File typeFolder = new File(baseFolder, type.name());
        File file = new File(typeFolder, fileName);

        // Check if file exists
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("File not found: " + fileName);
        }

        // Create a resource from the file
        Resource resource = new FileSystemResource(file);

        // Build response with download headers
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

 // delete file
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteFile(
            @RequestParam("type") FileType type,
            @RequestParam("fileName") String fileName) {

        Map<String, Object> response = new HashMap<>();

        File file = new File(System.getProperty("user.dir") + File.separator + uploadDir
                + File.separator + type.name() + File.separator + fileName);

        if (!file.exists()) {
            response.put("message", "File not found: " + fileName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (file.delete()) {
            response.put("message", "File deleted successfully!");
            response.put("fileName", fileName);
            response.put("fileType", type.name());
        } else {
            response.put("message", "Failed to delete file: " + fileName);
        }

        return ResponseEntity.ok(response);
    }

    
}

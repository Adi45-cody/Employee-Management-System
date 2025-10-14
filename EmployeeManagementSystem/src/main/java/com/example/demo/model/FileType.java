package com.example.demo.model;

import java.util.List;

public enum FileType {

	RESUME(List.of("pdf","doc","docx")), //pdf format 
    ID_PROOF(List.of("pdf","jpg","png")), //Png or pdf
    CERTIFICATE(List.of("pdf","jpg","png")), //only in pdf
    PROFILE_PHOTO(List.of("jpg","jpeg","png")); //only in png
	
    private final List<String> allowedExtensions;

    FileType(List<String> allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }

    public List<String> getAllowedExtensions() {
        return allowedExtensions;
    }
}//end of class

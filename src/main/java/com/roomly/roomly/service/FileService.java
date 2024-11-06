package com.roomly.roomly.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface FileService {

    String accommodationMainFileUpload(MultipartFile file);
    String accommodationSubFileUpload(MultipartFile file);
    String roomSubFileUpload(MultipartFile file);
    String businessFileUpload(MultipartFile file);

    Resource getAccommodationMainFile(String fileName);
    Resource getAccommodationSubFile(String fileName);
    Resource getRoomSubFile(String fileName);
    Resource getBusinessFile(String fileName); 


    
}

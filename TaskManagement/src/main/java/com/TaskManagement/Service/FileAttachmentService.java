package com.TaskManagement.Service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagement.Entity.FileAttachment;
import com.TaskManagement.Repository.FileAttachmentRepository;
import com.cloudinary.Cloudinary;

@Service
public class FileAttachmentService {
	
	
	@Autowired
	private Cloudinary clodinary;
	
	
	@Autowired
	private FileAttachmentRepository fileRepo;
	
	
	public FileAttachment upload(Long issueId,MultipartFile file,String uploadedBy) {
		
		validateFile(file);   // Size < 5MB, allowed types only
		try {
			Map<String,Object> ulpoadOption= new HashMap<>();
			ulpoadOption.put("resource_type", "auto");
			
			Map uploadResult=clodinary.uploader().upload(file.getBytes(), ulpoadOption);
			
			
			// Extract Cloudinary response
			
			FileAttachment attach= new FileAttachment();
			attach.setIssueId(issueId);
			attach.setFileName(file.getOriginalFilename());
			attach.setContenType(file.getContentType());
			attach.setSizeBytes(file.getSize());
			attach.setStoragePath(uploadResult.get("secure_url").toString());
			//attach.setCloudId(uploadResult.get("cloud_id").toString());
			attach.setCloudId(uploadResult.get("public_id").toString());
			attach.setUploadBy(uploadedBy);
			
			return fileRepo.save(attach);
			
		} catch (Exception e) {
			throw new RuntimeException("cloud upload failed");
			
		}
	}
	
	
	public List<FileAttachment>getFileByIssueId(Long issueId){
		return fileRepo.findByIssueId(issueId);
	}
	
	public FileAttachment getFileById(Long id) {
		return fileRepo.findById(id).orElseThrow(()-> new RuntimeException("Attachment not found"));
	}
	
	public void deleteFile(Long id) {
		FileAttachment a= getFileById(id);
		
		try {
		
			Map<String,Object>options= new HashMap<>();
			options.put("resource_type", "auto");
			
			clodinary.uploader().destroy(a.getCloudId(), options);
			fileRepo.delete(a);
			
			
		} catch (Exception e) {
			throw new RuntimeException("delete failed");
		}
	}
	
	
	// file validation logic 
	private void validateFile(MultipartFile file) {
		
		if(file.isEmpty()) {
			throw new RuntimeException("file can not be empty");
		}
		
		long MAX= 5*1024*1024;  //5mb size 
		
		if(file.getSize()>MAX) {
			throw new RuntimeException("Max file size is 5MB");
		}
		
		List<String>allowedFile=Arrays.asList("image/png","image/jpeg","application/pdf","text/plain");
		
		if(!allowedFile.contains(file.getContentType())) {
			throw new RuntimeException("invalid file content");
		}
		
	}

}

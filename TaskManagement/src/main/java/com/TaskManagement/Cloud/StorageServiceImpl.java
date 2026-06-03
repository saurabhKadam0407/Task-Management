package com.TaskManagement.Cloud;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;



@Service
public class StorageServiceImpl implements StorageService {
	@Autowired
	private Cloudinary cloudinary;
	

	@Override
	public String store(MultipartFile file, String relativeFolder){
		
		
		try {
				Map upload= cloudinary.uploader().upload(file.getBytes(),ObjectUtils.asMap("folder",relativeFolder,"resource_type","auto"));
				 
				
				return (String) upload.get("secure_url");
		} catch (IOException e) {
				
				throw new RuntimeException("Failed to store file",e);
				
			}
		}
	
	@Override
	public byte[] read(String storagePath) {
			try {
				return Files.readAllBytes(Paths.get(storagePath));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
//	@Override
//	public void delete() {
//			try {
//				cloudinary.uploader().destroy( null, ObjectUtils.emptyMap());
//			} catch (Exception e) {
//				throw new RuntimeException("Cloudinary delete failed") ;
//			}
//		}

	@Override
	public void delete(String cloudId) {
		try {
			cloudinary.uploader().destroy( cloudId, ObjectUtils.emptyMap());
		} catch (Exception e) {
			throw new RuntimeException("Cloudinary delete failed") ;
		}
		
	}
	}


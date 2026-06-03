package com.TaskManagement.Controller;

import java.io.InputStream;

import java.net.URL;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagement.Entity.FileAttachment;
import com.TaskManagement.Service.FileAttachmentService;



@RestController
@RequestMapping("/api/attachments")
public class FileAttachmentController {
	
	
	@Autowired
	private FileAttachmentService attachmentService;
	
	
	@PostMapping("/upload/{issueId}")
	public ResponseEntity<FileAttachment>upload(@PathVariable("issueId") Long issueId,
			                                    @RequestParam("file") MultipartFile file,
			                                    @RequestParam("uploadedBy") String uploaedBy){
		
		return ResponseEntity.ok(attachmentService.upload(issueId, file, uploaedBy));
		
		
	}
	
	@GetMapping("/issue/{issueId}")
	public ResponseEntity<List<FileAttachment>>getFileByIssueId(@PathVariable Long issueId){
		
		return ResponseEntity.ok(attachmentService.getFileByIssueId(issueId));
		
		
	}
	
//	Cloud download
	@GetMapping("/download/{id}")
	public ResponseEntity<Void>download(@PathVariable Long id){
		FileAttachment attachments= attachmentService.getFileById(id);
		
		return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION,attachments.getStoragePath()).build();
	}
	
	
//	Device download
	@GetMapping("/download/stream/{id}")
	public ResponseEntity<Resource>stream(@PathVariable Long id) throws Exception{
		FileAttachment attachments= attachmentService.getFileById(id);
		
		URL url = new URL(attachments.getStoragePath());
		
		InputStream inputStream = url.openStream();
		
		InputStreamResource resource= new InputStreamResource(inputStream);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_LOCATION,
				                "attachments;fileName="+attachments.getFileName()+ "\"").
				                contentType(MediaType.parseMediaType(attachments.getContenType())).body(resource);
		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		attachmentService.deleteFile(id);
		return ResponseEntity.ok("File deleted successfully");
	}
}

package com.green.service.impl;

import java.io.File;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.green.service.UploadService;


@Service
public class UploadServiceImpl implements UploadService {

	@Autowired 
	ServletContext app;

	public File save(MultipartFile file, String folder) {
		File dir = new File(app.getRealPath("/assets/" + folder));
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String s = System.currentTimeMillis() + file.getOriginalFilename();
		String filename = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));

		try {
			File savedFile = new File(dir,filename);
			file.transferTo(savedFile);
			System.out.println("file directory: "+savedFile.getAbsolutePath());
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
//	@Override
//	public File save(MultipartFile file, String folder) {
//		File dir = Paths.get(app.getRealPath("/assets/"),folder).toFile();
//		if(!dir.exists()) {
//			dir.mkdirs();
//		}
//		String s = System.currentTimeMillis() + file.getOriginalFilename();
//		String filename = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
//
//		try {
//			File savedFile = new File(dir,filename);
//			file.transferTo(savedFile);
//			System.out.println("file directory: "+savedFile.getAbsolutePath());
//			return savedFile;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		
//	}

}

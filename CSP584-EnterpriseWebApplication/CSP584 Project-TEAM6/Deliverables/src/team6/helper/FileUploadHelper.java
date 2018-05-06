package team6.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import team6.servlet.ServletHotelManagement;
import team6.utils.StringUtilities;

public class FileUploadHelper {
    private final String MIME_PNG = "image/png";
    private final String MIME_JPG = "image/jpeg";
    private final String IMAGE_FILE_PATH = "resources/images/upload/";

	/**
	 * Retrieve a list of FileItem from POST request 
	 */
	public List<FileItem> parseMultipartRequest(HttpServletRequest request) {
		List<FileItem> listFileItem = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		factory.setSizeThreshold(1024 * 1024 * 10);  // 10MB
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1024 * 1024 * 5);         // 5MB
		
		try {
		    listFileItem = upload.parseRequest(request);
		}
		catch(FileUploadException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return listFileItem;
	}

	/**
	 * Do upload image. Return a list of image file name
	 * Uploaded image goes to ./resources/images/upload/hotel/[hotel-id]/ 
	 */
	public List<String> uploadHotelImage(ServletContext sc, List<FileItem> toBeUploaded, int hotelId) {
		if(toBeUploaded.size() == 0) {
			return null;
		}
		List<String> listFileName = new ArrayList<>();
		String realImageFilePath = sc.getRealPath(IMAGE_FILE_PATH) + "/hotel";
		Path path = Paths.get(realImageFilePath);
		try {
			path = path.resolve(String.valueOf(hotelId));
			if(Files.notExists(path)) {
				Files.createDirectory(path);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		for(FileItem fi: toBeUploaded) {
			String extension = null;
	        switch(fi.getContentType()) {
	            case MIME_JPG:
	            {
	                extension = ".jpg";
	                break;
	            }
	            case MIME_PNG:
	            {
	                extension = ".png";
	                break;
	            }
	        }
	        if(extension != null) {
	            String fileName = StringUtilities.INSTANCE.generateRandomString(10) + extension;
	            Path fiFilePath = path.resolve(fileName);	// full file path
	            while(Files.exists(fiFilePath)) {
	            	fileName = StringUtilities.INSTANCE.generateRandomString(10) + extension;
	            	fiFilePath = path.resolve(fileName);
	            }
	            listFileName.add(fileName);
	            // do upload file
	            try {
	                fi.write(fiFilePath.toFile());
	            }
	            catch(Exception e) {
	                e.printStackTrace();
	            }
	        }
		}
		
		return listFileName;
	}

	/**
	 * Do upload image. Return a list of image file name
	 * Uploaded image goes to ./resources/images/upload/room/[room-id]/ 
	 */
	public String uploadRoomImage(ServletContext sc, List<FileItem> toBeUploaded, Integer roomId) {
		if(toBeUploaded.size() == 0) {
			return null;
		}
		String fileName = null;
		String realImageFilePath = sc.getRealPath(IMAGE_FILE_PATH) + "/room";
		Path path = Paths.get(realImageFilePath);
		try {
			path = path.resolve(String.valueOf(roomId));
			if(Files.notExists(path)) {
				Files.createDirectory(path);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		for(FileItem fi: toBeUploaded) {
			String extension = null;
	        switch(fi.getContentType()) {
	            case MIME_JPG:
	            {
	                extension = ".jpg";
	                break;
	            }
	            case MIME_PNG:
	            {
	                extension = ".png";
	                break;
	            }
	        }
	        if(extension != null) {
	        	fileName = StringUtilities.INSTANCE.generateRandomString(10) + extension;
	            Path fiFilePath = path.resolve(fileName);	// full file path
	            while(Files.exists(fiFilePath)) {
	            	fileName = StringUtilities.INSTANCE.generateRandomString(10) + extension;
	            	fiFilePath = path.resolve(fileName);
	            }
	            // do upload file
	            try {
	                fi.write(fiFilePath.toFile());
	            }
	            catch(Exception e) {
	                e.printStackTrace();
	            }
	        }
		}
		
		return fileName;
	}

}

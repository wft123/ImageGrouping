package com.roxy.image;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

import com.roxy.model.EtcFileInfo;
import com.roxy.model.FileInfo;
import com.roxy.model.JpegFileInfo;
import com.roxy.model.TiffFileInfo;
import com.roxy.model.VideoFileInfo;

public class ImageGrouping {
	
	public void startGrouping(String source, String target) throws Exception {
		File dir = new File(source); 
		File[] fileList = dir.listFiles();
		for(File file : fileList) {
			if(file.isFile()){
				grouping(file, target);
			} else {
				startGrouping(file.getAbsolutePath(), target);
			}
		}
	}
	
	private void grouping(File file, String targetRoot) throws Exception {
		FileInfo fi = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat nameFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String[] fileType = new String[2];
		String probeContentType = Files.probeContentType(file.toPath());
		if(probeContentType != null)
			fileType = probeContentType.split("/");
		
		if("image".equalsIgnoreCase(fileType[0]) && "jpeg".equalsIgnoreCase(fileType[1])) {
			fi = new JpegFileInfo();
		} else if ("image".equalsIgnoreCase(fileType[0]) && "tiff".equalsIgnoreCase(fileType[1])) {
			fi = new TiffFileInfo();
		} else if ("video".equalsIgnoreCase(fileType[0])) {
			fi = new VideoFileInfo();
		} else {
			fi = new EtcFileInfo();
		}
		
		Date originalDate = fi.getFileOriginDate(file);
		
		String newFileName = nameFormat.format(originalDate) + "." + FilenameUtils.getExtension(file.getName());
		Path target = Paths.get(targetRoot, format.format(originalDate), newFileName);
		if(!target.getParent().toFile().exists()) target.getParent().toFile().mkdirs();
		
		//Files.move(path, Paths.get(target.toString(), path.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
		
		FileTime fromMillis = FileTime.fromMillis(originalDate.getTime());
		
		Files.setAttribute(target, "creationTime", fromMillis);
		Files.setAttribute(target, "lastModifiedTime", fromMillis);
		Files.setAttribute(target, "lastAccessTime", fromMillis);
		
		String msg = String.format("%s\t-->\t%s\tcopy \n", file.getName(), target); 
		System.out.printf(msg);
		GroupUI.logArea.append(msg);
	}
	
}

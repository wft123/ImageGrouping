package com.roxy.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.tiff.TiffMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class TiffFileInfo implements FileInfo {
	
	@Override
	public Date getFileOriginDate(File file) throws Exception {
		Metadata metadata = TiffMetadataReader.readMetadata(file);
		String date = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class).getObject(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL).toString();
		return new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").parse(date);
	}

}

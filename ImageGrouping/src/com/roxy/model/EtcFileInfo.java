package com.roxy.model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class EtcFileInfo implements FileInfo {

	@Override
	public Date getFileOriginDate(File file) throws Exception {
		BasicFileAttributes attrb = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		return new Date(attrb.lastModifiedTime().toMillis());
	}

}

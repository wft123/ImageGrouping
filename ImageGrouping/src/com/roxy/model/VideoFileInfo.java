package com.roxy.model;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.BodyContentHandler;

public class VideoFileInfo implements FileInfo {

	@Override
	public Date getFileOriginDate(File file) throws Exception {
		Metadata metadata = new Metadata();
		FileInputStream fis = new FileInputStream(file);
		new MP4Parser().parse(fis, new BodyContentHandler(), metadata, new ParseContext());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(metadata.get(TikaCoreProperties.CREATED)));
		fis.close();
		if(cal.getTimeInMillis() > 0) {
			cal.add(Calendar.HOUR, 9);
			return cal.getTime();
		} else {
			return new EtcFileInfo().getFileOriginDate(file);				
		}
	}

}

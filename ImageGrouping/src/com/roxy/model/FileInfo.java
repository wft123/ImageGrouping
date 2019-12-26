package com.roxy.model;

import java.io.File;
import java.util.Date;

public interface FileInfo {
	public Date getFileOriginDate(File file) throws Exception;
}

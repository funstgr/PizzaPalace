package com.pizzaPalace.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileSystemUtils;

public class FileSystemUtil extends FileSystemUtils {

	public static String getRealPath(HttpServletRequest request,
			String fileLocation) {

		String path = request.getServletContext().getRealPath(
				"/WEB-INF" + fileLocation);

		return path;
	}
}

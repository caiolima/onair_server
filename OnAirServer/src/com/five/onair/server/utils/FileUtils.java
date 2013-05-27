package com.five.onair.server.utils;

public class FileUtils {
	
	static{
		System.loadLibrary("AirNativeOperations");
	}

	public static native boolean copyDirectoryLikeRoot(String from,String to);
	public static native boolean deleteDirectoryWithRoot(String path);
	
}

package com.nickardson.bukkitlua;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

// Needs to be an instance because of getClass().
public class FileReader {
	/**
	 * Reads an entire file and returns the string.
	 * @param file
	 * The file location
	 * @return
	 * @throws IOException
	 */
	public static String readAll(String file) throws IOException {
		Scanner fileScanner = new Scanner(new File(file), "UTF-8");
		fileScanner.useDelimiter("\\A");
		
		if (fileScanner.hasNext()) {
			String next = fileScanner.next();
			fileScanner.close();
			return next;
		}
		else {
			
			fileScanner.close();
			return "";
		}
	}
	
	/**
	 * Saves a file that is in the classpath to an external location
	 * @param classPathLocation
	 * The local class path location of the file
	 * @param destination
	 * The location on the hard drive to save it to
	 */
	public void saveLocalFileTo(String classPathLocation, String destination) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(classPathLocation);
			OutputStream out = new FileOutputStream(new File(destination));
			
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while ((read = inputStream.read(bytes)) != -1) 
			{
				out.write(bytes, 0, read);
			}
			
			inputStream.close();
			out.flush();
			out.close();
	    } catch (IOException e) {
	    	System.out.println(ChatColor.RED + "Error: " + e.getLocalizedMessage());
	    }
	}
}

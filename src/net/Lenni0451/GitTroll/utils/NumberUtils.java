package net.Lenni0451.GitTroll.utils;

public class NumberUtils {
	
	public static boolean isInteger(final String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch (Exception e) {}
		return false;
	}
	
}

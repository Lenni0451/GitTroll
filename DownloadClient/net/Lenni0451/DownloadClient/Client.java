package net.Lenni0451.DownloadClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) throws Throwable {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the server ip: ");
		String serverIp = s.nextLine();
		System.out.print("Enter the FileDownloadServer port: ");
		int port = s.nextInt();
		s.nextLine();
		
		Socket socket = new Socket();
		socket.setTcpNoDelay(true);
		socket.connect(new InetSocketAddress(serverIp, port));
		
		InputStream is = socket.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while((length = is.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		is.close();
		socket.close();
		
		System.out.print("Enter the file name and path: ");
		String out = s.nextLine();
		Files.write(new File(out).toPath(), baos.toByteArray());
		
		s.close();
	}
	
}

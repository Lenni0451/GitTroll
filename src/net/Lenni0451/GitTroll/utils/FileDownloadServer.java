package net.Lenni0451.GitTroll.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class FileDownloadServer {
	
	private static final Random rnd = new Random();
	
	private int port;
	
	public FileDownloadServer(final File file) throws Throwable {
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.setSoTimeout(60000 * 2);
		
		serverSocket.bind(new InetSocketAddress(port = (rnd.nextInt(64535) + 1000)));
		
		new Thread(() -> {
			try {
				Socket socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				FileInputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int length;
				while((length = fis.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}
				fis.close();
				socket.close();
			} catch (Throwable e) {}
			try {
				serverSocket.close();
			} catch (Throwable e) {}
		}).start();
	}

	public int getPort() {
		return port;
	}
	
}

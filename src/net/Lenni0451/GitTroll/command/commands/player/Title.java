package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;
import java.util.regex.Pattern;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Title extends CommandBase {

	public Title() {
		super("Title", "Send a title to a player", "<Player> <Title message>(split with /)[Subtitle message]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(1)) {
			CustomPlayer player = this.parsePlayer(args.getString(0), executor);
			String title = args.getAsString(1).replace("&", "§").replace("§§", "&");
			String subtitle = "";
			if(title.split(Pattern.quote("/")).length == 2) {
				subtitle = title.split("/")[1];
				title = title.split("/")[0];
			}
			
			player.sendTitle(title, subtitle);
			executor.sendGitMessage("The title has been sent to the player.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			this.tabCompletePlayers(tabComplete);
		}
	}

}

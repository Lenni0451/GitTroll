package net.Lenni0451.GitTroll.command.commands.trolling;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventServerPacket;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateTime;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColorShift extends CommandBase implements EventListener {

    private List<CustomPlayer> players = new ArrayList<>();

    public ColorShift() {
        super("ColorShift", "Shift the color of a players minecraft", "<Player>");

        Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance().getParentPlugin(), () -> {
            try {
                for (CustomPlayer player : this.players) {
                    player.sendPacket(new PacketPlayOutGameStateChange(8, 40));
                    player.sendPacket(new PacketPlayOutGameStateChange(2, 1));
                }
            } catch (Throwable ignored) {}
        }, 1, 1);
    }

    @Override
    public void execute(CustomPlayer executor, ArrayHelper args) {
        if (args.isLength(1)) {
            CustomPlayer vic = this.parsePlayer(args.getString(0), executor);

            if (players.remove(vic)) {
                vic.sendPacket(new PacketPlayOutGameStateChange(8, 0));
                vic.sendPacket(new PacketPlayOutGameStateChange(1, 1));
                executor.sendGitMessage("§cThe players minecraft is no longer color shifted.");
            } else {
                this.players.add(vic);
                executor.sendGitMessage("The players minecraft is now color shifted.");
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

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventServerPacket) {
            if (this.players.contains(CustomPlayer.instanceOf(((EventServerPacket) event).getPlayer()))) {
                Packet<?> packet = ((EventServerPacket) event).getPacket();
                if (packet instanceof PacketPlayOutUpdateTime) {
                    try {
                        Field b = PacketPlayOutUpdateTime.class.getDeclaredField("b");
                        b.setAccessible(true);
                        b.set(packet, 4000);
                    } catch (Throwable ignored) {}
                }
            }
        }
    }
}

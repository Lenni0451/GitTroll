package net.Lenni0451.GitTroll.event.events;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.Lenni0451.GitTroll.event.types.Event;

public class EventPluginMessage implements Event {

	private final String channel;
	private final Player player;
	private final byte[] data;
	
	public EventPluginMessage(final String channel, final Player player, final byte[] data) {
		this.channel = channel;
		this.player = player;
		this.data = data;
	}
	
	public String getChannel() {
		return this.channel;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public byte[] getData() {
		return this.data;
	}
	
	public ByteArrayDataInput getDataStream() {
		ByteArrayDataInput in = ByteStreams.newDataInput(this.data);
		return in;
	}
	
}

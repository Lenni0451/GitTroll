package net.Lenni0451.GitTroll.event.events;

import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.event.types.CancellableEvent;
import net.minecraft.server.v1_8_R3.Packet;

public class EventPlayerPacket extends CancellableEvent {
	
	private final Player player;
	private final Packet<?> packet;
	
	public EventPlayerPacket(final Player player, final Packet<?> packet) {
		this.player = player;
		this.packet = packet;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Packet<?> getPacket() {
		return this.packet;
	}
	
}

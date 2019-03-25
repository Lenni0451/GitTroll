package net.Lenni0451.GitTroll.event.events;

import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.event.types.CancellableEvent;

public class EventUntrustPlayer extends CancellableEvent {
	
	private final Player player;
	
	public EventUntrustPlayer(final Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
}

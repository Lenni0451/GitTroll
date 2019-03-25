package net.Lenni0451.GitTroll.event.types;

public class CancellableEvent implements Event {
	
	private boolean cancelled = false;
	
	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public boolean isCancelled() {
		return this.cancelled;
	}
	
}

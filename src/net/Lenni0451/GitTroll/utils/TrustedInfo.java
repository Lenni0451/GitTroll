package net.Lenni0451.GitTroll.utils;

import java.util.UUID;

import org.bukkit.entity.Player;

public class TrustedInfo {
	
	private final String name;
	private final UUID uuid;
	private final String ipAddress;
	private final TrustLevel trustLevel;
	
	public TrustedInfo(final Player player, final TrustLevel trustLevel) {
		this(player.getName(), player.getUniqueId(), player.getAddress().getHostString(), trustLevel);
	}
	
	public TrustedInfo(final String serializedInfo) {
		String[] parts = serializedInfo.split("\0");
		
		this.name = parts[0];
		this.uuid = UUID.fromString(parts[1]);
		this.ipAddress = parts[2];
		this.trustLevel = TrustLevel.valueOf(parts[3]);
	}
	
	public TrustedInfo(final String name, final UUID uuid, final String ipAddress, final TrustLevel trustLevel) {
		this.name = name;
		this.uuid = uuid;
		this.ipAddress = ipAddress;
		this.trustLevel = trustLevel;
	}

	public final String getName() {
		return this.name;
	}

	public final UUID getUuid() {
		return this.uuid;
	}

	public final String getIpAddress() {
		return this.ipAddress;
	}
	
	public TrustLevel getTrustLevel() {
		return this.trustLevel;
	}
	
	public final String serialize() {
		return this.getName() + "\0" + this.getUuid() + "\0" + this.getIpAddress() + "\0" + this.trustLevel.toString();
	}

	
	public boolean isPlayer(final Player player) {
		try {
			return this.name.equals(player.getName()) && this.uuid.toString().equals(player.getUniqueId().toString()) && (player.getAddress() == null ? (true) : (this.ipAddress.equals(player.getAddress().getHostString())));
		} catch (Exception e) {}
		return false;
	}
	
}

package net.Lenni0451.GitTroll.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.event.events.EventPlayerPacket;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;

@SuppressWarnings("rawtypes")
public class PacketInjector extends MessageToMessageDecoder implements Listener {
	
	private final Player player;
	private final Channel channel;
	
	public PacketInjector(final Player player) {
		this.player = player;
		
		CraftPlayer craftPlayer = (CraftPlayer) player;
		NetworkManager networkManager = craftPlayer.getHandle().playerConnection.networkManager;
		this.channel = networkManager.channel;
		
		Bukkit.getPluginManager().registerEvents(this, GitTroll.getInstance());
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		if(event.getPlayer().equals(this.player)) {
			this.uninject();
		}
	}
	
	public void inject() {
		this.channel.pipeline().addAfter("decoder", "PacketInjection_" + this.player.getName(), this);
	}
	
	public void uninject() {
//		this.channel.pipeline().remove(this);
		PlayerQuitEvent.getHandlerList().unregister(this);
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, Object packet, List packetList) throws Exception {
		if(!(packet instanceof Packet)) {
			packetList.add(packet);
			return;
		}
		EventPlayerPacket event = new EventPlayerPacket(this.player, (Packet<?>) packet);
		GitTroll.getInstance().eventManager.callEvent(event);
		if(!event.isCancelled()) {
			packetList.add(packet);
		}
	}
	
}

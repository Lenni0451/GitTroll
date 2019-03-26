package net.Lenni0451.GitTroll.softdepends;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.event.events.EventPlayerPacket;
import net.Lenni0451.GitTroll.event.events.EventServerPacket;
import net.minecraft.server.v1_8_R3.Packet;

public class ProtocolLibPacketInjector {
	
	public ProtocolLibPacketInjector() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketSendHandler());
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketReceiveHandler());
	}
	
}

class PacketSendHandler extends PacketAdapter {

	@SuppressWarnings("deprecation")
	public PacketSendHandler() {
		super(GitTroll.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.ABILITIES,
				PacketType.Play.Server.ANIMATION,
				PacketType.Play.Server.ATTACH_ENTITY,
				PacketType.Play.Server.BED,
				PacketType.Play.Server.BLOCK_ACTION,
				PacketType.Play.Server.BLOCK_BREAK_ANIMATION,
				PacketType.Play.Server.BLOCK_CHANGE,
				PacketType.Play.Server.CAMERA,
				PacketType.Play.Server.CHAT,
				PacketType.Play.Server.CLOSE_WINDOW,
				PacketType.Play.Server.COLLECT,
				PacketType.Play.Server.COMBAT_EVENT,
				PacketType.Play.Server.CRAFT_PROGRESS_BAR,
				PacketType.Play.Server.CUSTOM_PAYLOAD,
				PacketType.Play.Server.ENTITY,
				PacketType.Play.Server.ENTITY_DESTROY,
				PacketType.Play.Server.ENTITY_EFFECT,
				PacketType.Play.Server.ENTITY_EQUIPMENT,
				PacketType.Play.Server.ENTITY_HEAD_ROTATION,
				PacketType.Play.Server.ENTITY_LOOK,
				PacketType.Play.Server.ENTITY_METADATA,
				PacketType.Play.Server.ENTITY_MOVE_LOOK,
				PacketType.Play.Server.ENTITY_STATUS,
				PacketType.Play.Server.ENTITY_TELEPORT,
				PacketType.Play.Server.ENTITY_VELOCITY,
				PacketType.Play.Server.EXPERIENCE,
				PacketType.Play.Server.EXPLOSION,
				PacketType.Play.Server.GAME_STATE_CHANGE,
				PacketType.Play.Server.HELD_ITEM_SLOT,
				PacketType.Play.Server.KICK_DISCONNECT,
				PacketType.Play.Server.KEEP_ALIVE,
				PacketType.Play.Server.LOGIN,
				PacketType.Play.Server.MAP,
				PacketType.Play.Server.MAP_CHUNK,
				PacketType.Play.Server.MAP_CHUNK_BULK,
				PacketType.Play.Server.MULTI_BLOCK_CHANGE,
				PacketType.Play.Server.NAMED_ENTITY_SPAWN,
				PacketType.Play.Server.NAMED_SOUND_EFFECT,
				PacketType.Play.Server.OPEN_SIGN_EDITOR,
				PacketType.Play.Server.OPEN_SIGN_ENTITY,
				PacketType.Play.Server.OPEN_WINDOW,
				PacketType.Play.Server.PLAYER_INFO,
				PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER,
				PacketType.Play.Server.POSITION,
				PacketType.Play.Server.REL_ENTITY_MOVE,
				PacketType.Play.Server.REL_ENTITY_MOVE_LOOK,
				PacketType.Play.Server.REMOVE_ENTITY_EFFECT,
				PacketType.Play.Server.RESOURCE_PACK_SEND,
				PacketType.Play.Server.RESPAWN,
				PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE,
				PacketType.Play.Server.SCOREBOARD_OBJECTIVE,
				PacketType.Play.Server.SCOREBOARD_SCORE,
				PacketType.Play.Server.SCOREBOARD_TEAM,
				PacketType.Play.Server.SERVER_DIFFICULTY,
				PacketType.Play.Server.SET_COMPRESSION,
				PacketType.Play.Server.SET_SLOT,
				PacketType.Play.Server.SPAWN_ENTITY,
				PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB,
				PacketType.Play.Server.SPAWN_ENTITY_LIVING,
				PacketType.Play.Server.SPAWN_ENTITY_PAINTING,
				PacketType.Play.Server.SPAWN_ENTITY_WEATHER,
				PacketType.Play.Server.SPAWN_POSITION,
				PacketType.Play.Server.STATISTIC,
				PacketType.Play.Server.STATISTICS,
				PacketType.Play.Server.TAB_COMPLETE,
				PacketType.Play.Server.TILE_ENTITY_DATA,
				PacketType.Play.Server.TITLE,
				PacketType.Play.Server.TRANSACTION,
				PacketType.Play.Server.UPDATE_ATTRIBUTES,
				PacketType.Play.Server.UPDATE_ENTITY_NBT,
				PacketType.Play.Server.UPDATE_HEALTH,
				PacketType.Play.Server.UPDATE_SIGN,
				PacketType.Play.Server.UPDATE_TIME,
				PacketType.Play.Server.WINDOW_DATA,
				PacketType.Play.Server.WINDOW_ITEMS,
				PacketType.Play.Server.WORLD_BORDER,
				PacketType.Play.Server.WORLD_EVENT,
				PacketType.Play.Server.WORLD_PARTICLES);
	}
	
	@Override
	public void onPacketSending(PacketEvent event) {
		EventServerPacket e = new EventServerPacket(event.getPlayer(), (Packet<?>) event.getPacket().getHandle());
		GitTroll.getInstance().eventManager.callEvent(e);
		event.setCancelled(e.isCancelled());
	}
	
}

class PacketReceiveHandler extends PacketAdapter {

	public PacketReceiveHandler() {
		super(GitTroll.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.ABILITIES,
				PacketType.Play.Client.ARM_ANIMATION,
				PacketType.Play.Client.BLOCK_DIG,
				PacketType.Play.Client.BLOCK_PLACE,
				PacketType.Play.Client.CHAT,
				PacketType.Play.Client.CLIENT_COMMAND,
				PacketType.Play.Client.CLOSE_WINDOW,
				PacketType.Play.Client.CUSTOM_PAYLOAD,
				PacketType.Play.Client.ENCHANT_ITEM,
				PacketType.Play.Client.ENTITY_ACTION,
				PacketType.Play.Client.FLYING,
				PacketType.Play.Client.HELD_ITEM_SLOT,
				PacketType.Play.Client.KEEP_ALIVE,
				PacketType.Play.Client.LOOK,
				PacketType.Play.Client.POSITION,
				PacketType.Play.Client.POSITION_LOOK,
				PacketType.Play.Client.RESOURCE_PACK_STATUS,
				PacketType.Play.Client.SET_CREATIVE_SLOT,
				PacketType.Play.Client.SETTINGS,
				PacketType.Play.Client.SPECTATE,
				PacketType.Play.Client.STEER_VEHICLE,
				PacketType.Play.Client.TAB_COMPLETE,
				PacketType.Play.Client.TRANSACTION,
				PacketType.Play.Client.UPDATE_SIGN,
				PacketType.Play.Client.USE_ENTITY,
				PacketType.Play.Client.WINDOW_CLICK);
	}
	
	@Override
	public void onPacketReceiving(PacketEvent event) {
		EventPlayerPacket e = new EventPlayerPacket(event.getPlayer(), (Packet<?>) event.getPacket().getHandle());
		GitTroll.getInstance().eventManager.callEvent(e);
		event.setCancelled(e.isCancelled());
	}
	
}

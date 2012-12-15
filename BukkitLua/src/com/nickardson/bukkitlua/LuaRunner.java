package com.nickardson.bukkitlua;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaRunner {
	/**
	 * This string will be prepended before every script to set up that script's globals.
	 */
	public static String core = "";
	
	public static void run(String content, CommandSender target, String[] args, Globals globals) {
		LuaScript script = new LuaScript(content, target, args, globals);
		RunnableScript runnableScript = new RunnableScript(script);
		
		script.thread = new Thread(runnableScript);
		script.thread.start();
	}
	
	public static void runString(String content, CommandSender target, String[] args) throws IOException {
		// TODO: Using the same globals is not thread-safe, maybe make something like addLine?
		//run(content, target, args, commandGlobals);
		
		run(content, target, args, LuaScript.getGlobals());
	}
	
	/**
	 * Runs a Lua script.
	 * @param file
	 * The file's location.
	 * @param target
	 * The sender, or null if no sender.
	 * @param args
	 * The arguments to pass to the script.
	 * @throws IOException 
	 */
	public static void runFile(String file, CommandSender target, String[] args) throws IOException {
		LuaRunner.run(FileReader.readAll(file), target, args, LuaScript.getGlobals());
	}
}

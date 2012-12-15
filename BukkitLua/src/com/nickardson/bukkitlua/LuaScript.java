package com.nickardson.bukkitlua;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.command.CommandSender;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaScript {
	/**
	 * The string to run.
	 */
	String source;
	
	/**
	 * The person who sent the code, either player or console.  It can be null to indicate no specific sender.
	 */
	CommandSender target;
	
	/**
	 * The args passed to the script.  These include the name of the script as it was called in the command.
	 */
	String[] args;
	
	/**
	 * The globals to use.  Pass a new LuaRunner.getGlobals() to start from scratch, existing globals will retain previously set values.
	 * This is useful for script building.
	 */
	Globals globals;
	
	Thread thread;
	
	public LuaScript(String content, CommandSender target, String[] args, Globals globals) {
		this.source = content;
		this.target = target;
		this.args = args;
		this.globals = globals;
	}
	
	public void run() {
		
	}
	
	public void resume() {
		thread.notify();
	}
	
	public void pause() {
		// TODO: Pause
		try {
			thread.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//throw new NotImplementedException();
	}
	
	public void stop() {
		// TODO: Stop
		
		throw new NotImplementedException();
	}
	
	/**
	 * Gets a fresh new set of Globals.
	 * @return
	 */
	public static Globals getGlobals() 
	{
		Globals _G = JsePlatform.standardGlobals();
		
		// Set anything that will always be in each global
		_G.set("loadstring", _G.get("load"));
		
		return _G;
	}
}

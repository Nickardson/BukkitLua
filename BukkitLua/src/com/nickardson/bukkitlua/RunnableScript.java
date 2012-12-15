package com.nickardson.bukkitlua;

import org.bukkit.ChatColor;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;

public class RunnableScript implements Runnable {
	public LuaScript script;
	public RunnableScript(LuaScript script){
		this.script = script;
	}
	
	@Override
	public void run() {
		LuaTable scriptInfo = LuaTable.tableOf();
		
		if (script.target != null) {
			scriptInfo.set("sender", script.target.getName());
			scriptInfo.set("senderIsPlayer", LuaValue.valueOf(script.target.getName() != "CONSOLE"));
		}
		else {
			scriptInfo.set("sender", LuaValue.NIL);
			scriptInfo.set("senderIsPlayer", LuaValue.FALSE);
		}
		
		script.globals.set("script", scriptInfo);
		
		try {
			script.globals.get("loadstring").call(LuaRunner.core).call();
			
			//_G.get("dofile").call( LuaValue.valueOf(file) );
			
			// Turn the given args into a Varargs so we can pass it through the loadstring.
			// Retrieved on the other end with (...)
			// {(...)}[1] gets the first arg.
			LuaValue[] argTable;
			
			if (script.args != null) {
				argTable = new LuaValue[script.args.length];
				
				for (int i = 0; i < script.args.length; i++) {
					argTable[i] = LuaValue.valueOf(script.args[i]);
				}
			}
			else {
				argTable = new LuaValue[0];
			}
			
			script.globals.get("loadstring").call(LuaValue.valueOf(script.source)).invoke(LuaValue.varargsOf(argTable));
		}
		catch (LuaError err) {
			if (script.target != null) {
				script.target.sendMessage(ChatColor.RED + "Error: " + err.getLocalizedMessage());
			}
		}
	}

}

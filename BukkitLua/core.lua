_G.Bukkit=luajava.bindClass("org.bukkit.Bukkit");
_G.World=Bukkit:getWorld("world");

-- Print is rewritten to fix a formatting bug caused by Bukkit.
local system = luajava.bindClass("java.lang.System")
_G["print"] = function(...)
	local out = ""
	local args = {...}
	
	for i = 1, #args do
		if (type(args[i]) == "function") then
			out = out .. "{function}"
		else
			out = out .. tostring(args[i]) .. "\t"
		end
	end
	
	system.out:println(out)
end

_G.sendMessage = function(receiver, message)
	if (receiver:upper() == "CONSOLE") then
		Bukkit:getConsoleSender():sendMessage(message)
	else
		local player = Bukkit:getPlayer(receiver)
		if (player ~= nil) then
			player:sendMessage(message)
		end
	end
end

local thread=luajava.bindClass("java.lang.Thread");
function _G.wait(t) t=(t or .03) t=math.max(t,.03) thread:currentThread():sleep(t*1000) end

function _G.setBlockId(x,y,z,id) World:getBlockAt(x,y,z):setTypeId(id) end
function _G.setBlockData(x,y,z,data) World:getBlockAt(x,y,z):setData(data) end
function _G.setBlock(x,y,z,id,data) local b=World:getBlockAt(x,y,z) b:setTypeId(id) b:setData(data) end
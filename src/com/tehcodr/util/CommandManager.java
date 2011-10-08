/**  
 * CommandManager.java
 * 
 * @section LICENSE
 * 
 * Permission is hereby granted, free of charge, to
 * any person obtaining a copy of this software and
 * associated documentation files (the "Software"),
 * to deal in the Software without restriction,
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package com.tehcodr.util;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.tehcodr.util.*;

/**
 * The handler for minecraft commands, loosely based off of sk89q's command system.
 * 
 * @author Omri Barak, TehCodr
 * @version 1.0
 *
 */
public final class CommandManager implements CommandExecutor {
	
	/**
	 * The manager has to be activated, so no commands can be managed until
	 * it is activated, for the sake of accidental initialization of multiple managers.
	 */
	protected boolean activated;
	
	/**
	 * The main plugin class. Since all plugin classes of Bukkit extends JavaPlugin,
	 * this should work with anything that implements or extends it.
	 */
	protected JavaPlugin plugin;

	/**
	 * The array that contains all of the commands.
	 */
	protected List<com.tehcodr.util.Command> commands;
	
	/**
	 * The number of commands in the array, 0 inclusive.
	 */
	protected int numCommands = 0;
	
	/**
	 * Initializes the CommandManager by setting the plugin parent.
	 * @param plugin the plugin that calls it. Usually referenced by this.
	 */
	public CommandManager(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Activates the program, by setting activated to true.
	 */
	public void activate() {
		activated = true;
	}
	
	/**
	 * Registers the command into the base.
	 * @param command
	 * @throws UnactivatedManagerException if the manager hasn't been activated yet.
	 */
	public void register(com.tehcodr.util.Command command) throws UnactivatedManagerException {
		if(commands.indexOf(command) == -1)
			commands.add(command);
		if(!activated) {
			throw new UnactivatedManagerException("Forgot to activate() manager");
		}
		if(activated) {
			for(int i=0; i < command.getNames().size(); i++) {
				plugin.getCommand(command.getName(i)).setExecutor(this);
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		for(int i=0; i < commands.size(); i++)
			for(int j=0; j < commands.get(i).getNames().size(); j++)
				if(commands.get(i).getName(j) == command.getName())
					commands.get(i).execute(sender, args);
		return false;
	}
	
}

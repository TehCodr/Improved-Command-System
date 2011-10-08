/**  
 * CommandRegisterCommands.java
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
import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.tehcodr.util.*;

/**
 * Registers the commands that failed registration, due to an un-activate()'ed CommandManager. This also serves as an example command.
 * 
 * @author Omri Barak, TehCodr
 * @version 1.0
 */
public final class CommandRegisterCommands extends Command {
	
	/**
	 * Initializes the command with the proper names, flags, and whatnot. 
	 * 
	 * @param parent the CommandManager parent.
	 */
	public CommandRegisterCommands(CommandManager parent) {
		super(parent, Arrays.asList("register","reg","registercommands","regcommands"), Arrays.asList(""), "Just call /registercommands",
				"Registers the commands, if they didn't get registered successfully before-hand", 0, 1 /* -h */);
	}

	@Override
	/**
	 * The function called when the command is executed.
	 * 
	 * @return true on success, false on failure.
	 */
	public boolean execute(CommandSender caller, String[] args) {
		if(!getParent().activated) {
			parent.activate();
			try {
				parent.register(this);
			} catch (UnactivatedManagerException e) {
				log.info("ERROR: CommandManager activate()'ion failed!");
				return false;
			}
		}
		if(args[0] != "h") {
			for(int i=0; i < getParent().commands.size(); i++) {
				try {
					parent.register(parent.commands.get(i));
				} catch (UnactivatedManagerException e) {
					log.info("ERROR: Failed activate()'ion of the CommandManager!");
					return false;
				}
				log.info("ERROR: CommandManager activate()'ion failed!");
			}
			return true;
		}
		else if(args[0] == "h"){
			printHelp();
			return true;
		}
		else {
			return false;
		}
	}
}

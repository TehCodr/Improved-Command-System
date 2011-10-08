/**  
 * Command.java
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
import java.util.logging.Logger;
import java.rmi.NoSuchObjectException;

import com.tehcodr.util.CommandManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The framework for minecraft commands by TehCodr, loosely based off of sk89q's command system.
 * 
 * @author Omri Barak, TehCodr
 * @version 1.0
 */
public abstract class Command {
	
	/**
	 * For the logging of exceptions.
	 */
	protected Logger log = Logger.getLogger("Minecraft");
	
	/**
	 * The CommandManager that events are registered to. 
	 */
	protected CommandManager parent;
	
	/**
	 * Possible names for the command - name[0] is the main name of the command.
	 * Names past names[0] are aliases.
	 */
	protected List<String> name;
	
	/**
	 * Possible flags for the command.
	 * When called, flags already have the dash behind them.
	 */
	protected List<String> flags;
	
	/**
	 * Text block for how to use a command.
	 * Format variables -
	 * \$c - Command name (has forward slash)
	 * \$f[0] || \$f - Flag name (Index number (Array) optional).
	 */
	protected String usage;
	
	/**
	 * A short description of the command.
	 */
	protected String description;
	
	/**
	 * Minimum number of flags when calling the command.
	 */
	protected int min;
	
	/**
	 * Maximum number of flags when calling the command. -1 means unlimited.
	 */
	protected int max;
	
	/**
	 * The person that sent the command.
	 */
	protected CommandSender caller;
	
	/**
	 * Constructor class
	 * 
	 * @param parent       The CommmandManager instance used to initialize the command.
	 * @param name         REQUIRED - Possible names for the command.
	 * @param flags        Possible flags for the command.
	 * @param usage        Text block for how to use a command.
	 * @param description  A short description of the command.
	 * @param min          Minimum number of flags when calling the command.
	 * @param max          Maximum number of flags when calling the command.
	 * @return true if name[0] is filled out, otherwise false.
	 * 
	 * @see #parent
	 * @see #name
	 * @see #flags
	 * @see #usage
	 * @see #description
	 * @see #min
	 * @see #max
	 */
	public Command(CommandManager parent, List<String> name, List<String> flags, String usage, String description, int min, int max) {
		assert(name.indexOf((String)"") == -1);
		setName(name);
		setFlags(flags);
		if(usage == "") usage = "Usage: $c <flags> \nAvailable Flags: $f[0 -1]";
		setUsage(usage);
		if (description == "") description = "/" + name.get(0);
		setDesc(description);
		setMinFlags(min);
		setMaxFlags(max);
		
		try {
			parent.register(this);
		}
		catch (UnactivatedManagerException e) {
			log.info("ERROR: CommandManager has not been activate()'ed yet!");
		}
	}
	
	/**
	 * Function to call when the slash command is typed.
	 * 
	 * @param caller person who called the command
	 * @param args the flags used
	 */
	public abstract boolean execute(CommandSender caller, String[] args);
	
	/**
	 * Gets the player that calls the given command
	 * @return caller
	 * @see #caller
	 */
	public final CommandSender getCaller() {
		return caller;
	}
	
	/**
	 * Gets the description string from the command.
	 * 
	 * @return description
	 * @see #description
	 */
	public final String getDescription() {
		return description;
	}
	
	/**
	 * Gets the description string from the command. An alternative name to {@link #getDescription()}
	 * 
	 * @return description
	 * @see getDescription()
	 * @see #description
	 */
	public final String getDesc() {
		return description;
	}
	
	/**
	 * Gets the name of the flag, at a certain point in the array.
	 * 
	 * @param i
	 * @return flags.get(i)
	 * @see #flags
	 */
	public final String getFlag(int i) {
		return flags.get(i);
	}
	
	/**
	 * Gets all of the flags in the list flags.
	 * 
	 * @return flags
	 * @see #flags
	 */
	public final List<String> getFlags() {
		return flags;
	}
	
	/**
	 * Gets the flags in an array, from a beginning point/ending point, all-inclusive.
	 * 
	 * @param index array item number
	 * @param first beginning index or ending index.
	 * @return flags.subList(...) list of flags, or all if something happens. 
	 * @see #flags
	 */
	public final List<String> getFlags(int index, boolean first) {
		if(first) {
			return flags.subList(index, flags.size() +1);
		}
		else if (!first) {
			return flags.subList(0, index + 1);
		}
		return flags;
	}
	
	/**
	 * Gets the names in an array, from a beginning point, to an end point.
	 * 
	 * @param beginIndex first item in the array index
	 * @param endIndex last item in the array index
	 * @return flags.subList(...) list of names starting from the beginIndex, and ending at the endIndex.
	 * @see #flags 
	 */
	public final List<String> getFlags(int beginIndex, int endIndex) {
		assert(flags.size() >= beginIndex && flags.size() >= endIndex && endIndex >= beginIndex);
		return flags.subList(beginIndex, endIndex);
	}
	
	/**
	 * Gets the logger for the game.
	 * @return log
	 * @see #log
	 */
	public final Logger getLogger() {
		return log;
	}
	
	/**
	 * Gets the maximum amount of callable flags per command.
	 * 
	 * @return maximum flags allowed
	 * @see #max
	 */
	public final int getMaxFlags() {
		return max;
	}
	
	/**
	 * Gets the minimum amount of callable flags per command.
	 * 
	 * @return minimum flags allowed
	 * @see #min
	 */
	public final int getMinFlags() {
		return min;
	}
	
	/**
	 * Gets the command's name.
	 * 
	 * @return the first item in the name list
	 * @see #name
	 */
	public final String getName() {
		return name.get(0);
	}
	
	/**
	 * Gets the name of the command, at a certain point in the array.
	 * 
	 * @param i index of the array (list).
	 * @return name
	 * @see #name
	 */
	public final String getName(int i) {
		return name.get(i);
	}
	
	/**
	 * Gets all of the names in the list name.
	 * 
	 * @return name
	 * @see #name
	 */
	public final List<String> getNames() {
		return name;
	}
	
	/**
	 * Gets the names in an array, from a beginning point/ending point, all-inclusive.
	 * 
	 * @param index array item number
	 * @param first beginning index or ending index.
	 * @return name.subList() list of names, or all if something happens.
	 * @see #name
	 */
	public final List<String> getNames(int index, boolean first) {
		if(first) {
			return name.subList(index, name.size() +1);
		}
		else if(!first) {
			return name.subList(0, index + 1);
		}
		return name;
	}
	
	/**
	 * Gets the names in an array, from a beginning point, to an end point.
	 * 
	 * @param beginIndex
	 * @param endIndex
	 * @return name.subList() list of names starting from the beginIndex, and ending at the endIndex.
	 * @see #name 
	 */
	public final List<String> getNames(int beginIndex, int endIndex) {
		assert(name.size() >= beginIndex && name.size() >= endIndex && endIndex >= beginIndex);
		return name.subList(beginIndex, endIndex);
	}

	/**
	 * Gets the CommandManager parent.
	 * 
	 * @return parent the CommandManager parent.
	 * @see #parent
	 */
	public final CommandManager getParent() {
		return parent;
	}
	
	/**
	 * Gets the parsed usage string.
	 * TODO: Make a function to retrieve an unparsed usage string.
	 * 
	 * @return usage the usage string.
	 * @see #usage
	 */
	public final String getUsage() {
		return usage;
	}
	
	/**
	 * Parses the usage string for commands.
	 * 
	 * @see #usage
	 */
	public final void parseUsageString() {
		/* Put flags list into an ordered list, separated by commas */
		String FlagList = "";
		for (int i=0; i < flags.size(); i++) {
			FlagList += flags.get(i).concat(", ");
		}
		usage.replaceAll("$f", FlagList);
		/* Flag index indexing */
		for(int i=0; i < flags.size(); i++) {
			usage.replaceAll("$f["+ i + "]", flags.get(i));
		}
		usage.replaceAll("$c", name.get(0));
	}
	
	/**
	 * 
	 */
	public final void printHelp() {
		caller.sendMessage(getDesc() + "\n" + getUsage());
	}
	
	/**
	 * Register the command, if the command didn't register in the initializer.
	 * 
	 * @param parent the CommandManager parent that's used to register the command.
	 */
	public final void register(CommandManager parent) {
		try {
			parent.register(this);
		}
		catch (UnactivatedManagerException e) {
			log.info("ERROR: CommandManager has not been activate()'ed yet!");
		}
	}
	
	/**
	 * Register the command, if the command didn't register in the initializer.
	 */
	public final void register() {
		try {
			parent.register(this);
		}
		catch (UnactivatedManagerException e) {
			log.info("ERROR: CommandManager has not been activate()'ed yet!");
		}
	}
	
	/**
	 * Sets the Player that calls any given command.
	 * 
	 * @param newCaller pretty self-explanatory.
	 * @see #caller
	 */
	private final void setCaller(CommandSender newCaller) {
		caller = newCaller;
	}
	
	/**
	 * Sets the command's description.
	 * 
	 * @param newDesc pretty self-explanatory.
	 * @see #description
	 */
	private final void setDescription(String newDesc) {
		description = newDesc;
	}
	
	private final void setDesc(String newDesc) {
		setDescription(newDesc);
	}
	
	/**
	 * Sets the command's flags.
	 * 
	 * @param newFlags pretty self-explanatory.
	 * @see #flags
	 */
	private final void setFlags(List<String> newFlags) {
		flags = newFlags;
	}
	
	/**
	 * Sets the logger for the game.
	 * 
	 * @param newLog
	 * @see #log
	 */
	private final void setLogger(Logger newLog) {
		log = newLog;
	}
	
	/**
	 * Sets the maximum allowed callable flags.
	 * 
	 * @param newMax pretty self-explanatory.
	 * @see #max
	 */
	private final void setMaxFlags(int newMax) {
		max = newMax;
	}
	
	/**
	 * Sets the minimum allowed callable flags.
	 * 
	 * @param newMin pretty self-explanatory.
	 * @see #min
	 */
	private final void setMinFlags(int newMin) {
		min = newMin;
	}
	
	/**
	 * Sets the command's name.
	 * 
	 * @param newName pretty self-explanatory.
	 * @see #name
	 */
	private final void setName(List<String> newName) {
		assert(newName.indexOf((String)"") == -1);
		name = newName;
	}
	
	/**
	 * Sets the CommandManager parent.
	 * 
	 * @param newParent pretty self-explanatory.
	 * @see #parent
	 */
	private final void setParent(CommandManager newParent) {
		parent = newParent;
	}
	
	/**
	 * Sets the command's usage string.
	 * 
	 * @param newUsage pretty self-explanatory.
	 * @see #usage
	 */
	private final void setUsage(String newUsage) {
		usage = newUsage;
		parseUsageString();
	}
}

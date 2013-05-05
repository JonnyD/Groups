package groups.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;

public class CreateGroupCommand extends PlayerCommand {

	public CreateGroupCommand() {
		super("Create Group");
		setDescription("Creates a group");
		setUsage("/gcreate <name>");
		setArgumentRange(1,1);
		setIdentifier("gcreate");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		String name = args[0];
		
		int minNameLength = 3;
		int maxNameLength = 16;
		boolean greaterThanEqualMin = name.length() >= minNameLength;
		boolean lessThanEqualMax = name.length() <= maxNameLength;
		if(!greaterThanEqualMin || !lessThanEqualMax) {
			sender.sendMessage("Name can't be less than " + minNameLength +
					" characters or greater than " + maxNameLength + 
					" characters");
			return true;
		}
		
		Group group = groupManager.getGroupByName(name);
		if(group != null) {
			sender.sendMessage("Group already exists");
			return true;
		}
		
		groupManager.createGroup(name, sender.getName());
		return true;
	}

}

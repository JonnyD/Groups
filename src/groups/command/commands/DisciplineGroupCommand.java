package groups.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Group.GroupStatus;

public class DisciplineGroupCommand extends PlayerCommand {

	public DisciplineGroupCommand() {
		super("Discipline Group");
		setDescription("Disciplines a group");
		setUsage("/ctdiscipline §8 [del] <group-name>");
		setArgumentRange(1,2);
		setIdentifier("ctadg");
	}

	public boolean execute(CommandSender sender, String[] args) {
		String groupName = null;
		boolean deleteGroup = false;
		
		if (args[0].equalsIgnoreCase("del")) {
			deleteGroup = true;
			groupName = args[1];
		} else {
			groupName = args[0];
		}
		
		Group group = groupManager.getGroupByName(groupName);
		if (group == null) {
			//sendMessage(sender, ChatColor.RED, "Group doesn't exist");
			return true;
		}
		
		GroupStatus status = group.getStatus();
        if (status == GroupStatus.DELETED) {
			//sendMessage(sender, ChatColor.YELLOW, "Group already deleted");
			return true;
        }
        
        if (status == GroupStatus.DISABLED) {
            if (deleteGroup) {
			    group.setStatus(GroupStatus.ENABLED);
            } else {
			    //sendMessage(sender, ChatColor.YELLOW, "Group already disabled");
			    return true;
            }
        }
        
		if (deleteGroup) {
			group.setStatus(GroupStatus.DELETED);
			//sendMessage(sender, ChatColor.GREEN, "Group %s is deleted", groupName);
		} else {
			group.setStatus(GroupStatus.DISABLED);
			//sendMessage(sender, ChatColor.GREEN, "Group %s is disabled", groupName);
		}
		
        groupManager.addGroup(group);
		return true;
	}
}

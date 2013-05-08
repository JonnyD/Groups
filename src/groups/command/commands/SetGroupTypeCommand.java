package groups.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Group.GroupType;
import groups.model.GroupMember;
import groups.model.GroupMember.Role;

public class SetGroupTypeCommand extends PlayerCommand {

	public SetGroupTypeCommand() {
		super("Set Group Type");
		setDescription("Set the groups type");
		setUsage("/gsettype <group> <type>");
		setArgumentRange(2,2);
		setIdentifier("gsettype");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}

		String username = sender.getName();
		String name = args[0];
		
		Group group = groupManager.getGroupByName(name);
		if(group == null) {
			sender.sendMessage("Group doesn't exist");
			return true;
		}
		
		if(group.getPersonal()) {
			sender.sendMessage("Can't modify a Personal Group");
			return true;
		}

		GroupMember groupMember = group.getGroupMember(username);		
		if(groupMember == null || groupMember.getRole() != Role.ADMIN) {
			sender.sendMessage("You don't have permission to perform this action");
			return true;
		}
		
		String typeName = args[1];
		GroupType type = null;
		for(GroupType t : GroupType.values()) {
			if(t.toString().equalsIgnoreCase(typeName)) {
				type = GroupType.valueOf(typeName.toUpperCase());
				break;
			}
		}
		
		if(type == null) {
			sender.sendMessage("Group Type " + typeName + " doesn't exist");
			return true;
		}
		
		group.setType(type);		
		return true;
	}

}

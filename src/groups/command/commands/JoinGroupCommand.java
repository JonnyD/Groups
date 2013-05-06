package groups.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Member;
import groups.model.Member.Role;

public class JoinGroupCommand extends PlayerCommand {

	public JoinGroupCommand() {
		super("Join Group");
		setDescription("Join a group");
		setUsage("/cjoin <group> <password");
		setArgumentRange(1,1);
		setIdentifier("ctjoin");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		String name = args[0];
		Group group = groupManager.getGroupByName(name);
		if(group == null) {
			sender.sendMessage("Group doesn't exist");
			return true;
		}
		
		String username = sender.getName();
		Member member = group.getMemberByName(username);
		if(member != null) {
			String message = "You are already member of this group";
			if(member.getRole() == Role.BANNED) {
				message = "You are banned from this group";
			}
			sender.sendMessage(message);
			return true;
		}
		
		String groupPassword = group.getPassword();
		boolean joinable = !groupPassword.equals("") && !groupPassword.equalsIgnoreCase("NULL") && groupPassword != null;
		if(!joinable) {
			sender.sendMessage("Group not joinable");
			return true;
		}
		
		String enteredPassword = args[1];
		boolean enteredCorrectPassword = enteredPassword.equals(groupPassword);
		if(!enteredCorrectPassword) {
			sender.sendMessage("Wrong password");
			return true;
		}
		
		groupManager.addMember(group, username, Role.MEMBER);
			
		return true;
	}

}

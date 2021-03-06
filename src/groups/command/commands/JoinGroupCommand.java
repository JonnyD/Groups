package groups.command.commands;

import groups.command.PlayerCommand;
import groups.model.Group;
import groups.model.Membership;
import groups.model.Membership.Role;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinGroupCommand extends PlayerCommand {

	public JoinGroupCommand() {
		super("Join Group");
		setDescription("Join a group");
		setUsage("/cjoin <group> <password");
		setArgumentRange(1,1);
		setIdentifier("cjoin");
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
		
		if(group.getPersonal()) {
			sender.sendMessage("You can't join a Personal Group");
			return true;
		}
		
		String username = sender.getName();
		Membership senderMembership = group.getMembership(username);
		if(senderMembership != null) {
			String message = "You are already member of this group";
			if(senderMembership.isBanned()) {
				message = "You are banned from this group";
			}
			sender.sendMessage(message);
			return true;
		}
		
		boolean joinable = group.isJoinable();
		if(!joinable) {
			sender.sendMessage("Group not joinable");
			return true;
		}
		
		String groupPassword = group.getPassword();
		String enteredPassword = args[1];
		boolean enteredCorrectPassword = enteredPassword.equals(groupPassword);
		if(!enteredCorrectPassword) {
			sender.sendMessage("Wrong password");
			return true;
		}
		
		membershipManager.addMembership(group, username, Role.MEMBER);
		return true;
	}

}

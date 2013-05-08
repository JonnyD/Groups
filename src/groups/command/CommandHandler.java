package groups.command;

import java.util.LinkedHashMap;
import java.util.Map;

import groups.command.Command;
import groups.command.commands.AddMemberCommand;
import groups.command.commands.CreateGroupCommand;
import groups.command.commands.DeleteGroupCommand;
import groups.command.commands.GroupInfoCommand;
import groups.command.commands.JoinGroupCommand;
import groups.command.commands.ListAllGroupsCommand;
import groups.command.commands.ListGroupsCommand;
import groups.command.commands.ListMembersCommand;
import groups.command.commands.RemoveMemberCommand;
import groups.command.commands.SetGroupTypeCommand;
import groups.command.commands.SetPasswordCommand;

import org.bukkit.command.CommandSender;

public class CommandHandler {
	private Map<String, Command> commands = new LinkedHashMap<String, Command>();

	public void registerCommands() {
		addCommand(new CreateGroupCommand());
		addCommand(new DeleteGroupCommand());
		addCommand(new ListAllGroupsCommand());
		addCommand(new AddMemberCommand());
		addCommand(new RemoveMemberCommand());
		addCommand(new ListMembersCommand());
		addCommand(new GroupInfoCommand());
		addCommand(new SetPasswordCommand());
		addCommand(new JoinGroupCommand());
		addCommand(new ListGroupsCommand());
		addCommand(new SetGroupTypeCommand());
	}
	
	public void addCommand(Command command) {
		String identifier = command.getIdentifier().toLowerCase();
		this.commands.put(identifier, command);
	}

	public boolean dispatch(CommandSender sender, String label, String[] args) {
		if (commands.containsKey(label)) {
			Command command = commands.get(label);
			if (args.length < command.getMinArguments()
					|| args.length > command.getMaxArguments()) {
				this.displayCommandHelp(command, sender);
				return true;
			}
			command.execute(sender, args);
		}
		return true;
	}

	public void displayCommandHelp(Command command, CommandSender sender) {
		sender.sendMessage(new StringBuilder().append("Command: ")
				.append(command.getName()).toString());
		sender.sendMessage(new StringBuilder().append("Description: ")
				.append(command.getDescription()).toString());
		sender.sendMessage(new StringBuilder().append("Usage: ")
				.append(command.getUsage()).toString());
	}
}

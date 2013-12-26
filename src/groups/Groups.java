package groups;

import groups.command.CommandHandler;
import groups.listener.PlayerListener;
import groups.manager.ConfigManager;
import groups.manager.GroupManager;
import groups.manager.MemberManager;
import groups.manager.MembershipManager;
import groups.service.GroupService;
import groups.service.MemberService;
import groups.service.MembershipService;
import groups.storage.DAO;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Groups extends JavaPlugin {

	private static Groups instance;
	private ConfigManager configManager;
	private DAO dao;
	private GroupManager groupManager;
	private MemberManager memberManager;
	private MembershipManager membershipManager;
	private CommandHandler commandHandler;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return commandHandler.dispatch(sender, label, args);
	}
	
	public void onEnable() {
		instance = this;
		configManager = new ConfigManager();
		dao = new DAO();
		
		GroupService groupService = new GroupService(dao);
		MemberService memberService = new MemberService(dao);
		MembershipService membershipService = new MembershipService(dao);
		
		groupManager = new GroupManager(groupService, memberService, membershipService);
		memberManager = new MemberManager(memberService);
		membershipManager = new MembershipManager(membershipService, memberService);
		
		commandHandler = new CommandHandler();
		commandHandler.registerCommands();
		registerEvents();
		System.out.println("Groups Enabled");
	}
	
	public void onDisable() {
		
	}
	
	public static Groups getInstance() {
		return instance;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public DAO getDao() {
		return dao;
	}
	
	public GroupManager getGroupManager() {
		return groupManager;
	}
	
	public MemberManager getMemberManager() {
		return memberManager;
	}
	
	public MembershipManager getMembershipManager() {
		return this.membershipManager;
	}
	
	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
	}

}

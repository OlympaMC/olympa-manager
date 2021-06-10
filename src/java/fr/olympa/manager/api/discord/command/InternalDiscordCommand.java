package fr.olympa.manager.api.discord.command;

import java.awt.Color;
import java.lang.reflect.Method;

import fr.olympa.manager.api.discord.DiscordPermission;
import net.dv8tion.jda.api.entities.Member;

public class InternalDiscordCommand {

	public CmdDiscord cmd;
	public DiscordPermission perm;
	public Method method;
	public Object commands;
	public String name;

	public InternalDiscordCommand(CmdDiscord cmd, Method method, Object commandsClass) {
		this.cmd = cmd;
		this.method = method;
		commands = commandsClass;
		name = method.getName();
		String permName = cmd.permissionName();
		if (!permName.isBlank()) {
			perm = DiscordPermission.get(cmd.permissionName());
			if (perm == null) {
				System.err.println(Color.RED + String.format("Permission %s is not register. The level of permission is Author, Owner, Admin.", cmd.permissionName()));
				perm = new DiscordPermission();
			}
		}
	}

	String getSyntax() {
		return cmd.syntax();
	}

	public boolean canRun(Member member) {
		return perm != null && perm.hasPermission(member);

	}
}

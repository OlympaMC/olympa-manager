package fr.olympa.manager.api.discord.command;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ComplexDiscordCommand extends ListenerAdapter {

	public String PREFIX = "/";
	public final Map<String, InternalDiscordCommand> commands = new HashMap<>();

	public void registerCommandsClass(Class<?> clazz, Object commandsClassInstance) {
		for (Method method : clazz.getDeclaredMethods())
			if (method.isAnnotationPresent(CmdDiscord.class)) {
				CmdDiscord cmd = method.getDeclaredAnnotation(CmdDiscord.class);
				if (method.getParameterCount() >= 1 && method.getParameterTypes()[0] == DiscordCommandContext.class) {
					InternalDiscordCommand internalCmd = new InternalDiscordCommand(cmd, method, commandsClassInstance);
					commands.put(method.getName().toLowerCase(), internalCmd);
					if (cmd.aliases() != null)
						commands.putAll(Arrays.stream(cmd.aliases()).collect(Collectors.toMap(s -> s, s -> internalCmd)));
				} else
					System.err.println(Color.RED + String.format("Error when loading command annotated method %s in class %s. Required first argument: fr.olympa.manager.api.discord.command.DiscordCommandContext",
							method.getName(), method.getDeclaringClass().getName()));
			}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		checkMsg(event, event.getMessage());
	}

	@Override
	public void onMessageUpdate(MessageUpdateEvent event) {
		checkMsg(event, event.getMessage());
	}

	/*
	 * @return true if good, false for error
	 */
	private boolean checkMsg(GenericMessageEvent event, Message message) {
		String rawMsg = message.getContentRaw();
		if (rawMsg.isBlank() || !rawMsg.startsWith(PREFIX))
			return true;
		String[] allArgs = rawMsg.split(" ");
		String label = allArgs[0];
		InternalDiscordCommand dCmd = commands.get(label);
		if (dCmd == null)
			message.reply(String.join("Commande `%s` inconnu. Essaye avec ", label)).queue();
		return true;
	}
}

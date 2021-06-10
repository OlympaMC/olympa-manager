package fr.olympa.manager.api.discord.command.old;

import net.dv8tion.jda.api.entities.Message;

public interface CommandEvent {

	void onCommandSend(DiscordCommand command, String[] args, Message message, String label);
}

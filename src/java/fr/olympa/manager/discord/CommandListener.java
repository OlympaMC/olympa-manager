package fr.olympa.manager.discord;

import java.io.IOException;

import fr.olympa.manager.api.ScriptAction;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Member member = event.getMember();
		Message message = event.getMessage();
		if (event.isWebhookMessage() || !event.isFromGuild())
			return;
		Guild guild = member.getGuild();
		MessageChannel channel = message.getChannel();
		if (!message.getContentDisplay().split(" ")[0].matches("^\\.[a-zA-Z]+$"))
			return;
		guild.retrieveMemberById(660223974000689182l).queue(otherBot -> {
			if (otherBot.getOnlineStatus() != OnlineStatus.OFFLINE)
				return;
			if (!message.getContentDisplay().equalsIgnoreCase(".start bungee1")) {
				channel.sendMessage("Il semblerait que " + otherBot.getAsMention() + " est " + otherBot.getOnlineStatus().getKey() + "... Tu peux l'allumer en faisant `.start bungee1`, je m'en occupe après.").queue();
				return;
			}
			try {
				ScriptAction.actionWithoutColor("mc start bungee1", s -> channel.sendMessage(s).queue());
			} catch (IOException | InterruptedException e) {
				channel.sendMessage("Erreur > `" + e.getMessage() + "`").queue();
				e.printStackTrace();
			}
		});
	}

}

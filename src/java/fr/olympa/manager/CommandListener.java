package fr.olympa.manager;

import java.io.IOException;

import fr.olympa.manager.server.ScriptAction;
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
		Guild guild = member.getGuild();
		MessageChannel channel = message.getChannel();
		if (!event.isFromGuild())
			return;
		if (!message.getContentDisplay().split(" ")[0].matches("^\\.[a-zA-Z]+$"))
			return;
		Member otherBot = guild.getMemberById(660223974000689182l);
		if (otherBot == null)
			return;
		if (otherBot.getOnlineStatus() != OnlineStatus.OFFLINE)
			return;
		if (!message.getContentDisplay().equalsIgnoreCase(".start bungee1")) {
			channel.sendMessage("Il semblerait que " + otherBot.getAsMention() + " est débrancher... Tu peux l'allumer en faisant `.start bungee1`, je m'en occupe après.").queue();
			return;
		}
		try {
			ScriptAction.action("mc start bungee1", s -> channel.sendMessage(s.replaceAll("\\[\\d*(;\\d*)?m?", "")).queue());
		} catch (IOException | InterruptedException e) {
			channel.sendMessage("Erreur > `" + e.getMessage() + "`").queue();
			e.printStackTrace();
		}
	}

}

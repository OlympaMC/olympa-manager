package fr.olympa.manager.discord;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordManager {

	public static DiscordManager INSTANCE;

	public JDA jda;

	public DiscordManager() {
		INSTANCE = this;
	}

	@SuppressWarnings("deprecation")
	public void connect() throws LoginException {
		JDABuilder builder = new JDABuilder("ODM0OTYyMjE1MzExOTAwNzIy.YIIhKA.Rt9SxYSB3A9HA2CaCEVUfDwcHAM");
		builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
		builder.addEventListeners(new CommandListener());
		builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
		jda = builder.build();
	}
}

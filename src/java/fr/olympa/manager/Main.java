package fr.olympa.manager;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class Main {
	public static void main(String[] args) {
		try {
			new Main().connect();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

	private JDA jda;

	@SuppressWarnings("deprecation")
	public void connect() throws LoginException {
		JDABuilder builder = new JDABuilder("ODM0OTYyMjE1MzExOTAwNzIy.YIIhKA.Rt9SxYSB3A9HA2CaCEVUfDwcHAM");
		builder.setStatus(OnlineStatus.IDLE);
		builder.addEventListeners(new CommandListener());
		jda = builder.build();
	}
}

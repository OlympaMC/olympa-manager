package fr.olympa.manager;

import javax.security.auth.login.LoginException;

import fr.olympa.manager.discord.DiscordManager;

public class Main {
	public static void main(String[] args) {
		try {
			new DiscordManager().connect();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

}

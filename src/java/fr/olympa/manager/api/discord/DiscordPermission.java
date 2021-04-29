package fr.olympa.manager.api.discord;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import fr.olympa.manager.api.java.ListCompareList;
import fr.olympa.manager.api.java.MapFromList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class DiscordPermission {

	private final static MapFromList<String, DiscordPermission> permissions = new MapFromList<>(dp -> dp.name);

	public static Set<Entry<String, DiscordPermission>> entrySet() {
		return permissions.entrySet();
	}

	public static DiscordPermission get(String name) {
		return permissions.get(name);
	}

	public static DiscordPermission add(DiscordPermission permission) {
		return permissions.add(permission);
	}

	@NotNull
	String name;
	@Nullable
	List<Role> associatedRoles;
	@Nullable
	List<Permission> nativeDiscordPermission;

	boolean botAllow = true;
	boolean userAllow = true;

	public DiscordPermission(String name, Role... associatedRole) {
		this.name = name;
		associatedRoles = Arrays.asList(associatedRole);
	}

	public DiscordPermission(Permission... nativeDiscordPermission) {
		this.nativeDiscordPermission = Arrays.asList(nativeDiscordPermission);
		name = this.nativeDiscordPermission.stream().map(Permission::name).collect(Collectors.joining(", "));
	}

	/**
	 * For Debug Only. Permission for Author, Owner & Admin
	 */
	public DiscordPermission() {
		name = "SUPER_ADMIN";
		associatedRoles = null;
		nativeDiscordPermission = null;
	}

	public boolean hasPermission(Member member) {
		return hasPermission(member);
	}

	public boolean hasPermission(GuildChannel guildChannel, Member member) {
		return containsRoles(member.getRoles()) || containsPermission(guildChannel, member);
	}

	private boolean containsPermission(GuildChannel guildChannel, Member member) {
		Iterator<Permission> it = nativeDiscordPermission.iterator();
		if (guildChannel != null) {
			while (it.hasNext())
				if (member.hasPermission(guildChannel, it.next()))
					return true;
		} else
			while (it.hasNext())
				if (member.hasPermission(it.next()))
					return true;
		return false;
	}

	private boolean containsRoles(List<Role> role) {
		ListCompareList<Role> comparator = new ListCompareList<Role>().compare((thisCompare, compareNext) -> {
			return thisCompare.getGuild().getIdLong() == compareNext.getGuild().getIdLong() && thisCompare.getIdLong() == compareNext.getIdLong();
		});
		return comparator.contains(associatedRoles, role);
	}
}

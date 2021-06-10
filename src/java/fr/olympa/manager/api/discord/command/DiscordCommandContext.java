package fr.olympa.manager.api.discord.command;

import javax.annotation.Nonnull;

import fr.olympa.manager.api.discord.command.old.DiscordCommand;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

public class DiscordCommandContext {

	private GenericMessageEvent event;
	private Message message;
	private User user;
	private Member member;

	private DiscordCommand command;
	private String label;
	private String[] args;

	public DiscordCommandContext(GenericMessageEvent event, Message message, User user, Member member) {
		this.event = event;
		this.message = message;
		this.user = user;
		this.member = member;
	}

	public Message getMessage() {
		return message;
	}

	public User getUser() {
		return user;
	}

	public Member getMember() {
		return member;
	}

	/**
	 * The {@link net.dv8tion.jda.api.entities.MessageChannel MessageChannel} for this Message
	 *
	 * @return The MessageChannel
	 */
	@Nonnull
	public MessageChannel getChannel() {
		return event.getChannel();
	}

	/**
	 * The id for this message
	 *
	 * @return The id for this message
	 */
	@Nonnull
	public String getMessageId() {
		return event.getMessageId();
	}

	/**
	 * The id for this message
	 *
	 * @return The id for this message
	 */
	public long getMessageIdLong() {
		return event.getMessageIdLong();
	}

	/**
	 * Indicates whether the message is from the specified {@link net.dv8tion.jda.api.entities.ChannelType ChannelType}
	 *
	 * @param  type
	 *         The ChannelType
	 *
	 * @return True, if the message is from the specified channel type
	 */
	public boolean isFromType(@Nonnull ChannelType type) {
		return event.getChannel().getType() == type;
	}

	/**
	 * Whether this message was sent in a {@link net.dv8tion.jda.api.entities.Guild Guild}.
	 * <br>If this is {@code false} then {@link #getGuild()} will throw an {@link java.lang.IllegalStateException}.
	 *
	 * @return True, if {@link #getChannelType()}.{@link ChannelType#isGuild() isGuild()} is true.
	 */
	public boolean isFromGuild() {
		return getChannelType().isGuild();
	}

	/**
	 * The {@link net.dv8tion.jda.api.entities.ChannelType ChannelType} for this message
	 *
	 * @return The ChannelType
	 */
	@Nonnull
	public ChannelType getChannelType() {
		return event.getChannel().getType();
	}

	/**
	 * The {@link net.dv8tion.jda.api.entities.Guild Guild} the Message was received in.
	 * <br>If this Message was not received in a {@link net.dv8tion.jda.api.entities.TextChannel TextChannel},
	 * this will throw an {@link java.lang.IllegalStateException}.
	 *
	 * @throws java.lang.IllegalStateException
	 *         If this was not sent in a {@link net.dv8tion.jda.api.entities.TextChannel}.
	 *
	 * @return The Guild the Message was received in
	 *
	 * @see    #isFromGuild()
	 * @see    #isFromType(ChannelType)
	 * @see    #getChannelType()
	 */
	@Nonnull
	public Guild getGuild() {
		return getTextChannel().getGuild();
	}

	/**
	 * The {@link net.dv8tion.jda.api.entities.TextChannel TextChannel} the Message was received in.
	 * <br>If this Message was not received in a {@link net.dv8tion.jda.api.entities.TextChannel TextChannel},
	 * this will throw an {@link java.lang.IllegalStateException}.
	 *
	 * @throws java.lang.IllegalStateException
	 *         If this was not sent in a {@link net.dv8tion.jda.api.entities.TextChannel}.
	 *
	 * @return The TextChannel the Message was received in
	 *
	 * @see    #isFromGuild()
	 * @see    #isFromType(ChannelType)
	 * @see    #getChannelType()
	 */
	@Nonnull
	public TextChannel getTextChannel() {
		return event.getTextChannel();
	}

	/**
	 * The {@link net.dv8tion.jda.api.entities.PrivateChannel PrivateChannel} the Message was received in.
	 * <br>If this Message was not received in a {@link net.dv8tion.jda.api.entities.PrivateChannel PrivateChannel},
	 * this will throw an {@link java.lang.IllegalStateException}.
	 *
	 * @throws java.lang.IllegalStateException
	 *         If this was not sent in a {@link net.dv8tion.jda.api.entities.PrivateChannel}.
	 *
	 * @return The PrivateChannel the Message was received in
	 *
	 * @see    #isFromGuild()
	 * @see    #isFromType(ChannelType)
	 * @see    #getChannelType()
	 */
	@Nonnull
	public PrivateChannel getPrivateChannel() {
		return getPrivateChannel();
	}

}

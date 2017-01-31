/*
 * Copyright 2016 Abstraction
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.astromc.libreui.utils.messaging;

import net.astromc.libreui.utils.version.Version;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.Set;

/**
 * A utility class to easily send MC|BOpen messages to
 * the client on both new (with dual wielding) and legacy
 * versions.
 */
public final class BookOpenPluginMessageHandler {
    /**
     * The plugin messaging channel for which opens the book
     * on the player's client. This is a default channel in
     * Minecraft.
     */
    private static final String OPEN_BOOK_MESSAGE_CHANNEL = "MC|BOpen";

    /**
     * The version at which dual wielding was implemented into the
     * game. This is used to determine whether or not the hand
     * holding the book should be included in the plugin message.
     */
    private static final Version DUAL_WIELDING_VERSION_DIVIDER = Version.from(1,9,0);

    /**
     * Magic value which signifies the main hand for the player.
     */
    private static final int MAIN_HAND_ENUM_CONSTANT_INDEX = 0;

    /**
     * The main hand magic value wrapped in a cached byte array,
     * so it doesn't have to be compiled for each plugin message.
     */
    private static final byte[] MAIN_HAND_ENUM_CONSTANT_BYTE_ARRAY = new byte[] {MAIN_HAND_ENUM_CONSTANT_INDEX};

    /**
     * Cached empty byte array. This is sent as the plugin message
     * if the version doesn't need to specify the hand which the
     * book is held in.
     */
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * {@link PlayerChannelRegistrationInsurer} instance used
     * for insuring that the MC|BOpen channel is registered when
     * sending the message. The instance is using the
     * {@link FieldCachingChannelsRetrieveStrategy} strategy by default.
     */
    private static final PlayerChannelRegistrationInsurer
            CHANNEL_REGISTRATION_INSURER = new PlayerChannelRegistrationInsurer(new FieldCachingChannelsRetrieveStrategy());

    //
    private final Plugin plugin;

    public BookOpenPluginMessageHandler(Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin);
    }

    /**
     * Registers the <tt>MC|BOpen</tt> messaging channel to the
     * plugin, which allows the plugin to send book opening messages
     * to the client through plugin messaging. This method is safe
     * to invoke without unregistered the channel. The Bukkit
     * implementation just re-adds it to the {@link Set} of registered
     * channels.
     */
    public void registerBookOpenMessagingChannel() {
        this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, OPEN_BOOK_MESSAGE_CHANNEL);
    }

    /**
     * Sends a message in the <tt>MC|BOpen</tt> channel to the player.
     * Whenever the client receives a message in this channel it will
     * open the book in their current hand. For versions with dual wielding
     * (1.9 and above), {@link PlayerInventory#getItemInHand} and {@link
     * PlayerInventory#setItemInHand} are depreciated in the Bukkit API, but
     * still works and delegates to the main hand. Additional to that, the
     * <tt>MC|BOpen</tt> channel now must also send the targeted hand in
     * order for it to work. This method accounts for that by explicitly
     * choosing the main hand on newer versions, retaining functionality on
     * legacy and newer versions.
     * <p/>
     *
     * The channel is insured to be registered to the player using an
     * instance of {@link PlayerChannelRegistrationInsurer}, and the
     * {@link PlayerChannelRegistrationInsurer#insureChannelRegistration(Player, String)}
     * method to forcefully register the channel.
     *
     * @param player the player sending book open message to
     * @param version the player client version
     */
    public void sendOpenBookMessageToPlayer(Player player, Version version) {
        byte[] message = getAppropriateMessageFromVersion(version);

        CHANNEL_REGISTRATION_INSURER.insureChannelRegistration(player, OPEN_BOOK_MESSAGE_CHANNEL);

        player.sendPluginMessage(this.plugin, OPEN_BOOK_MESSAGE_CHANNEL, message);
    }

    /**
     * Returns the appropriate byte array message depending on
     * the client version of the player. This assures this utility
     * works on legacy version, and newer versions with dual wielding.
     *
     * @param version the player client version
     * @return an empty byte array for legacy servers, and a
     * single-element byte array with the value of 0 for newer
     * version with dual wielding
     */
    private static byte[] getAppropriateMessageFromVersion(Version version) {
        return version.compareTo(DUAL_WIELDING_VERSION_DIVIDER) >= 0 ? MAIN_HAND_ENUM_CONSTANT_BYTE_ARRAY : EMPTY_BYTE_ARRAY;
    }
}

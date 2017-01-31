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

import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

/**
 * This class insures to add channels to the Set of
 * registered channels the Player is listening to. This
 * is required to send plugin messages which the player
 * hasn't explicitly registered to listen for. This can
 * be used to forcefully make players listen for certain
 * vanilla channels such as the MC|BOpen channel.
 */
public final class PlayerChannelRegistrationInsurer {
    private ChannelsRetrieveStrategy channelsRetrieveStrategy;

    /**
     * Constructs a new {@link PlayerChannelRegistrationInsurer}
     * instance using the specified {@link ChannelsRetrieveStrategy
     * strategy} implementation.
     *
     * @param strategy the strategy that this instance should use by default
     */
    public PlayerChannelRegistrationInsurer(ChannelsRetrieveStrategy strategy) {
        this.setChannelsRetrieveStrategy(strategy);
    }

    /**
     * Sets the strategy used by this instance to use the
     * specified {@link ChannelsRetrieveStrategy strategy}.
     *
     * @param strategy the strategy being set
     */
    public void setChannelsRetrieveStrategy(ChannelsRetrieveStrategy strategy) {
        this.channelsRetrieveStrategy = Objects.requireNonNull(strategy);
    }

    /**
     * Insures that player is listening to the specified channel. The
     * listening channels is retrieved from the player object using an
     * {@link ChannelsRetrieveStrategy} instance, and then adds the
     * channel to the Set of channels.
     *
     * @param player the player insuring the channel is registered for
     * @param channel the channel insured registration for
     */
    public void insureChannelRegistration(Player player, String channel) {
        try {
            Set<String> channels = channelsRetrieveStrategy.getChannels(player);
            channels.add(channel);
        } catch (CannotRetrieveChannelsException e) {
            e.printStackTrace();
        }
    }
}

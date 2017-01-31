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

import java.util.Set;

/**
 * A Strategy pattern interface. This interface is
 * used by the {@link PlayerChannelRegistrationInsurer}
 * class, to retrieve the register channels of the
 * CraftPlayer object.
 */
public interface ChannelsRetrieveStrategy {

    /**
     * Retrieves the value of the channels field in the
     * <tt>CraftPlayer</tt> {@link Player} implementation.
     *
     * @param source the player retrieving the channels from
     * @return a {@link Set} of String which is the channels
     * @throws CannotRetrieveChannelsException if the method is
     * unable to retrieve the values of the channels field
     */
    Set<String> getChannels(Player source) throws CannotRetrieveChannelsException;
}

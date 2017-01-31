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

import java.lang.reflect.Field;

/**
 * An abstract {@link ChannelsRetrieveStrategy} implementation
 * with standard a standard method to retrieve the channels field
 * from the source parameter. This is useful for reflection based
 * implementation, as the class name also implies.
 */
public abstract class ReflectionBasedChannelsRetrieveStrategy
        implements ChannelsRetrieveStrategy {
    /**
     * CraftPlayer implementation <tt>channels</tt> field name.
     */
    private static final String CHANNELS_FIELD_NAME = "channels";

    /**
     * Resolves and returns the <tt>channels</tt> field for the
     * specified <tt>source</tt> object. Additional this method
     * automatically set the field's accessibility to true.
     *
     * @param source the player resolving the channels field for
     * @return the field holding registered channels for the source
     * @throws CannotRetrieveChannelsException if an exception occur
     * is it wrapped in this exception and rethrown to the method caller
     */
    protected final Field resolveChannelsField(Player source) throws CannotRetrieveChannelsException {
        try {
            Class<? extends Player> playerClass = source.getClass();
            Field channelsField = playerClass.getDeclaredField(CHANNELS_FIELD_NAME);
            channelsField.setAccessible(true);

            return channelsField;
        } catch (NoSuchFieldException e) {
            throw new CannotRetrieveChannelsException(e);
        }
    }
}

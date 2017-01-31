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
import java.util.Set;

/**
 * This {@link ChannelsRetrieveStrategy} implementation dynamically
 * retrieves the channels fields from the class of the <tt>source</tt>
 * parameter in the {@link ChannelsRetrieveStrategy#getChannels(Player)}
 * method.
 */
public final class DynamicChannelsRetrieveStrategy
        extends ReflectionBasedChannelsRetrieveStrategy {
    /**
     * Singleton implementation instance.
     */
    private static final DynamicChannelsRetrieveStrategy
            INSTANCE = new DynamicChannelsRetrieveStrategy();

    /**
     * Private constructor for non-insatiability to enforce the
     * Singleton pattern.
     */
    private DynamicChannelsRetrieveStrategy() {}

    /**
     * Implementation of the specification described in the class
     * documentation. If a reflection related exception occur in
     * this method, it is wrapped and rethrown in a
     * {@link CannotRetrieveChannelsException}.
     *
     * @param source the player retrieving the channels from
     * @return the channels field value
     * @throws CannotRetrieveChannelsException if a reflection
     * related exception is thrown
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getChannels(Player source) throws CannotRetrieveChannelsException {
        Field channelsField = this.resolveChannelsField(source);

        try {
            channelsField.setAccessible(true);
            return (Set<String>) channelsField.get(source);
        } catch (IllegalAccessException e) {
            throw new CannotRetrieveChannelsException(e);
        }
    }

    /**
     * Returns the Singleton instance for this object.
     *
     * @return Singleton instance
     */
    public static DynamicChannelsRetrieveStrategy getInstance() {
        return INSTANCE;
    }
}

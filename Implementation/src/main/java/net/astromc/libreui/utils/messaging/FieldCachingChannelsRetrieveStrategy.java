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
 * A channels field caching implementation. This implementation
 * dynamically resolves the channels field, and caches it in a
 * field. The cache is then used for future method calls, if an
 * exception occur it is wrapped in a {@link CannotRetrieveChannelsException}
 * and the cache is invalidated.
 */
public final class FieldCachingChannelsRetrieveStrategy implements ChannelsRetrieveStrategy {

    private Field cachedChannelsField;

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getChannels(Player source) throws CannotRetrieveChannelsException {
        if (this.cachedChannelsField == null) {
            this.cachedChannelsField = resolveChannelsField(source);
        }

        try {
            return (Set<String>) this.cachedChannelsField.get(source);
        } catch (IllegalAccessException e) {
            this.invalidateCache();
            throw new CannotRetrieveChannelsException(e);
        }
    }

    /**
     * Invalidates the cached channels field.
     */
    private void invalidateCache() {
        this.cachedChannelsField = null;
    }

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

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
public final class FieldCachingChannelsRetrieveStrategy
        extends ReflectionBasedChannelsRetrieveStrategy {
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
}

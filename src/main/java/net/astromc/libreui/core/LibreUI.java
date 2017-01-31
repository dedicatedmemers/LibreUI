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

package net.astromc.libreui.core;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.astromc.libreui.bridge.VersionBridge;
import net.astromc.libreui.caching.BookAndPageCacheLoader;
import net.astromc.libreui.utils.messaging.BookOpenPluginMessageHandler;
import org.bukkit.plugin.java.JavaPlugin;

//TODO: Javadoc
public final class LibreUI extends JavaPlugin {
    private static final LoadingCache<Object, String> SERIALIZED_CACHE = CacheBuilder
            .newBuilder()
            .weakKeys()
            .build(new BookAndPageCacheLoader());

    private static LibreUIAccessor accessor;

    private VersionBridge bridge;
    private BookOpenPluginMessageHandler bookOpenPluginMessageHandler;

    @Override
    public void onLoad() {
        LibreUIInitializer initializer = new LibreUIInitializer(this);

        initializer.registerAndDefaultVersionBridgeProvider();

        this.bridge = initializer.loadVersionBridge();
        this.bookOpenPluginMessageHandler = initializer.
                createAndConfigureBookOpenPluginMessageHandler();

        LibreUI.accessor = LibreUIAccessor.newInstance(this);
    }

    @Override
    public void onDisable() {
        LibreUI.accessor = null;
        LibreUI.SERIALIZED_CACHE.invalidateAll();
    }

    public VersionBridge getVersionBridge() {
        return bridge;
    }

    public BookOpenPluginMessageHandler getBookOpenPluginMessageHandler() {
        return bookOpenPluginMessageHandler;
    }

    public static LoadingCache<Object, String> getSerializedCache() {
        return LibreUI.SERIALIZED_CACHE;
    }

    public static LibreUIAccessor getAccessor() {
        return LibreUI.accessor;
    }
}

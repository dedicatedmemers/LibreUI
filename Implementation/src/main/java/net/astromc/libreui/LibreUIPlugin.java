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

package net.astromc.libreui;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.astromc.libreui.api.LibreUI;
import net.astromc.libreui.api.book.Book;
import net.astromc.libreui.bridge.VersionBridge;
import net.astromc.libreui.utils.messaging.BookOpenPluginMessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class LibreUIPlugin extends JavaPlugin implements LibreUI {

    private VersionBridge bridge;

    private BookOpenPluginMessageHandler bookOpenPluginMessageHandler;

    @Override
    public void onLoad() {
        LibreUIInitializer initializer = new LibreUIInitializer(this);

        initializer.registerAndDefaultVersionBridgeProvider();

        this.bridge = initializer.loadVersionBridge();
        this.bookOpenPluginMessageHandler = initializer.createAndConfigureBookOpenPluginMessageHandler();
        Bukkit.getServicesManager().register(LibreUI.class, this, this, ServicePriority.Normal);
    }

    public void openBook(Player player, Book book) {
        this.bridge.openBook(player, book, this);
    }

    @Override
    public void onDisable() {
    }

    public BookOpenPluginMessageHandler getBookOpenPluginMessageHandler() {
        return bookOpenPluginMessageHandler;
    }

    public static <T> LoadingCache<T, String> buildSerialisationCache(CacheLoader<T, String> loader) {
        return CacheBuilder
                .newBuilder()
                .weakKeys()
                .build(loader);
    }
}

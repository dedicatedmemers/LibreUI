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

import net.astromc.libreui.bridge.UnsupportedVersionException;
import net.astromc.libreui.bridge.VersionBridge;
import net.astromc.libreui.bridge.VersionBridgeProviders;
import net.astromc.libreui.bridge.impl.VersatileVersionBridgeProvider;
import net.astromc.libreui.utils.messaging.BookOpenPluginMessageHandler;
import net.astromc.libreui.utils.version.Version;
import net.astromc.libreui.utils.version.craftbukkit.CraftBukkitImplementationStringVersionSupplier;
import net.astromc.libreui.utils.version.craftbukkit.CraftBukkitImplementationVersionParser;

/**
 * This class is responsible for initializing and providing
 * the {@link LibreUI} with necessary instances.
 */
/* package-private */ final class LibreUIInitializer {
    private static final String VERSATILE_VERSION_BRIDGE_PROVIDER_NAME = "versatile";
    private final LibreUI libreUI;

    /* package-private */
    LibreUIInitializer(LibreUI libreUI) {
        this.libreUI = libreUI;
    }

    /**
     * Registers the {@link VersatileVersionBridgeProvider}
     * as the default provider, it by the name <tt>"versatile"</tt>
     * in {@link VersionBridgeProviders}.
     */
    public void registerAndDefaultVersionBridgeProvider() {
        VersionBridgeProviders.registerProvider(
                VERSATILE_VERSION_BRIDGE_PROVIDER_NAME,
                VersatileVersionBridgeProvider.INSTANCE);

        VersionBridgeProviders.registerDefaultProvider(
                VersatileVersionBridgeProvider.INSTANCE);
    }

    /**
     * Loads a {@link VersionBridge} implementation for the
     * {@link LibreUI} instance to use.
     *
     * @return a {@link VersionBridge} implementation
     */
    public VersionBridge loadVersionBridge() {
        String stringVersion = CraftBukkitImplementationStringVersionSupplier.INSTANCE.get();
        Version version = CraftBukkitImplementationVersionParser.parse(stringVersion);

        try {
            //TODO: Load which provider to use from config
            return VersionBridgeProviders.newInstance(version);
        } catch (UnsupportedVersionException e) {
            throw new IllegalStateException("Bukkit version not supported", e);
        }
    }

    /**
     * Creates and configures a {@link BookOpenPluginMessageHandler}
     * for the {@link LibreUI} instance to use.
     *
     * @return a new and configures {@link BookOpenPluginMessageHandler} instance
     */
    public BookOpenPluginMessageHandler createAndConfigureBookOpenPluginMessageHandler() {
        BookOpenPluginMessageHandler bookOpenPluginMessageHandler =
                new BookOpenPluginMessageHandler(libreUI);

        bookOpenPluginMessageHandler.registerBookOpenMessagingChannel();

        return bookOpenPluginMessageHandler;
    }
}

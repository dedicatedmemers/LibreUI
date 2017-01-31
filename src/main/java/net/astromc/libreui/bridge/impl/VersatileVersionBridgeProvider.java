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

package net.astromc.libreui.bridge.impl;

import net.astromc.libreui.bridge.UnsupportedVersionException;
import net.astromc.libreui.bridge.VersionBridge;
import net.astromc.libreui.bridge.VersionBridgeProvider;
import net.astromc.libreui.utils.version.Version;

/**
 * A {@link VersionBridgeProvider} which provides instances of
 * {@link VersatileVersionBridge} with the implementation version,
 * specified in the {@link VersionBridgeProvider#newVersionBridge(Version)
 * newVersionBridge} method.
 */
public enum VersatileVersionBridgeProvider implements VersionBridgeProvider {
    /**
     * Singleton implementation instance.
     */
    INSTANCE;

    /**
     * The minimum support CraftBukkit implementation version.
     */
    private static final Version MINIMUM_SUPPORTED_VERSION = Version.from(1,7,0);

    /**
     * Creates a new {@link VersatileVersionBridge} instance with
     * the specified implementation <tt>version</tt>. An
     * {@link UnsupportedVersionException} is thrown if the
     * specified <tt>version</tt> is below 1.7.
     *
     * @param version implementation version used by the
     * <tt>VersatileVersionBridge</tt> instance
     * @return a new {@link VersatileVersionBridge} using
     * the specified <tt>version</tt>
     * @throws UnsupportedVersionException if the specified version
     * is below the server version 1.7 (CraftBukkit 1_7_R1)
     */
    @Override
    public VersionBridge newVersionBridge(Version version)
            throws UnsupportedVersionException {
        if (version.compareTo(MINIMUM_SUPPORTED_VERSION) < 0) {
            throw new UnsupportedVersionException(
                    "Versions below 1.7 is not supported", version);
        }

        return VersatileVersionBridge.newInstance(version);
    }
}

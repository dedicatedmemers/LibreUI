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

package net.astromc.libreui.bridge;

import net.astromc.libreui.utils.version.Version;

/**
 * This interface is responsible for creating, or
 * "providing", new {@link VersionBridge} instances.
 */
public interface VersionBridgeProvider {

    /**
     * Creates and returns a new {@link VersionBridge} instance. An
     * {@link UnsupportedVersionException} is thrown if the
     * {@link VersionBridge} implementation return does not support
     * the specified <tt>version</tt>.
     *
     * @param version the underlying system version
     * @return a new {@link VersionBridge} instance
     * @throws UnsupportedVersionException if the implementation
     * provided doesn't support the specified version
     */
    VersionBridge newVersionBridge(Version version) throws UnsupportedVersionException;
}

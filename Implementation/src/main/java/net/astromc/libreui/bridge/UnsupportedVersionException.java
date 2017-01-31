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
 * This exception indicates that a version in not supported.
 */
public final class UnsupportedVersionException extends Exception {
    private final Version version;

    /**
     * Constructs a new <tt>UnsupportedVersionException</tt>
     * with an error message, and the unsupported version.
     *
     * @param message the error message
     * @param version the unsupported version
     */
    public UnsupportedVersionException(String message, Version version) {
        super(message);
        this.version = version;
    }

    /**
     * Returns the unsupported version.
     *
     * @return the unsupported version
     */
    public Version getVersion() {
        return version;
    }
}

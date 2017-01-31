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

package net.astromc.libreui.utils.version.craftbukkit;

import net.astromc.libreui.utils.version.Version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Utility class for parsing String CraftBukkit implementation
 * versions to {@link Version} objects.
 */
public enum CraftBukkitImplementationVersionParser {;
    private static Pattern CRAFT_BUKKIT_IMPLEMENTATION_VERSION_PATTERN = Pattern.compile("(\\d{1,2})_(\\d{1,3})_R(\\d{1,3})");

    /**
     * Parses a CraftBukkit implementation version {@link String}
     * to a {@link Version} object representing the version.
     *
     * @param version the CraftBukkit implementation version
     * @return the parsed versions
     * @throws IllegalArgumentException if <tt>version</tt> doesn't match
     * the {@link CraftBukkitImplementationVersionParser#CRAFT_BUKKIT_IMPLEMENTATION_VERSION_PATTERN}
     * pattern.
     */
    public static Version parse(String version) throws IllegalArgumentException {
        Matcher matcher = CRAFT_BUKKIT_IMPLEMENTATION_VERSION_PATTERN.matcher(version);

        if (!matcher.matches() || !matcher.reset().find()) {
            throw new IllegalArgumentException("Version cannot be parsed: " + version);
        }

        int major = parseInt(matcher.group(1));
        int minor = parseInt(matcher.group(2));
        int patch = parseInt(matcher.group(3));

        return Version.from(major, minor, patch);
    }
}

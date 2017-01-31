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

import org.bukkit.Bukkit;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Supplier implementation returns the CraftBukkit implementation version,
 * using a Regex pattern to locate the version package in the fully-qualified class
 * name for the class from the {@link Bukkit#getServer()} implementation. This class
 * is implemented using an enum with a single constant named "INSTANCE". The
 * CraftBukkit implementation version is the same as the Minecraft server
 * implementation version, and can therefore be used to determine the Minecraft
 * version the server is running.
 */
public enum CraftBukkitImplementationStringVersionSupplier implements Supplier<String> {
    /**
     * Singleton implementation instance. Getter method omitted.
     */
    INSTANCE;

    /**
     * Pattern to identify the CraftBukkit implementation version.
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile("(?<=v)\\d+_\\d+_R\\d+");

    /**
     * Gets the version from the CraftBukkit implementation. The CraftBukkit version is
     * determined by name from the last package in the fully-qualified class name from the
     * object returned by {@link Bukkit#getServer()}. On a running CraftBukkit server,
     * this should return an instance from the CraftBukkit {@link org.bukkit.Server}
     * implementation, which should always reside in the package with a name
     * corresponding to the CraftBukkit implementation.<p>
     *
     * The version is then ultimately retrieved from the fully-qualified class name
     * using the {@link CraftBukkitImplementationStringVersionSupplier#VERSION_PATTERN VERSION_PATTERN}
     * <tt>Pattern</tt>. If the {@link Pattern#matcher(CharSequence) matcher}'s
     * {@link Matcher#find() find()} method returns false, a {@link ImplementationVersionPackageNotFound}
     * is thrown to indicate the method is unable to resolve the CraftBukkit
     * implementation version. If a result if found, the leading '<tt>v</tt>'
     * in the implementation version is omitted, and only the raw version
     * identifier is returned.
     *
     * <p>
     * <table summary="Version examples">
     *     <tr><td>Version</td>     <td>Package</td>                         <td>Resolved</td>
     *     <tr><td>1.8-R0.1</td>    <td>org.bukkit.craftbukkit.v1_8_R1</td>  <td>1_8_R1</td>
     *     <tr><td>1.10.2-R0.1</td> <td>org.bukkit.craftbukkit.v1_10_R1</td> <td>1_10_R1</td>
     * </table>
     *
     * @return the CraftBukkit implementation version
     * @throws ImplementationVersionPackageNotFound if the Regex pattern
     * {@link CraftBukkitImplementationStringVersionSupplier#VERSION_PATTERN VERSION_PATTERN}
     * cannot find a match
     */
    @Override
    public final String get() throws ImplementationVersionPackageNotFound {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        Matcher matcher = VERSION_PATTERN.matcher(packageName);

        if (!matcher.find()) {
            throw new ImplementationVersionPackageNotFound(
                    "could not resolve CraftBukkit implementation version for '" + packageName + "'");
        }

        return matcher.group();
    }
}

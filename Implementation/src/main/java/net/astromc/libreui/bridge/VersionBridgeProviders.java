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

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;

/**
 * A simple service provider framework for creating
 * and providing {@link VersionBridge} implementations.
 */
public enum VersionBridgeProviders {;
    /**
     * The name for the default provider.
     */
    private static final String DEFAULT_PROVIDER_NAME = "<default>";
    /**
     * Synchronized {@link TreeMap} using the {@link String#CASE_INSENSITIVE_ORDER}
     * {@link java.util.Comparator}. Holds the registered providers, and used to
     * retrieve providers
     * by their name, regardless of their casing.
     */
    private static final Map<String, VersionBridgeProvider> PROVIDERS = Collections.synchronizedMap(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));

    /**
     * Registers the <tt>provider</tt> as the default provider.
     *
     * @param provider the new default provider
     */
    public static void registerDefaultProvider(VersionBridgeProvider provider) {
        PROVIDERS.put(DEFAULT_PROVIDER_NAME, requireNonNull(provider, "provider"));
    }

    /**
     * Registers a new provider to the specified name. An
     * {@link IllegalArgumentException} is thrown if <tt>name</tt>
     * matches {@link VersionBridgeProviders#DEFAULT_PROVIDER_NAME}.
     *
     * @param name the key for choosing the provider
     * @param provider the provider being registered
     * @throws IllegalArgumentException if <tt>name</tt> matches
     * {@link VersionBridgeProviders#DEFAULT_PROVIDER_NAME}
     */
    public static void registerProvider(String name, VersionBridgeProvider provider) throws IllegalArgumentException {

        requireNonNull(name, "name");
        requireNonNull(provider, "provider");

        if (name.equals(DEFAULT_PROVIDER_NAME)) {
            throw new IllegalArgumentException("The default provider must explicitly be set using the setDefaultProvider method");
        }

        PROVIDERS.put(name, provider);
    }

    /**
     * Creates a new {@link VersionBridge} instance using
     * the default provider. This method delegates to the
     * {@link VersionBridgeProviders#newInstance(String, Version)}
     * with the parameters ({@link VersionBridgeProviders#DEFAULT_PROVIDER_NAME},
     * <tt>version</tt>). If no default provider is registered an
     * <tt>IllegalArgumentException</tt> is thrown indicating no
     * default provider registered. An <tt>UnsupportedVersionException</tt>
     * may be thrown if a provider is found, but does not support <tt>version</tt>.
     *
     * @param version the underlying system version
     * @return a new {@link VersionBridge} instance
     * @throws UnsupportedVersionException if the specified
     * version is not supported by the provider
     * @throws IllegalArgumentException if there is no default
     * provider registered
     */
    public static VersionBridge newInstance(Version version) throws UnsupportedVersionException, IllegalArgumentException {
        return newInstance(DEFAULT_PROVIDER_NAME, version);
    }

    /**
     * Gets a registered {@link VersionBridgeProvider} by its
     * registered name. If no provider is found an {@link
     * IllegalArgumentException} is thrown indicating no provider
     * is registered with <tt>name</tt>. An <tt>UnsupportedVersionException</tt>
     * may be thrown if a provider is found, but does not support <tt>version</tt>.
     *
     * @param name the name of the provider to use
     * @param version the underlying system version
     * @return a new {@link VersionBridge} instance
     * @throws UnsupportedVersionException if the specified
     * version is not supported by the provider
     * @throws IllegalArgumentException if there is no
     * provider registered with the specified <tt>name</tt>
     */
    public static VersionBridge newInstance(String name, Version version) throws UnsupportedVersionException, IllegalArgumentException {

        requireNonNull(name, "name");
        requireNonNull(version, "version");

        VersionBridgeProvider provider = PROVIDERS.get(name);

        if (provider == null) {
            throw new IllegalArgumentException("No provider registered with name: " + name);
        }

        return provider.create(version);
    }
}

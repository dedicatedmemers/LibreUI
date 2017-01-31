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

package net.astromc.libreui.utils.version;

import java.util.Comparator;

/**
 * A {@link Version} based {@link Comparator}.
 */
//TODO: Javadoc
public enum VersionComparator implements Comparator<Version> {
    INSTANCE;

    /**
     * Backing {@link Version} {@link Comparator} instance.
     */
    private static final Comparator<Version> VERSION_COMPARATOR =
            Comparator.comparingInt(Version::getMajor)
                    .thenComparingInt(Version::getMinor)
                    .thenComparingInt(Version::getPatch);

    /**
     *
     *
     * @param first the first version to be compared.
     * @param second the second version to be compared.
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    @Override
    public int compare(Version first, Version second) {
        return VERSION_COMPARATOR.compare(first, second);
    }
}

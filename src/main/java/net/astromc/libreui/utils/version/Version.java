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

/**
 * Minimalistic Semantic Versioning 2.0.0 Java implementation.
 * This implementation only implements the MAJOR, MINOR and PATCH
 * format. Additional labels are not included. This class is used
 * to handle CraftBukkit package implementations for the
 * {@link net.astromc.libreui.bridge.VersionBridge} SPI
 * implementation to use.
 *
 * @see <a href="http://semver.org/">http://semver.org/</a>
 */
public final class Version implements Comparable<Version> {
    private final int major, minor, patch;

    private Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Returns the major identifier of this version
     *
     * @return major identifier of this version
     */
    public int getMajor() {
        return major;
    }

    /**
     * Returns the minor identifier of this version
     *
     * @return minor identifier of this version
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Returns the patch identifier of this version
     *
     * @return patch identifier of this version
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Compares a {@link Version} to this version. 1 is returned
     * if the version is higher. It is higher if it's major is
     * larger then this version's, if they are equal and the minor
     * is higher, or if they are equal but the patch is higher. If
     * all are equal 0 is returned, otherwise returns -1.
     *
     * This method delegates to {@link VersionComparator#INSTANCE}
     *
     * @param other the version being compared
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object
     */
    @Override
    public int compareTo(Version other) {
        return VersionComparator.INSTANCE.compare(this, other);
    }

    /**
     * Takes the major, minor and patch version identifiers
     * to and returns a new {@link Version} instance. An
     * {@link IllegalArgumentException} is thrown if any of
     * the parameters are invalid.
     *
     * @param major major version identifier
     * @param minor minor version identifier
     * @param patch patch version identifier
     * @return a new Version instance with the version identifiers
     * @throws IllegalArgumentException if major is less than 1, if
     *                                  minor is less than 0, or if
     *                                  patch is less than 0
     */
    public static Version from(int major, int minor, int patch)
            throws IllegalArgumentException {
        if (major < 0) {
            throw new IllegalArgumentException("major is < 0");
        } else if (minor < 0) {
            throw new IllegalArgumentException("minor is < 0");
        } else if (patch < 0) {
            throw new IllegalArgumentException("patch is < 0");
        }

        return new Version(major, minor, patch);
    }

    /**
     * Formats the MAJOR, MINOR and PATCH fields to the
     * <tt>${MAJOR}.${MINOR}.${PATCH}}</tt> format.
     *
     * @return returns a formatted MAJOR, MINOR and PATCH
     *         separated by dots
     */
    public String getFormattedVersion() {
        return String.format("%s.%s.%S", major, minor, patch);
    }

    @Override
    public String toString() {
        return "Version{"
                + "major=" + major +
                ", minor=" + minor +
                ", patch=" + patch +'}';
    }
}

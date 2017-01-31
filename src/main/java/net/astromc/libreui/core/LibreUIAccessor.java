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

import net.astromc.libreui.book.Book;
import net.astromc.libreui.bridge.VersionBridge;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * An accessor class which makes access and interacting with
 * {@link LibreUI}easier.
 */
public final class LibreUIAccessor {
    private final LibreUI libreUI;
    private final VersionBridge versionBridge;

    private LibreUIAccessor(LibreUI libreUI) {
        this.libreUI = libreUI;
        this.versionBridge = libreUI.getVersionBridge();
    }

    /**
     * Displays the specified <tt>book</tt> object to the
     * given <tt>player</tt>.
     *
     * @param player player displaying the book to
     * @param book the book being displayed
     */
    public void openBook(Player player, Book book) {
        this.versionBridge.openBook(player, book, this.libreUI);
    }

    /**
     * Instantiates a new {@link LibreUIAccessor} object
     * with a {@link LibreUI} instance.
     *
     * @param libreUI backing {@link LibreUI} instance
     * @return a new {@link LibreUIAccessor} instance
     */
    /* package-private */ static LibreUIAccessor newInstance(LibreUI libreUI) {
        return new LibreUIAccessor(Objects.requireNonNull(libreUI));
    }
}

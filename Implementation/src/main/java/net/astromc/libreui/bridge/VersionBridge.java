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

import net.astromc.libreui.LibreUIPlugin;
import net.astromc.libreui.api.book.Book;
import org.bukkit.entity.Player;

/**
 * A compatibility interface to allow the plugin to function
 * across multiple server versions, by creating version specific
 * implementation, rather than a plugin-static implementation.
 *
 * This design choice improves maintainability and greatly increases
 * backwards compatibility, along with creating much cleaner code,
 * by decoupling {@link Book} opening functionality from the rest
 * from the application.
 */
public interface VersionBridge {

    /**
     * Opens a book GUI with the data contained in the <tt>book</tt>. The
     * side effects from this method may change between implementations, and
     * is not guaranteed to be specific to a
     *
     * @param player player opening the book screen for
     * @param book book opening to the player
     * @param plugin the LibreUIPlugin plugin instance used to send the open book
     *                plugin message
     */
    void openBook(Player player, Book book, LibreUIPlugin plugin);
}

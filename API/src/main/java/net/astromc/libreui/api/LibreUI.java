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

package net.astromc.libreui.api;

import net.astromc.libreui.api.book.Book;
import org.bukkit.entity.Player;

/**
 * LibreUI service
 */
public interface LibreUI {
    /**
     * Displays the specified <tt>book</tt> object to the
     * given <tt>player</tt>.
     *
     * @param player player displaying the book to
     * @param book   the book being displayed
     */
    void openBook(Player player, Book book);
}

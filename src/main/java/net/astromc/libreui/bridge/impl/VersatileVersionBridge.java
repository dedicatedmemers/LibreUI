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

package net.astromc.libreui.bridge.impl;

import net.astromc.libreui.core.LibreUI;
import net.astromc.libreui.book.Book;
import net.astromc.libreui.bridge.VersionBridge;
import net.astromc.libreui.utils.messaging.BookOpenPluginMessageHandler;
import net.astromc.libreui.utils.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

/**
 * This is a versatile {@link VersionBridge} implementation designed to work on
 * all versions from Minecraft 1.7.2 (CB 1_7_R1) to Minecraft 1.11.2 (v1_11_R1).
 * This implementation may support versions outside from this scope, but is not
 * guaranteed. This class is package-private as it is not intended to by used to
 * any classes except for the {@link VersatileVersionBridgeProvider}.
 */
final class VersatileVersionBridge implements VersionBridge {
    private final Version implementationVersion;

    private VersatileVersionBridge(Version implementationVersion) {
        this.implementationVersion = implementationVersion;
    }

    /**
     * The specified <tt>book</tt> is sent and displayed to the
     * <tt>player</tt>, by creating a dummy {@link ItemStack} of
     * material type {@link Material#WRITTEN_BOOK}. The book's
     * JSON representation is then applied to that dummy object
     * using {@link org.bukkit.UnsafeValues#modifyItemStack(ItemStack, String)
     * Bukkit.getUnsafe().modifyItemStack}. The modifyItem takes
     * a JSON-like formatted String and converts it to NBT data,
     * which is then applied to the targeted {@link ItemStack}.
     * The current held item in hand is stored, and is replaced
     * with the dummy book {@link ItemStack}. The book is then opened
     * by invoking {@link BookOpenPluginMessageHandler#sendOpenBookMessageToPlayer(Player, Version)}
     * which sends the player a open book message, ultimately the
     * original item it restored in a <tt>finally</tt> statement
     * to assure it is always restored.
     *
     * @param player player opening the book screen for
     * @param book book opening to the player
     * @param libreUI the LibreUI plugin instance used to send the open book
     */
    @Override
    public void openBook(Player player, Book book, LibreUI libreUI) {
        ItemStack dummyWrittenBookItemStack = createDummyWrittenBookItemStackFromBook(book);

        PlayerInventory inventory = player.getInventory();
        ItemStack originalItem = inventory.getItemInHand();

        try {
            inventory.setItemInHand(dummyWrittenBookItemStack);

            libreUI.getBookOpenPluginMessageHandler()
                    .sendOpenBookMessageToPlayer(player, implementationVersion);
        } finally {
            inventory.setItemInHand(originalItem);
        }
    }

    /**
     * Constructs the dummy book {@link ItemStack} described in the
     * {@link VersatileVersionBridge#openBook(Player, Book, LibreUI)}
     * method documentation.
     *
     * @param book the book creating the dummy from
     * @return the dummy book {@link ItemStack}
     */
    private static ItemStack createDummyWrittenBookItemStackFromBook(Book book) {
        ItemStack dummyWrittenBookItemStack = new ItemStack(Material.WRITTEN_BOOK);
        String jsonRepresentation = book.getJsonRepresentation();

        dummyWrittenBookItemStack = Bukkit.getUnsafe()
                .modifyItemStack(dummyWrittenBookItemStack, jsonRepresentation);

        return dummyWrittenBookItemStack;
    }

    /**
     * Creates a new {@link VersatileVersionBridge} instance targeting
     * the CraftBukkit implementation version specified by the
     * <tt>implementationVersion</tt> parameter.
     *
     * @param implementationVersion CraftBukkit implementation this
     * {@link VersionBridge} instance is targeting
     * @return a new {@link VersatileVersionBridge} targeting the specified version
     */
    static VersatileVersionBridge newInstance(Version implementationVersion) {
        return new VersatileVersionBridge(Objects.requireNonNull(implementationVersion));
    }
}

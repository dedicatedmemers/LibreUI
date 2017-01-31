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

package net.astromc.libreui.utils.messaging;

import org.bukkit.entity.Player;

/**
 * Checked exception used to indicate that the a method
 * is unable to retrieve the channels of an object. This
 * is used by {@link ChannelsRetrieveStrategy#getChannels(Player)}.
 * This exception is only thrown if another exception occur
 * while trying to retrieve the channel. This will mostly
 * be exceptions thrown when incorrectly trying to retrieve
 * the channels reflectively.
 */
public final class CannotRetrieveChannelsException extends Exception {

    /**
     * Constructs a <tt>CannotRetrieveChannelsException</tt>
     * instance with a <tt>cause</tt>.
     *
     * @param cause exception which caused this exception
     *              to be thrown
     */
    public CannotRetrieveChannelsException(Throwable cause) {
        super(cause);
    }
}

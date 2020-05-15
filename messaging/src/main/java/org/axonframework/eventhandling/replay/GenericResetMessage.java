/*
 * Copyright (c) 2010-2020. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.eventhandling.replay;

import org.axonframework.messaging.GenericMessage;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDecorator;
import org.axonframework.messaging.MetaData;

import java.util.Map;

/**
 * Generic implementation of the {@link ResetMessage}.
 *
 * @param <T> the type of payload contained in the message
 * @author Steven van Beelen
 * @since 4.4
 */
public class GenericResetMessage<T> extends MessageDecorator<T> implements ResetMessage<T> {

    private static final long serialVersionUID = -6872386525166762225L;

    /**
     * Returns the given {@code messageOrPayload} as a {@link ResetMessage}. If {@code messageOrPayload} already
     * implements {@code ResetMessage}, it is returned as-is. Otherwise, the given {@code messageOrPayload} is wrapped
     * into a {@link GenericResetMessage} as its payload.
     *
     * @param messageOrPayload the payload to wrap as {@link ResetMessage}
     * @param <T>              the type of payload contained in the message
     * @return a {@link ResetMessage} containing given {@code messageOrPayload} as payload, or the {@code
     * messageOrPayload} if it already implements {@code ResetMessage}.
     */
    @SuppressWarnings("unchecked")
    public static <T> ResetMessage<T> asResetMessage(Object messageOrPayload) {
        return messageOrPayload instanceof ResetMessage
                ? (GenericResetMessage<T>) messageOrPayload
                : new GenericResetMessage<>((T) messageOrPayload);
    }

    /**
     * Instantiate a {@link GenericResetMessage} containing {@link ResetMessage#NO_RESET_PAYLOAD} and en empty {@link
     * MetaData} instance.
     */
    @SuppressWarnings("unchecked")
    public GenericResetMessage() {
        this((T) ResetMessage.NO_RESET_PAYLOAD);
    }

    /**
     * Instantiate a {@link GenericResetMessage} containing the given {@code payload} and en empty {@link MetaData}
     * instance.
     *
     * @param payload the payload be included
     */
    public GenericResetMessage(T payload) {
        this(payload, MetaData.emptyInstance());
    }

    /**
     * Instantiate a {@link GenericResetMessage} containing the given {@code payload} and {@link MetaData}.
     *
     * @param payload  the payload to be included
     * @param metaData the {@link MetaData} to be included
     */
    public GenericResetMessage(T payload, Map<String, ?> metaData) {
        this(new GenericMessage<>(payload, metaData));
    }

    /**
     * Create a {@link GenericResetMessage} from the given {@code delegate} message containing payload, metadata and
     * message identifier.
     *
     * @param delegate the delegate message
     */
    public GenericResetMessage(Message<T> delegate) {
        super(delegate);
    }

    @Override
    public GenericResetMessage<T> withMetaData(Map<String, ?> metaData) {
        return new GenericResetMessage<>(getDelegate().withMetaData(metaData));
    }

    @Override
    public GenericResetMessage<T> andMetaData(Map<String, ?> additionalMetaData) {
        return new GenericResetMessage<>(getDelegate().andMetaData(additionalMetaData));
    }

    @Override
    protected String describeType() {
        return "GenericResetMessage";
    }
}
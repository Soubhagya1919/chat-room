package com.soubhagya.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The MessageRequest class is used to represent a message sent by a user within a chat application.
 * It contains the message content, the sender's identity, and the room ID where the message is sent.
 *
 * This class is primarily used for transferring data from clients to the server when a message is sent.
 *
 * Attributes:
 * - content: The actual text content of the message.
 * - sender: The identifier or name of the message sender.
 * - roomId: The unique identifier of the chat room where the message is to be sent.
 *
 * The class is annotated with Lombok annotations to automatically generate
 * getter and setter methods, as well as constructors.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private String content;
    private String sender;
    private String roomId;
}

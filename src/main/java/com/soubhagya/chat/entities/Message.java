package com.soubhagya.chat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a message in a chat system.
 *
 * This class holds information about the sender, content, and timestamp of the message.
 * The timestamp is automatically set to the current time when the message is created using the
 * constructor with sender and content parameters.
 *
 * The class includes annotations from Project Lombok to automatically generate
 * constructors, getters, and setters.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    private String sender;
    private String content;
    private LocalDateTime timeStamp;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }
}

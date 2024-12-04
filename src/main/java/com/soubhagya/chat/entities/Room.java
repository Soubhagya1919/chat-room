package com.soubhagya.chat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * The Room class represents a chat room in the system. It holds information about the room's unique identifier and the collection of messages associated with it.
 *
 * It is annotated with @Document, indicating it is a MongoDB document stored in the "rooms" collection.
 * The Room class is designed to be used with Project Lombok, which automatically generates getters and setters through the @Getter and @Setter annotations.
 *
 * This class includes constructors for creating a new Room instance either with no arguments or with all properties specified.
 */
@Document(collection  = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String id;//Mongo db: unique identifier
    private String roomId;
    private List<Message> messages = new ArrayList<>();
}

package com.soubhagya.chat.repositories;

import com.soubhagya.chat.entities.Message;
import com.soubhagya.chat.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * RoomRepository is an interface for accessing and manipulating Room entities
 * within a MongoDB data store. It extends the MongoRepository interface, which
 * provides various methods for interacting with the database such as saving,
 * finding, deleting, etc.
 *
 * This repository is focused on Room entities, identified by a String ID.
 * Additional custom query methods are provided for retrieving specific Room
 * entities and their associated messages.
 */
public interface RoomRepository extends MongoRepository<Room, String> {
    //get room using room id
    Room findByRoomId(String roomId);

    // Add a method to fetch messages for a room with pagination
    Page<Message> findMessagesByRoomId(String roomId, Pageable pageable);
}

package com.soubhagya.chat.controller;

import com.soubhagya.chat.entities.Message;
import com.soubhagya.chat.entities.Room;
import com.soubhagya.chat.payload.MessageRequest;
import com.soubhagya.chat.repositories.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

/**
 * ChatController is a Spring MVC controller responsible for handling message-related operations in a chat application.
 * It manages sending and receiving messages within chat rooms, leveraging WebSocket communication for real-time interaction.
 *
 * This controller utilizes the RoomRepository to interact with Room entities in the data store, facilitating the retrieval
 * and storage of chat messages. It listens for incoming messages sent to specific endpoints and broadcasts them to the specified
 * destination topics.
 *
 * The controller is annotated with @Controller, indicating its role as a Spring controller component, and it uses annotations such
 * as @MessageMapping and @SendTo to define the message routing paths for WebSocket communication.
 */
@Controller
@CrossOrigin("http://localhost:3000")
public class ChatController {

    private final RoomRepository roomRepository;

    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Sends a message to a specific chat room and returns the message details.
     *
     * This method handles the creation and delivery of a message within a chat application.
     * It retrieves the room based on the provided room ID and adds the message to the room's
     * list of messages. If the room is found, the message is saved and broadcasted
     * to subscribers of the specified room topic.
     *
     * @param roomId the unique identifier of the chat room to which the message is sent
     * @param request the request payload containing the message content and sender details
     * @return the message that was sent, including the content, sender, and timestamp
     * @throws RuntimeException if the specified room is not found
     */
    //for both sending and receiving messages
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ) {
        Room room = roomRepository.findByRoomId(request.getRoomId());
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if(room != null) {
            room.getMessages().add(message);
            roomRepository.save(room);
        } else {
            throw new RuntimeException("Room not found!");
        }
        return message;
    }
}

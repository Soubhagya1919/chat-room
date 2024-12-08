package com.soubhagya.chat.controller;

import com.soubhagya.chat.entities.Message;
import com.soubhagya.chat.entities.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.soubhagya.chat.repositories.RoomRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * RoomController handles HTTP requests related to room operations.
 * This includes creating rooms, retrieving room details, and fetching messages associated with a room.
 */
@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin("http://localhost:5173")
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    
    /**
     * Creates a new room with the specified room ID. If a room with the given ID
     * already exists, it returns a bad request response.
     *
     * @param roomId the ID of the room to be created
     * @return a ResponseEntity containing the created Room object if successful,
     *         or a message indicating that the room already exists if not
     */
    //create room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {
        if(roomRepository.findByRoomId(roomId) != null) {
            return ResponseEntity.badRequest().body("Room already exists!");
        }
        Room room = new Room();
        room.setRoomId(roomId);
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    /**
     * Retrieves the details of a room based on the provided room ID.
     *
     * @param roomId the unique identifier of the room to be retrieved.
     * @return ResponseEntity containing the room details if found;
     *         a bad request response with an error message if the room does not exist.
     */
    //get room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if(room == null) {
            return ResponseEntity.badRequest()
                    .body("Room does not exist!");
        }
        return ResponseEntity.ok(room);
    }

    /**
     * Retrieves a list of messages for a specified room. Supports pagination through
     * the 'page' and 'size' parameters.
     *
     * @param roomId the unique identifier of the room whose messages are to be retrieved
     * @param page the page number of the messages to be retrieved, defaults to 0 if not provided
     * @param size the number of messages per page, defaults to 20 if not provided
     * @return a ResponseEntity containing a list of Message objects if the room exists;
     *         returns a bad request if the room does not exist
     */
    //get messages of room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        Optional<Room> roomOpt = Optional.ofNullable(roomRepository.findByRoomId(roomId));
        if (roomOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Room room = roomOpt.get();
        size = Math.max(1, size); // Ensure size is positive
        List<Message> paginatedMessages = paginateMessages(room.getMessages(), page, size);
/*      // Create a Pageable object to represent pagination information
        Pageable pageable = PageRequest.of(page, size);

        // Fetch the message page from the repository
        Page<Message> messagePage = roomRepository.findMessagesByRoomId(roomId, pageable);
 */
        return ResponseEntity.ok(paginatedMessages);
    }

    /**
     * Paginates a list of messages by selecting a subset of messages based on the specified page number and size.
     * The subset is defined in such a way that allows for navigating through pages of message history.
     *
     * @param messages the list of messages to paginate
     * @param page the page number to retrieve, with 0 being the first page
     * @param size the number of messages per page
     * @return a sublist of messages corresponding to the requested page, or an empty list if the page is out of bounds
     */
    private List<Message> paginateMessages(List<Message> messages, int page, int size) {
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        return start > end ? Collections.emptyList() : messages.subList(start, end);
    }

}

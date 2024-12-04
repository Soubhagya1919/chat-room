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
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    
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

    private List<Message> paginateMessages(List<Message> messages, int page, int size) {
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        return start > end ? Collections.emptyList() : messages.subList(start, end);
    }

}

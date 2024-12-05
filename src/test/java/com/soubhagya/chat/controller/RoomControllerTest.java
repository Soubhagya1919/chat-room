package com.soubhagya.chat.controller;

import com.soubhagya.chat.entities.Message;
import com.soubhagya.chat.entities.Room;
import com.soubhagya.chat.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RoomRepository roomRepository;

    @Test
    public void testGetMessages_RoomNotFound() throws Exception {
        when(roomRepository.findByRoomId(anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/testRoom/messages")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetMessages_EmptyMessages() throws Exception {
        Room room = new Room();
        room.setRoomId("testRoom");
        room.setMessages(new ArrayList<>());

        when(roomRepository.findByRoomId("testRoom")).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/testRoom/messages")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetMessages_WithMessages() throws Exception {
        Room room = new Room();
        room.setRoomId("testRoom");

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("sender1", "First message"));
        messages.add(new Message("sender2", "Second message"));
        room.setMessages(messages);

        when(roomRepository.findByRoomId("testRoom")).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/testRoom/messages")
                        .param("page", "0")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":2,\"content\":\"Second message\"},{\"id\":1,\"content\":\"First message\"}]"))
                .andDo(print());
    }
}
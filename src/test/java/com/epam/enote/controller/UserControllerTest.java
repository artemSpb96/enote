package com.epam.enote.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.enote.model.User;
import com.epam.enote.service.UserService;
import com.epam.enote.service.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private static final Long USER_ID_1 = 1L;
    private static final Long USER_ID_2 = 2L;
    private static final String NOT_FOUND_MESSAGE = "not found";

    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private User user1;

    private User user2;

    private List<User> users;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new ExceptionController())
            .build();

        mapper = new ObjectMapper();

        user1 = new User();
        user1.setId(USER_ID_1);
        user1.setName("name1");
        user1.setSurname("surname1");

        user2 = new User();
        user2.setId(USER_ID_2);
        user2.setName("name2");
        user2.setSurname("surname2");

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    public void getUsers_ok() throws Exception {
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
            .andDo(print())
            .andExpect(content().string(mapper.writeValueAsString(users)))
            .andExpect(status().isOk());

        verify(userService).getUsers();
    }

    @Test
    public void getUsers_notFound() throws Exception {
        when(userService.getUsers()).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        mockMvc.perform(get("/users"))
            .andDo(print())
            .andExpect(content().string(NOT_FOUND_MESSAGE))
            .andExpect(status().isNotFound());

        verify(userService).getUsers();
    }

    @Test
    public void addUser_created() throws Exception {
        user1.setId(null);
        when(userService.addUser(any())).thenReturn(USER_ID_1);

        mockMvc.perform(post("/users")
            .content(mapper.writeValueAsString(user1))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().string(String.valueOf(USER_ID_1)))
            .andExpect(status().isCreated());

        verify(userService).addUser(any(User.class));
    }

    @Test
    public void getUser_ok() throws Exception {
        when(userService.getUser(USER_ID_1)).thenReturn(user1);

        mockMvc.perform(get("/users/" + USER_ID_1))
            .andDo(print())
            .andExpect(content().string(mapper.writeValueAsString(user1)))
            .andExpect(status().isOk());

        verify(userService).getUser(USER_ID_1);
    }

    @Test
    public void getUser_noFound() throws Exception {
        when(userService.getUser(USER_ID_1)).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        mockMvc.perform(get("/users/" + USER_ID_1))
            .andDo(print())
            .andExpect(content().string(NOT_FOUND_MESSAGE))
            .andExpect(status().isNotFound());

        verify(userService).getUser(USER_ID_1);
    }

    @Test
    public void updateUser_ok() throws Exception {

        mockMvc.perform(put("/users/" + USER_ID_2)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user2)))
            .andDo(print())
            .andExpect(status().isOk());

        verify(userService).updateUser(eq(USER_ID_2), any(User.class));
    }

    @Test
    public void updateUser_notFound() throws Exception {
        doThrow(new NotFoundException(NOT_FOUND_MESSAGE)).when(userService)
            .updateUser(anyLong(), any());

        mockMvc.perform(put("/users/" + USER_ID_2)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user2)))
            .andDo(print())
            .andExpect(content().string(NOT_FOUND_MESSAGE))
            .andExpect(status().isNotFound());

        verify(userService).updateUser(eq(USER_ID_2), any(User.class));
    }

    @Test
    public void deleteUser_ok() throws Exception {

        mockMvc.perform(delete("/users/" + USER_ID_2))
            .andDo(print())
            .andExpect(status().isOk());

        verify(userService).deleteUser(USER_ID_2);
    }

    @Test
    public void deleteUser_notFound() throws Exception {
        doThrow(new NotFoundException(NOT_FOUND_MESSAGE)).when(userService)
            .deleteUser(anyLong());

        mockMvc.perform(delete("/users/" + USER_ID_2))
            .andDo(print())
            .andExpect(content().string(NOT_FOUND_MESSAGE))
            .andExpect(status().isNotFound());

        verify(userService).deleteUser(USER_ID_2);
    }
}
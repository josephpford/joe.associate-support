package learn.boardgames.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.boardgames.data.AppUserRepository;
import learn.boardgames.models.AppUser;
import learn.boardgames.models.Credentials;
import learn.boardgames.security.JwtConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @MockBean
    AppUserRepository repository;

    @Autowired
    MockMvc mvc;

    @MockBean
    JwtConverter converter;

    @Test
    void shouldSignInAndReturn200() throws Exception {
        Credentials credentials = new Credentials("john@smith.com", "P@ssw0rd!");
        AppUser appUser = new AppUser(1, "john@smith.com",
                "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", true, List.of("ADMIN"));

        when(repository.findByUsername("john@smith.com")).thenReturn(appUser);
        when(converter.getTokenFromUser(any())).thenReturn("FAKE_JWT");

        String token = converter.getTokenFromUser(appUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("jwt_token", token);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonIn = objectMapper.writeValueAsString(credentials);
        String jsonOut = objectMapper.writeValueAsString(map);

        var request = post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonOut));
    }
}
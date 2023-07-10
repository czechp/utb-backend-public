package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.command.UserRegisterCmd;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
class UserRegisterControllerTest {

    private static final String URL = "/api/users/register";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Register a new user  - ok")
    void registerUserTest() throws Exception {
        //given
        final UserRegisterCmd userRegisterCmd = new UserRegisterCmd("Some username", "testUser123@gmail.com", "Some password");
        String requestBody = objectMapper.writeValueAsString(userRegisterCmd);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Register a new user  - username already exists")
    void registerUserUsernameAlreadyExistsTest() throws Exception {
        //given
        String usedUsername = "UsedUsername";
        final UserRegisterCmd userRegisterCmd = new UserRegisterCmd(usedUsername, "someEmail@gmail.com", "Some password");
        String requestBody = objectMapper.writeValueAsString(userRegisterCmd);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Register a new user  - email already exists")
    void registerUserEmailAlreadyExistsTest() throws Exception {
        //given
        String usedEmail = "testUser@gmail.com";
        final UserRegisterCmd userRegisterCmd = new UserRegisterCmd("Brand new username", usedEmail, "Some password");
        String requestBody = objectMapper.writeValueAsString(userRegisterCmd);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
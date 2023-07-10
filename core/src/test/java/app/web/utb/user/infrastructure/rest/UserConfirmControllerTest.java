package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.command.UserConfirmCmd;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class UserConfirmControllerTest {
    private static final String URL = "/api/users/confirm";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Confirm user test - ok")
    void userConfirmTest() throws Exception {
        //given
        String confirmationToken = "user-confirm-token";
        UserConfirmCmd userConfirmCmd = new UserConfirmCmd(confirmationToken);
        String requestBody = objectMapper.writeValueAsString(userConfirmCmd);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Confirm user test - nok bad confirmation token")
    void userConfirmTestBadConfirmationToken() throws Exception {
        //given
        String confirmationToken = "user-confirm-token123";
        UserConfirmCmd userConfirmCmd = new UserConfirmCmd(confirmationToken);
        String requestBody = objectMapper.writeValueAsString(userConfirmCmd);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Confirm user test - nok token expired")
    void userConfirmTestTokenExpired() throws Exception {
        //given
        String confirmationToken = "user-confirm-token-expired";
        UserConfirmCmd userConfirmCmd = new UserConfirmCmd(confirmationToken);
        String requestBody = objectMapper.writeValueAsString(userConfirmCmd);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

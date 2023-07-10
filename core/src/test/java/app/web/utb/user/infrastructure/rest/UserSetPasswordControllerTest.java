package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.command.UserSetNewPasswordCmd;
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
class UserSetPasswordControllerTest {
    private static final String URL = "/api/users/set-new-password";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("UserSetNewPasswordController.setNewPassword() - ok")
    void setNewPasswordTest() throws Exception {
        //given
        String restoreToken = "1234-1234-1234";
        String newPassword = "new_password";
        UserSetNewPasswordCmd userSetNewPasswordCmd = new UserSetNewPasswordCmd(restoreToken, newPassword);
        String requestBody = objectMapper.writeValueAsString(userSetNewPasswordCmd);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

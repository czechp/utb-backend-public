package app.web.utb.user.infrastructure.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles({"test"})
@Transactional
@AutoConfigureMockMvc
class UserGenerateRestorePasswordTokenControllerTest {
    private static final String URL = "/api/users/password-restore";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("UserGenerateRestorePasswordTokenController.generateRestorePasswordToken() - ok")
    void generateRestorePasswordTokenTest() throws Exception {
        //given
        String email = "testUser@gmail.com";
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .param("email", email);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("UserGenerateRestorePasswordTokenController.generateRestorePasswordToken() - nok email not found")
    void generateRestorePasswordTokenEmailNotFoundTest() throws Exception {
        //given
        String email = "testUser123321@gmail.com";
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .param("email", email);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}

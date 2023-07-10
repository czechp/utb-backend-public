package app.web.utb.user.infrastructure.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class UserRemoveControllerTest {
    private static final String URL = "/api/users/{userId}";

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("UserRemoveController.removeUser  - ok")
    void removeUserTest() throws Exception {
        //given
        final long userId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.delete(URL, userId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    @DisplayName("UserRemoveController.removeUser  - nok only admin")
    void removeUserTestJustAdmin() throws Exception {
        //given
        final long userId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.delete(URL, userId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}

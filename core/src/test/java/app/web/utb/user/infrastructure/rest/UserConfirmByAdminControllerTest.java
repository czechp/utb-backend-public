package app.web.utb.user.infrastructure.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles({"test"})
@Transactional
@AutoConfigureMockMvc
class UserConfirmByAdminControllerTest {
    private static final String URL = "/api/users/confirm-by-admin";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("UserConfirmByAdminController.confirmByAdmin - ok")
    @WithMockUser(roles = {"ADMIN"})
    void confirmByAdminTest() throws Exception {
        //given
        final long userId = 1L;
        final boolean targetState = true;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/{userId}", userId)
                .param("activation", String.valueOf(targetState));
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("UserConfirmByAdminController.confirmByAdmin - nok just for admin")
    @WithMockUser(roles = {"USER"})
    void confirmByAdminJustForAdminTest() throws Exception {
        //given
        final long userId = 1L;
        final boolean targetState = true;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/{userId}", userId)
                .param("activation", String.valueOf(targetState));
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("UserConfirmByAdminController.confirmByAdmin - nok user not found")
    @WithMockUser(roles = {"ADMIN"})
    void confirmByAdminJustForAdminUserNotFoundTest() throws Exception {
        //given
        final long userId = Long.MAX_VALUE;
        final boolean targetState = true;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/{userId}", userId)
                .param("activation", String.valueOf(targetState));
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

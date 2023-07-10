package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.domain.UserRole;
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
@ActiveProfiles({"test"})
@Transactional
@AutoConfigureMockMvc
class UserAssignRoleControllerTest {
    private static final String URL = "/api/users/assign-role/{userId}";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("UserAssignRoleController.assignRole() - ok")
    @WithMockUser(roles = {"ADMIN"})
    void assignRoleTest() throws Exception {
        //given
        final long userId = 1L;
        final UserRole userRole = UserRole.ADMIN;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL, userId)
                .param("role", userRole.toString());
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("UserAssignRoleController.assignRole() - nok not found")
    @WithMockUser(roles = {"ADMIN"})
    void assignRoleUserNotFoundTest() throws Exception {
        //given
        final long userId = Long.MAX_VALUE;
        final UserRole userRole = UserRole.ADMIN;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL, userId)
                .param("role", userRole.toString());
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("UserAssignRoleController.assignRole() - nok only admin")
    @WithMockUser(roles = {"USER"})
    void assignRoleJustForAdminTest() throws Exception {
        //given
        final long userId = 1L;
        final UserRole userRole = UserRole.ADMIN;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL, userId)
                .param("role", userRole.toString());
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}

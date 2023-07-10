package app.web.utb.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserInfoAssignRoleTest {
    @Test
    @DisplayName("UserInfo.assignRole() - ok")
    void assignRoleTest() {
        //given
        final UserRole newRole = UserRole.ADMIN;
        UserInfo userInfo = UserInfo.builder()
                .withUsername("Some username")
                .withEmail("SomeEmail@gmail.com")
                .withPassword("SomePassword")
                .withRole(UserRole.USER)
                .build();
        //when
        UserInfo userInfoWithNewRole = userInfo.assignRole(newRole);
        //then
        assertEquals(newRole, userInfoWithNewRole.getRole());
    }


    @Test
    @DisplayName("UserInfo.assignRole() - nok role null")
    void assignRoleNullTest() {
        //given
        final UserRole newRole = null;
        UserInfo userInfo = UserInfo.builder()
                .withUsername("Some username")
                .withEmail("SomeEmail@gmail.com")
                .withPassword("SomePassword")
                .withRole(UserRole.USER)
                .build();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> userInfo.assignRole(newRole));
    }
}
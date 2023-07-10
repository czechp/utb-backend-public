package app.web.utb.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserActiveConfirmByAdminTest {
    @Test
    @DisplayName("UserActive.confirmedByAdmin() - ok")
    void confirmByAdminTest() {
        //given
        boolean targetState = true;
        UserActive userActive = UserActive.builder()
                .build();
        //when
        UserActive userAfterAdminConfirmation = userActive.confirmByAdmin(targetState);
        //then
        assertEquals(targetState, userAfterAdminConfirmation.isConfirmedByAdmin());
    }

    @Test
    @DisplayName("UserActive.isActive() - ok")
    void isActiveTest() {
        //given
        UserActive userActive = UserActive.builder()
                .withConfirmedByAdmin(true)
                .withConfirmed(true)
                .build();
        //when
        //then
        assertTrue(userActive.isActive());
    }


    @Test
    @DisplayName("UserActive.isActive() - nok not confirmed by admin")
    void isActiveTestNotConfirmedByAdmin() {
        //given
        UserActive userActive = UserActive.builder()
                .withConfirmedByAdmin(false)
                .withConfirmed(true)
                .build();
        //when
        //then
        assertFalse(userActive.isActive());
    }
}
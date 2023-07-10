package app.web.utb.configuration.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
class LoginController {
    private final AuthenticationManager authenticationManager;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        HashMap<String, String> responseBody = populateUserInfo(authenticate);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    private HashMap<String, String> populateUserInfo(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("email", userDetails.getEmail());
        responseBody.put("role", extractRoleFromUserDetails(userDetails));
        return responseBody;
    }

    private String extractRoleFromUserDetails(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.substring(5))
                .findFirst()
                .orElse("");
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginDto {
        private String login;
        private String password;
    }
}

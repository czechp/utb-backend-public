package app.web.utb.user.presentation;

import app.web.utb.exception.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
class UserViewController {
    private final UserViewJpaRepository userViewJpaRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    List<UserView> findAll(
            @PageableDefault(size = Integer.MAX_VALUE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return userViewJpaRepository.findAllUsersWithPagination(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    UserView findById(@PathVariable(name = "id") long userId) {
        return userViewJpaRepository.findById(userId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("Użytkownik z id: %s nie istnieje", userId)));
    }

}

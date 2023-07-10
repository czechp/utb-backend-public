package app.web.utb.user.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserViewJpaRepository extends JpaRepository<UserView, Long> {
    @Query(value = "select user from UserView user")
    List<UserView> findAllUsersWithPagination(Pageable pageable);

    Optional<UserView> findById(long id);
}

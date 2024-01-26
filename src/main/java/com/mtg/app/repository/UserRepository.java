package com.mtg.app.repository;

import com.mtg.app.models.Role;
import com.mtg.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
    @Query("SELECT u.authorities FROM User u WHERE u.userId = :userId")
    Set<Role> findRolesByUserId(@Param("userId") UUID userId);

}

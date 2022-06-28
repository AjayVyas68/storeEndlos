package com.storeendlos.user.Repository;

import com.storeendlos.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByPhoneno(String phoneno);
    Boolean existsByPhoneno(String phoneno);
}

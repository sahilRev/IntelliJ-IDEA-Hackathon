package com.assignmebt.techgig.Repository;

import com.assignmebt.techgig.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

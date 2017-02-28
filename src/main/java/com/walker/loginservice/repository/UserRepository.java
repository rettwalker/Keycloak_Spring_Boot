package com.walker.loginservice.repository;

import com.walker.loginservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jmw0705 on 2/7/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByUserName(String userName);
}

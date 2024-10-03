package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);
}

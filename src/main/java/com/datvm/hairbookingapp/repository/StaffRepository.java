package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Staff;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query(value = "SELECT code " +
            "FROM staff " +
            "ORDER BY code DESC " +
            "LIMIT 1", nativeQuery = true)
    @Transactional
    String findTheLatestStaffCode();

    Staff findStaffById(Long id);
    Staff findStaffByCode(String code);
}

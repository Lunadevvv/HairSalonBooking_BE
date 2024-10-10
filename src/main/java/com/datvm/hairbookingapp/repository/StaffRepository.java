package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Staff;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT TOP 1 s.id FROM Staff s ORDER BY s.id DESC")
    @Transactional
    String findTheLatestStaffCode();
}

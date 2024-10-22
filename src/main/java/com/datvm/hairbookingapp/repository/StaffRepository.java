package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.entity.enums.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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

    @Query("SELECT s FROM Staff s WHERE s.role = :role AND s NOT IN " +
            "(SELECT b.stylistId FROM Booking b WHERE b.slot.id = :slotId AND b.date = :date)")
    List<Staff> findAvailableStylists(@Param("slotId") Long slotId, @Param("date") LocalDate date, @Param("role") Role role);

    @Query("SELECT COUNT(s) AS stylist_count FROM Staff s WHERE s.role = ?1")
    int countStylist(Role role);
}

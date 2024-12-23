package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Salon;
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

    @Query("Select s From Staff s Where s.account = ?1")
    Staff findStaffByAccount(Account account);

    @Query("Select s From Staff s Where s.salons = ?1 and s.status = true")
    List<Staff> findBySalon(Salon salon);

    Staff findStaffByCode(String code);

    @Query("SELECT s FROM Staff s WHERE s.role = :role AND s.salons = :salon AND s.status = true AND s NOT IN " +
            "(SELECT b.stylistId FROM Booking b WHERE b.slot.id = :slotId AND b.date = :date)")
    List<Staff> findAvailableStylists(@Param("slotId") Long slotId, @Param("date") LocalDate date, @Param("role") Role role, @Param("salon")Salon salon);

    @Query("SELECT COUNT(s) AS stylist_count FROM Staff s WHERE s.role = ?1 AND s.salons = ?2")
    int countStylist(Role role, Salon salon);

    @Query("SELECT COUNT(s) AS staff_count FROM Staff s WHERE NOT s.role = 'ADMIN'")
    int countTotalStaff();

    @Query("SELECT COUNT(s) AS staff_count FROM Staff s WHERE s.salons = ?1 AND NOT s.role = 'ADMIN' AND NOT s.role = 'MANAGER'")
    int countTotalStaffBySalon(Salon salon);

    @Query("select s from Staff s where s.status = ?1 and not s.role = ?2")
    List<Staff> findAllActiveStaffs(boolean status, Role role);

    @Query("Select s from Staff s Order By s.ovrRating DESC LIMIT 5")
    List<Staff> topFiveStylistByRating();

    @Query("Select s from Staff s WHERE s.salons = ?1 AND NOT s.role = 'MANAGER' Order By s.ovrRating DESC LIMIT 5")
    List<Staff> topFiveStylistByRatingAndSalon(Salon salon);
}

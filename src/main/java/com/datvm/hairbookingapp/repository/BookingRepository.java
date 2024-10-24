package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Booking;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT MAX(b.id) FROM Booking b")
    String findLastId();

    @Query("SELECT b FROM Booking b WHERE b.account = :account ORDER BY b.id DESC LIMIT 1 ")
    Booking findLastBooking(@Param("account")Account account);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.date = ?1 AND b.slot.id = ?2")
    int countBookingInSlot(LocalDate date, Long slotId);

    @Query("UPDATE Booking b SET b.status = ?1 WHERE b.slot.id = ?2 AND b.status = ?3" )
    @Modifying
    @Transactional
    int updateBySpecificTime(BookingStatus newStatus ,Long slotId, BookingStatus status);

    @Query("Select b From Booking b Where b.account = ?1")
    List<Booking> findByAccount(Account account);
}

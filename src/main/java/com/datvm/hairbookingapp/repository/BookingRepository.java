package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT MAX(b.id) FROM Booking b")
    String findLastId();

    @Query("SELECT b FROM Booking b WHERE b.account = :account ORDER BY b.id DESC LIMIT 1 ")
    Booking findLastBooking(@Param("account")Account account);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.date = ?1 AND b.slot.id = ?2")
    int countBookingInSlot(LocalDate date, Long slotId);


//    Booking findTopByAccountOrderByDateDesc(Account account);
}

package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT MAX(b.id) FROM Booking b")
    String findLastId();

    @Query("SELECT b FROM Booking b WHERE b.account = :account ORDER BY b.id DESC LIMIT 1 ")
    Booking findLastBooking(@Param("account")Account account);

//    Booking findTopByAccountOrderByDateDesc(Account account);
}

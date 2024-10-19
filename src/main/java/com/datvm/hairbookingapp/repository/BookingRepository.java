package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT MAX(b.id) FROM Booking b")
    String findLastId();
}

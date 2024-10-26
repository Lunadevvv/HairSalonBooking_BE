package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Payment;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("SELECT MAX(p.id) FROM Payment p")
    String findLastId();
    @Query("select p from Payment p where p.booking.status = ?1")
    List<Payment> findAllPaymentAlreadyComplete(BookingStatus status);
}

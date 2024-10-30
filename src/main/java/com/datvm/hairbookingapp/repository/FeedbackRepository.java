package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Feedback;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.entity.enums.FeedbackStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    @Query("SELECT MAX(f.id) FROM Feedback f")
    String findLastId();
    @Query("select f from Feedback f left join Booking b on f.id = b.feedback.id where b.status = ?1 AND f.id = ?2")
    Feedback findFeedbackWithBookingCompleted(BookingStatus status, String feedbackId);
    @Query("select f from Feedback f left join Booking b on f.id = b.feedback.id where b.status = ?1")
    List<Feedback> findListFeedbackByBookingStatus(BookingStatus status);
    @Query("select f from Feedback f where f.status = ?1")
    List<Feedback> findAllByFeedbackStatus(FeedbackStatus status);
}

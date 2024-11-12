package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.FeedbackCreateRequest;
import com.datvm.hairbookingapp.dto.response.FeedbackResponse;
import com.datvm.hairbookingapp.entity.Feedback;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.entity.enums.FeedbackStatus;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.FeedbackMapper;
import com.datvm.hairbookingapp.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    StaffService staffService;

    public FeedbackResponse submitFeedback(FeedbackCreateRequest request) {
        Feedback feedback = feedbackRepository.findFeedbackWithBookingCompleted(BookingStatus.COMPLETED, request.getId());
        Staff stylist = feedback.getBooking().getStylistId();
        if(feedback == null)
            throw new AppException(ErrorCode.FEEDBACK_NOT_FOUND);
        if(feedback.getStatus() == FeedbackStatus.OPEN) {
            feedback.setFeedback(request.getFeedback());
            feedback.setRate(request.getRate());
            feedback.setStatus(FeedbackStatus.CLOSE);
            staffService.updateOveralRating(Double.parseDouble(request.getRate()),stylist);
        }else
            throw new AppException(ErrorCode.FEEDBACK_IS_DONE);
        return feedbackMapper.toFeedbackResponse(feedbackRepository.save(feedback));
    }

    public FeedbackResponse getFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));
        return feedbackMapper.toFeedbackResponse(feedback);
    }

    public List<FeedbackResponse> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findListFeedbackByBookingStatus(BookingStatus.COMPLETED);
        return feedbacks.stream().map(feedbackMapper::toFeedbackResponse).toList();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void autoCloseFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAllByFeedbackStatus(FeedbackStatus.OPEN);
        if(feedbacks.isEmpty())
            throw new AppException(ErrorCode.FEEDBACK_NOT_FOUND);
        else{
            for(Feedback feedback : feedbacks){
                feedback.setStatus(FeedbackStatus.CLOSE);
                feedbackRepository.save(feedback);
            }
        }
        System.out.println("system has auto close all open feedbacks");
    }
}

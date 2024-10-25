package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.FeedbackCreateRequest;
import com.datvm.hairbookingapp.dto.response.FeedbackResponse;
import com.datvm.hairbookingapp.entity.Feedback;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.FeedbackMapper;
import com.datvm.hairbookingapp.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackMapper feedbackMapper;

    public FeedbackResponse submitFeedback(FeedbackCreateRequest request) {
        Feedback feedback = feedbackRepository.findFeedbackWithBookingCompleted(BookingStatus.COMPLETED, request.getId());
        if(feedback != null) {
            feedback.setFeedback(request.getFeedback());
            feedback.setRate(request.getRate());
        }else
            throw new AppException(ErrorCode.FEEDBACK_NOT_FOUND);
        return feedbackMapper.toFeedbackResponse(feedbackRepository.save(feedback));
    }

    public FeedbackResponse getFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findFeedbackWithBookingCompleted(BookingStatus.COMPLETED, id);
        if(feedback == null)
            throw new AppException(ErrorCode.FEEDBACK_NOT_FOUND);
        return feedbackMapper.toFeedbackResponse(feedback);
    }

    public List<FeedbackResponse> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findListFeedbackWithBookingCompleted(BookingStatus.COMPLETED);
        return feedbacks.stream().map(feedbackMapper::toFeedbackResponse).toList();
    }

}

package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Booking;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FeedbackResponse {
    private String id;
    private String rate;
    private String feedback;
    private Booking booking;
}

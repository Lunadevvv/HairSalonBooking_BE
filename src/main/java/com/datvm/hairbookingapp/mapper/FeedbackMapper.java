package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.response.FeedbackResponse;
import com.datvm.hairbookingapp.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper
public interface FeedbackMapper {
    FeedbackResponse toFeedbackResponse(Feedback feedback);
}

package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.response.PaymentResponse;
import com.datvm.hairbookingapp.entity.Payment;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
    PaymentResponse toPaymentResponse(Payment payment);
}

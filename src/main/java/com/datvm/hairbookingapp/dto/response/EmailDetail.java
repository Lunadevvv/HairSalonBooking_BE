package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Account;
import lombok.Data;

@Data
public class EmailDetail {
    Account account;
    String subject;
    String link;
}

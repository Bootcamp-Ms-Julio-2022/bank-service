package com.nttdata.bank.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
public class Transaction {

    private String id;

    private String customerId;

    private String purchaseId;

    private String source;

    private String transactionType;

    private Date emittedAt;

    private Double amount;

    private String state;
}

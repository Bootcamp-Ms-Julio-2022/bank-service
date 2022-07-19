package com.nttdata.bank.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
public class Purchase {

    private String id;

    private String customerId;

    private String customerType;

    private String customerName;

    private String productId;

    private String productType;

    private String productCategory;

    private Date createdAt;

    private String state;

    private String accountNo;

    private Double balance;

    private Boolean HasCommissionPerMaintenance;

    private Double commissionPerMaintenancePercentage;

    private Boolean HasTransactionLimitPerMonth;

    private Integer transactionLimitPerMonthNumber;

    private Integer maxQtyOfCreditsAllowed;

    private Double creditLimitAmount;

    private Integer transactionsMadeByCustomerInCurrentMonth;

    private String purchaseSource;
}

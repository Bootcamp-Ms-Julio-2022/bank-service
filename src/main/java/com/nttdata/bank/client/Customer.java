package com.nttdata.bank.client;

import com.nttdata.bank.client.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
public class Customer {

    private String id;

    private CustomerType customerType;

    private String name;

    private String docType;

    private String docNumber;

    private Date createdAt;

    private String address;

    private String phoneNumber;

    private String state;

    private String email;

    private String imeiMobilePhoneNumber;

    private Date lastModifiedAt;

    private Integer ownedPasiveProductsQty = 0;

    private Integer ownedActiveProductsQty = 0;
}

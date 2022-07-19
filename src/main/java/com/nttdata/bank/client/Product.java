package com.nttdata.bank.client;

import com.nttdata.bank.client.enums.ProductCategory;
import com.nttdata.bank.client.enums.ProductType;
import lombok.Data;
import java.util.Date;

@Data
public class Product {

    private String id;

    private ProductType productType;

    private ProductCategory productCategory;

    private String state;

    private Date createdAt;
}

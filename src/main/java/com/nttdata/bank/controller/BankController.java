package com.nttdata.bank.controller;

import com.nttdata.bank.client.Customer;
import com.nttdata.bank.client.Product;
import com.nttdata.bank.client.Purchase;
import com.nttdata.bank.client.Transaction;
import com.nttdata.bank.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    //    PRODUCTS ********
    // -------------------Retrieve all products

    @GetMapping("/products")
    public Flux<Product> retrieveAllProducts() {
        log.info("Retrieving all products");
        return bankService.findAllProducts();
    }

    // -------------------Retrieve a product by category

    @GetMapping("/products/{category}")
    public Mono<Product> retrieveProductByCategory(@PathVariable("category") String category) {
        log.info("Retrieving product with category: " + category.toUpperCase());
        return bankService.findProductByCategory(category);
    }

    // -------------------Create a product

    @PostMapping("/products")
    public Mono<Product> saveProduct(@RequestBody Product product) {
        log.info("Registering new product - category: " + product.getProductCategory() + ", type: " + product.getProductType());
        return bankService.saveProduct(product);
    }

    // -------------------Update a product

    @PutMapping("/products")
    public Mono<ResponseEntity<Product>> updateProduct(@RequestBody Product product) {
        log.info("Updating product with id: " + product.getId());
        return bankService.updateProduct(product)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // -------------------Delete a product

    @DeleteMapping("/products/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("id") String id) {
        log.info("Deleting product with id: " + id);
        return bankService.deleteProduct(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //    CUSTOMERS ********
    // -------------------Retrieve all customers

    @GetMapping("/customers")
    public Flux<Customer> retrieveAllCustomers() {
        log.info("Retrieving all customers");
        return bankService.findAllCustomers();
    }

    // -------------------Retrieve all customers by type

    @GetMapping("/customers/type")
    public Flux<Customer> retrieveAllByType(@RequestParam(value = "type", required = false) String type) {
        log.info("Retrieving customers with type: " + type.toUpperCase());
        return type.isEmpty() ?
                bankService.findAllCustomers() :
                bankService.findAllCustomersByCustomerType(type.toUpperCase());
    }

    // -------------------Retrieve single customer by docNumber
    @GetMapping("/customers/{docNumber}")
    public Mono<ResponseEntity<Customer>> retrieveById(@PathVariable("docNumber") String docNumber) {
        log.info("Retrieving customer with doc-number: " + docNumber);
        Mono<Customer> customer = bankService.findCustomerByDocNumber(docNumber);
        return customer.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // -------------------Create a customer

    @PostMapping("/customers")
    public Mono<Customer> save(@RequestBody Customer customer) {
        log.info("Registering new customer - name: " + customer.getName() + ", type: " + customer.getCustomerType());
        return bankService.saveCustomer(customer);
    }

    // -------------------Update a customer

    @PutMapping("/customers")
    public Mono<ResponseEntity<Customer>> update(@RequestBody Customer customer) {
        log.info("Updating customer with id: " + customer.getId());
        return bankService.updateCustomer(customer)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // -------------------Delete a customer

    @DeleteMapping("/customers/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        log.info("Deleting customer with id: " + id);
        return bankService.deleteCustomer(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //    PURCHASES ********
    // -------------------Retrieve all purchases

    @GetMapping("/purchases")
    public Flux<Purchase> retrieveAllPurchases() {
        log.info("Retrieving all purchases");
        return bankService.findAllPurchases();
    }

    // -------------------Retrieve single purchase by id

    @GetMapping("/purchases/{id}")
    public Mono<ResponseEntity<Purchase>> retrievePurchaseById(@PathVariable("id") String id) {
        log.info("Retrieving purchase with id: " + id);
        Mono<Purchase> purchase = bankService.findPurchaseById(id);
        return purchase.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // -------------------Create a purchase

    @PostMapping("/purchases")
    public Mono<Purchase> savePurchase(@RequestBody Purchase purchase) {
        log.info("Registering new purchase - customer: " + purchase.getCustomerId() + ", product: " + purchase.getProductId());
        return bankService.savePurchase(purchase);
    }

    // -------------------Update a purchase

    @PutMapping("/purchases")
    public Mono<ResponseEntity<Purchase>> updatePurchase(@RequestBody Purchase purchase) {
        log.info("Updating purchase with id: " + purchase.getId());
        return bankService.updatePurchase(purchase)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // -------------------Delete a purchase

    @DeleteMapping("/purchases/{id}")
    public Mono<ResponseEntity<Void>> deletePurchase(@PathVariable("id") String id) {
        log.info("Deleting purchase with id: " + id);
        return bankService.deletePurchase(id)
                .map(p -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //    TRANSACTIONS ********
    // -------------------Retrieve all transactions

    @GetMapping("/transactions")
    public Flux<Transaction> retrieveAllTransactions() {
        log.info("Retrieving all transactions");
        return bankService.findAllTransactions();
    }

    // -------------------Retrieve single transaction by id

    @GetMapping("/transactions/{id}")
    public Mono<ResponseEntity<Transaction>> retrieveTransactionById(@PathVariable("id") String id) {
        log.info("Retrieving transaction with id: " + id);
        Mono<Transaction> transaction = bankService.findTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // -------------------Create a transaction

    @PostMapping("/transactions")
    public Mono<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        log.info("Registering new transaction - customer: " + transaction.getCustomerId() + ", purchase: " + transaction.getPurchaseId());
        return bankService.saveTransaction(transaction);
    }

    // -------------------Update a transaction

    @PutMapping("/transactions")
    public Mono<ResponseEntity<Transaction>> updateTransaction(@RequestBody Transaction transaction) {
        log.info("Updating transaction with id: " + transaction.getId());
        return bankService.updateTransaction(transaction)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // -------------------Delete a transaction

    @DeleteMapping("/transactions/{id}")
    public Mono<ResponseEntity<Void>> deleteTransaction(@PathVariable("id") String id) {
        log.info("Deleting transaction with id: " + id);
        return bankService.deleteTransaction(id)
                .map(p -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //    OPERATIONS ********
    @PostMapping("/operations/grantproduct")
    public Flux<Purchase> grantProductToCustomer(@RequestParam(value = "customerDocNumber") String customerDocNumber,
                                                 @RequestParam(value = "productCategory") String productCategory) {
        return bankService.grantProductToCustomer(customerDocNumber, productCategory);
    }

}

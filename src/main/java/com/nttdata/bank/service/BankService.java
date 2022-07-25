package com.nttdata.bank.service;

import com.nttdata.bank.client.Customer;
import com.nttdata.bank.client.Product;
import com.nttdata.bank.client.Purchase;
import com.nttdata.bank.client.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankService {

    //    PRODUCTS
    Flux<Product> findAllProducts();
    Mono<Product> findProductByCategory(String productCategory);
    Mono<Product> saveProduct(Product product);
    Mono<Product> updateProduct(Product product);
    Mono<Product> deleteProduct(String id);

    //    CUSTOMERS
    Flux<Customer> findAllCustomers();
    Flux<Customer> findAllCustomersByCustomerType(String type);
    Mono<Customer> findCustomerByDocNumber(String docNumber);
    Mono<Customer> saveCustomer(Customer customer);
    Mono<Customer> updateCustomer(Customer customer);
    Mono<Customer> deleteCustomer(String id);

    //    PURCHASES
    Flux<Purchase> findAllPurchases();
    Mono<Purchase> findPurchaseById(String id);
    Mono<Purchase> savePurchase(Purchase purchase);
    Mono<Purchase> updatePurchase(Purchase purchase);
    Mono<Purchase> deletePurchase(String id);

    //    TRANSACTIONS
    Flux<Transaction> findAllTransactions();
    Mono<Transaction> findTransactionById(String id);
    Mono<Transaction> saveTransaction(Transaction transaction);
    Mono<Transaction> updateTransaction(Transaction transaction);
    Mono<Transaction> deleteTransaction(String id);

    //    OPERATIONS
    Flux<Purchase> grantProductToCustomer(String customerDocNumber, String productCategory);
    Flux<Purchase> displayCustomerPurchases(String customerId);
    Flux<Transaction> deposit(String customerId, String purchaseId, double amount);
    Flux<Transaction> withdraw(String customerId, String purchaseId, double amount);

}

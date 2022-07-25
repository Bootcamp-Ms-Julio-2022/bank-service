package com.nttdata.bank.service.impl;

import com.nttdata.bank.client.Customer;
import com.nttdata.bank.client.Product;
import com.nttdata.bank.client.Purchase;
import com.nttdata.bank.client.Transaction;
import com.nttdata.bank.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class BankServiceImpl implements BankService {

    private final WebClient webClient;
    private static final String PRODUCTS_URI = "/products";
    private static final String CUSTOMERS_URI = "/customers";
    private static final String PURCHASES_URI = "/purchases";
    private static final String TRANSACTIONS_URI = "/transactions";

    public BankServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9000").build();
    }

    //    PRODUCTS

    @Override
    public Flux<Product> findAllProducts() {
        return webClient.get()
                .uri(PRODUCTS_URI)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    @Override
    public Mono<Product> findProductByCategory(String productCategory) {
        return webClient.get()
                .uri(PRODUCTS_URI + "/" + productCategory)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return webClient.post()
                .uri(PRODUCTS_URI)
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(PRODUCTS_URI)
                        .queryParam("id", product.getId())
                        .build())
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Override
    public Mono<Product> deleteProduct(String id) {
        return webClient.delete()
                .uri(PRODUCTS_URI + id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    //    CUSTOMERS

    @Override
    public Flux<Customer> findAllCustomers() {
        return webClient.get()
                .uri(CUSTOMERS_URI)
                .retrieve()
                .bodyToFlux(Customer.class);
    }

    @Override
    public Flux<Customer> findAllCustomersByCustomerType(String type) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMERS_URI + "/type")
                        .queryParam("type", type)
                        .build())
                .retrieve()
                .bodyToFlux(Customer.class);
    }

    @Override
    public Mono<Customer> findCustomerByDocNumber(String docNumber) {
        return webClient.get()
                .uri(CUSTOMERS_URI + "/" + docNumber)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    @Override
    public Mono<Customer> saveCustomer(Customer customer) {
        return webClient.post()
                .uri(CUSTOMERS_URI)
                .body(Mono.just(customer), Customer.class)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMERS_URI)
                        .queryParam("id", customer.getId())
                        .build())
                .body(Mono.just(customer), Customer.class)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    @Override
    public Mono<Customer> deleteCustomer(String id) {
        return webClient.delete()
                .uri(CUSTOMERS_URI + "/" + id)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    //    PURCHASES

    @Override
    public Flux<Purchase> findAllPurchases() {
        return webClient.get()
                .uri(PURCHASES_URI)
                .retrieve()
                .bodyToFlux(Purchase.class);
    }

    @Override
    public Mono<Purchase> findPurchaseById(String id) {
        return webClient.get()
                .uri(PURCHASES_URI + "/" + id)
                .retrieve()
                .bodyToMono(Purchase.class);
    }

    @Override
    public Mono<Purchase> savePurchase(Purchase purchase) {
        return webClient.post()
                .uri(PURCHASES_URI)
                .body(Mono.just(purchase), Purchase.class)
                .retrieve()
                .bodyToMono(Purchase.class);
    }

    @Override
    public Mono<Purchase> updatePurchase(Purchase purchase) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(PURCHASES_URI)
                        .queryParam("id", purchase.getId())
                        .build())
                .body(Mono.just(purchase), Purchase.class)
                .retrieve()
                .bodyToMono(Purchase.class);
    }

    @Override
    public Mono<Purchase> deletePurchase(String id) {
        return webClient.delete()
                .uri(PURCHASES_URI + "/" + id)
                .retrieve()
                .bodyToMono(Purchase.class);
    }

    //    TRANSACTIONS
    @Override
    public Flux<Transaction> findAllTransactions() {
        return webClient.get()
                    .uri(TRANSACTIONS_URI)
                .retrieve()
                .bodyToFlux(Transaction.class);
    }

    @Override
    public Mono<Transaction> findTransactionById(String id) {
        return webClient.get()
                .uri(TRANSACTIONS_URI + "/" + id)
                .retrieve()
                .bodyToMono(Transaction.class);
    }

    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return webClient.post()
                .uri(TRANSACTIONS_URI)
                .body(Mono.just(transaction), Transaction.class)
                .retrieve()
                .bodyToMono(Transaction.class);
    }

    @Override
    public Mono<Transaction> updateTransaction(Transaction transaction) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(TRANSACTIONS_URI)
                        .queryParam("id", transaction.getId())
                        .build())
                .body(Mono.just(transaction), Transaction.class)
                .retrieve()
                .bodyToMono(Transaction.class);
    }

    @Override
    public Mono<Transaction> deleteTransaction(String id) {
        return webClient.delete()
                .uri(TRANSACTIONS_URI + "/" + id)
                .retrieve()
                .bodyToMono(Transaction.class);
    }

    //    OPERATIONS

    @Override
    public Flux<Purchase> grantProductToCustomer(String customerDocNumber, String productCategory) {
        Purchase purchase = new Purchase();

        return webClient.get()
                .uri(CUSTOMERS_URI + "/" + customerDocNumber)
                .retrieve()
                .bodyToFlux(Customer.class)
                .next()
                .flatMap(customer -> {
                    String customerType = customer.getCustomerType().toString();
                    if (productCategory.equalsIgnoreCase("CUENTA_BANCARIA_AHORRO") ||
                    productCategory.equalsIgnoreCase("CUENTA_BANCARIA_CUENTA_CORRIENTE") ||
                            productCategory.equalsIgnoreCase("CUENTA_BANCARIA_PLAZO_FIJO")) {
                        customer.setOwnedPasiveProductsQty(customer.getOwnedPasiveProductsQty() + 1);
                    } else {
                        customer.setOwnedActiveProductsQty(customer.getOwnedActiveProductsQty() + 1);
                    }

                    purchase.setCustomerId(customer.getId());
                    purchase.setCustomerType(customer.getCustomerType().toString());
                    purchase.setCustomerName(customer.getName());
                    purchase.setProductCategory(productCategory);
                    return Mono.just(purchase);
                })
                .concatWith(webClient.get()
                        .uri(PRODUCTS_URI + "/" + productCategory)
                        .retrieve()
                        .bodyToFlux(Product.class)
                        .next()
                        .flatMap(product -> {
                            purchase.setProductId(product.getId());
                            purchase.setProductType(product.getProductType().toString());
                            return savePurchase(purchase);
                        }));

    }

    @Override
    public Flux<Transaction> deposit(String customerId, String purchaseId, double amount) {
        Transaction transaction = new Transaction();

        return webClient.get()
                .uri(CUSTOMERS_URI + "/just/" + customerId)
                .retrieve()
                .bodyToFlux(Customer.class)
                .next()
                .flatMap(customer -> {
                    transaction.setCustomerId(customer.getId());
                    transaction.setTransactionType("DEPOSIT");
                    transaction.setAmount(amount);
                    return Mono.just(transaction);
                })
                .concatWith(webClient.get()
                        .uri(PURCHASES_URI + "/customer/" + customerId)
                        .retrieve()
                        .bodyToFlux(Purchase.class)
                        .flatMap(purchase -> {
                            transaction.setPurchaseId(purchase.getId());
                            return saveTransaction(transaction);
                        }));
    }

    @Override
    public Flux<Transaction> withdraw(String customerId, String purchaseId, double amount) {
        Transaction transaction = new Transaction();

        return webClient.get()
                .uri(CUSTOMERS_URI + "/just/" + customerId)
                .retrieve()
                .bodyToFlux(Customer.class)
                .next()
                .flatMap(customer -> {
                    transaction.setCustomerId(customer.getId());
                    transaction.setTransactionType("WITHDRAWAL");
                    transaction.setAmount(amount);
                    return Mono.just(transaction);
                })
                .concatWith(webClient.get()
                        .uri(PURCHASES_URI + "/customer/" + customerId)
                        .retrieve()
                        .bodyToFlux(Purchase.class)
                        .flatMap(purchase -> {
                            transaction.setPurchaseId(purchase.getId());
                            return saveTransaction(transaction);
                        }));
    }

    @Override
    public Flux<Purchase> displayCustomerPurchases(String customerId) {
        return webClient.get()
                .uri(PURCHASES_URI + "/customer/" + customerId)
                .retrieve()
                .bodyToFlux(Purchase.class);
    }
}

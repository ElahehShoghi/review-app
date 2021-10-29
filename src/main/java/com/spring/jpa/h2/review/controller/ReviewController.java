package com.spring.jpa.h2.review.controller;

import com.spring.jpa.h2.review.constant.AcceptanceStatus;
import com.spring.jpa.h2.review.constant.ApiRoute;
import com.spring.jpa.h2.review.model.Comment;
import com.spring.jpa.h2.review.model.Product;
import com.spring.jpa.h2.review.model.Vote;
import com.spring.jpa.h2.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@CrossOrigin
@RestController
@RequestMapping(ApiRoute.REVIEW_PATH)
@AllArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(ApiRoute.PRODUCTS)
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(name = "productName", required = false) Optional<String> name) {
        try {
            List<Product> products = reviewService.getAllProducts(name);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(ApiRoute.PRODUCTS + "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Optional<Product> productData = reviewService.getProductById(id);
        return productData.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(ApiRoute.PRODUCTS)
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        try {
            Product _product = reviewService.createProduct(product);
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(ApiRoute.PRODUCTS + "/{productName}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productName") String productName, @RequestBody @Valid Product product) {
        Optional<Product> productData = reviewService.updateProduct(productName, product);
        return productData.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(ApiRoute.PRODUCTS + "/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            reviewService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(ApiRoute.PRODUCTS)
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            reviewService.deleteAllProducts();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(ApiRoute.COMMENTS + "/{acceptance-status}")
    public ResponseEntity<List<Comment>> findCommentsByAcceptanceStatus(@PathVariable("acceptance-status") AcceptanceStatus status) {
        try {
            List<Comment> comments = reviewService.findCommentsByAcceptanceStatus(status);
            if (comments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(ApiRoute.VOTES + "/{acceptance-status}")
    public ResponseEntity<List<Vote>> findVotesByAcceptanceStatus(@PathVariable("acceptance-status") AcceptanceStatus status) {
        try {
            List<Vote> votes = reviewService.findVotesByAcceptanceStatus(status);
            if (votes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(votes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(ApiRoute.PRODUCTS + "/{productName}" + ApiRoute.COMMENTS + ApiRoute.LAST)
    public ResponseEntity<List<Comment>> findLastCountOfAcceptedComments(@PathVariable(name = "productName") String productName,
                                                                         @RequestParam(name = "count", required = false, defaultValue = "3") int count) {
        Optional<List<Comment>> lastComments = reviewService.findLastCountOfAcceptedComments(productName, count);
        return lastComments.map(comments -> new ResponseEntity<>(comments, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(ApiRoute.PRODUCTS + "/{productName}" + ApiRoute.COMMENTS + ApiRoute.COUNT)
    public ResponseEntity<Long> findCountOfComments(@PathVariable(name = "productName") String productName) {
        Optional<Long> countOfComments = reviewService.findCountOfComments(productName);
        return countOfComments.map(count -> new ResponseEntity<>(count, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(ApiRoute.PRODUCTS + "/{productName}" + ApiRoute.VOTES + ApiRoute.AVERAGE)
    public ResponseEntity<Double> findAvgOfVotes(@PathVariable(name = "productName") String productName) {
        OptionalDouble avgOfVotes = reviewService.findAvgOfVotes(productName);
        return avgOfVotes.isPresent() ? new ResponseEntity<>(avgOfVotes.getAsDouble(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

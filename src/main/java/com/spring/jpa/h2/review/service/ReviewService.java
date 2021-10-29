package com.spring.jpa.h2.review.service;

import com.spring.jpa.h2.review.constant.AcceptanceStatus;
import com.spring.jpa.h2.review.model.Comment;
import com.spring.jpa.h2.review.model.Product;
import com.spring.jpa.h2.review.model.Vote;
import com.spring.jpa.h2.review.repository.CommentRepository;
import com.spring.jpa.h2.review.repository.ProductRepository;
import com.spring.jpa.h2.review.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    public List<Product> getAllProducts(Optional<String> name) {
        List<Product> products = new ArrayList();
        if (!name.isPresent())
            products.addAll(productRepository.findAll());
        else
            products.addAll(productRepository.findByNameContaining(name.get()));
        return products;
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(new Product(product.getName()));
    }

    public Optional<Product> updateProduct(String name, Product product) {
        Optional<Product> productData = productRepository.findByName(name);
        if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setComments(product.getComments());
            _product.setVotes(product.getVotes());
            return Optional.of(productRepository.save(_product));
        } else {
            return Optional.empty();
        }
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public List<Comment> findCommentsByAcceptanceStatus(AcceptanceStatus status) {
        return commentRepository.findCommentsByStatus(status);
    }

    public List<Vote> findVotesByAcceptanceStatus(AcceptanceStatus status) {
        return voteRepository.findVotesByStatus(status);
    }

    public Optional<List<Comment>> findLastCountOfAcceptedComments(String productName, int count) {
        Optional<Product> product = productRepository.findByName(productName);
        if (product.isPresent()) {
            List<Comment> comments = product.get().getComments();
            List<Comment> reversedSortedAcceptedComments = comments.stream()
                    .filter(comment -> comment.getStatus().equals(AcceptanceStatus.ACCEPTED))
                    .sorted((f1, f2) -> Long.compare(f2.getCreateDate().getTime(), f1.getCreateDate().getTime()))
                    .limit(count)
                    .collect(Collectors.toList());
            return Optional.of(reversedSortedAcceptedComments);
        }
        return Optional.empty();
    }

    public Optional<Long> findCountOfComments(String productName) {
        Optional<Product> product = productRepository.findByName(productName);
        if (product.isPresent()) {
            return Optional.of(product.get().getComments().stream()
                    .filter(comment -> comment.getStatus().equals(AcceptanceStatus.ACCEPTED))
                    .count());
        }
        return Optional.empty();
    }

    public OptionalDouble findAvgOfVotes(String productName) {
        Optional<Product> product = productRepository.findByName(productName);
        if (product.isPresent()) {
            return product.get().getVotes().stream()
                    .filter(vote -> vote.getStatus().equals(AcceptanceStatus.ACCEPTED))
                    .mapToLong(Vote::getScore)
                    .average();
        }
        return OptionalDouble.empty();
    }
}

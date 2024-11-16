package com.trimble.trimbleCar.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CarTransactionHistoryRepository extends JpaRepository<CarTransactionHistory,Integer> {

    @Query("""
        SELECT history
        FROM BookTransactionHistory history
        WHERE history.user.id= :userId
        """)
    Page<CarTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

    @Query("""
        SELECT history
        FROM BookTransactionHistory history
        WHERE history.book.owner.id= :userId
        """)
    Page<CarTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

    @Query("""
            SELECT
            (COUNT(*)>0) AS isBorrowed
            FROM BookTransactionHistory bookTransactionHistory
            WHERE bookTransactionHistory.user.id= :userId
            AND bookTransactionHistory.book.id= :bookId
            AND bookTransactionHistory.returnedApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);

    @Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.user.id= :userId
            AND transaction.book.id= :bookId
            AND transaction.returned= false
            AND transaction.returnedApproved= false
            """)
    Optional<CarTransactionHistory> findByBookAndUserId(Integer bookId, Integer userId);

    @Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.book.owner.id= :userId
            AND transaction.book.id= :bookId
            AND transaction.returned= true
            AND transaction.returnedApproved= false
            """)
    Optional<CarTransactionHistory> findByBookAndOwnerId(Integer bookId, Integer userId);
}

package com.minibank.repository;

import com.minibank.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(
      Long fromAccountId,
      Long toAccountId
  );
}

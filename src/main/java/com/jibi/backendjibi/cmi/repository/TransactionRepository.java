package com.jibi.backendjibi.cmi.repository;

import com.jibi.backendjibi.cmi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}

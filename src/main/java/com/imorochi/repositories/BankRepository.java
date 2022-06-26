package com.imorochi.repositories;

import com.imorochi.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    List<Bank> findAll();
    Optional<Bank> findById(Long id);
}
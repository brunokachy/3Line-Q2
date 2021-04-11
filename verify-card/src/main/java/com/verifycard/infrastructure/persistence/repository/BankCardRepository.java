package com.verifycard.infrastructure.persistence.repository;

import com.verifycard.infrastructure.persistence.entity.BankCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BankCardRepository extends CrudRepository<BankCardEntity, Long> {

    Optional<BankCardEntity> findBankCardByCardNumber(String cardNumber);

    @Query(value = "select b from BankCardEntity b")
    Page<BankCardEntity> getBankCards(Pageable pageable);
}
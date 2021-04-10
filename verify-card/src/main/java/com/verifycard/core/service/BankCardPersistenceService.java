package com.verifycard.core.service;

import com.verifycard.infrastructure.persistence.entity.BankCardEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BankCardPersistenceService extends CrudService<BankCardEntity, Long> {

    Optional<BankCardEntity> findBankCardByCardNumber(String cardNumber);

    Page<BankCardEntity> getBankCards(int startIndex, int size);
}

package com.verifycard.infrastructure.persistence.service;

import com.verifycard.core.exception.NotFoundException;
import com.verifycard.core.service.BankCardPersistenceService;
import com.verifycard.infrastructure.persistence.entity.BankCardEntity;
import com.verifycard.infrastructure.persistence.repository.BankCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankCardPersistenceServiceImpl implements BankCardPersistenceService {

    private final BankCardRepository bankCardRepository;

    @Override
    public Optional<BankCardEntity> findById(Long aLong) {
        return bankCardRepository.findById(aLong);
    }

    @Override
    public BankCardEntity getRecordById(Long aLong) throws RuntimeException {
        return findById(aLong).orElseThrow(() -> new NotFoundException("Record not found: BankCard with Id: " + aLong));
    }

    @Override
    public BankCardEntity saveRecord(BankCardEntity record) {
        return bankCardRepository.save(record);
    }

    @Override
    public Optional<BankCardEntity> findBankCardByCardNumber(String cardNumber) {
        return bankCardRepository.findBankCardByCardNumber(cardNumber);
    }

    @Override
    public Page<BankCardEntity> getBankCards(int startIndex, int size) {
        Pageable pageable = PageRequest.of(startIndex, size);
        return bankCardRepository.getBankCards(pageable);
    }
}

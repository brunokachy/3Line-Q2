package com.verifycard.core.service.impl;

import com.verifycard.core.service.BankCardPersistenceService;
import com.verifycard.core.service.CardStatService;
import com.verifycard.entrypoint.models.BankCardStatResponse;
import com.verifycard.infrastructure.persistence.entity.BankCardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardStatServiceImpl implements CardStatService {

    private final BankCardPersistenceService bankCardPersistenceService;

    @Override
    public BankCardStatResponse getBankCardStatistics(int start, int limit) {
        Page<BankCardEntity> bankCardPage = bankCardPersistenceService.getBankCards(start, limit);

        HashMap<String, Long> statMap = bankCardPage.get().collect(Collectors.toMap(BankCardEntity::getCardNumber, BankCardEntity::getRequestCount, (a, b) -> b, HashMap::new));

        BankCardStatResponse statResponse = new BankCardStatResponse();
        statResponse.setLimit(limit);
        statResponse.setPayload(statMap);
        statResponse.setSize(bankCardPage.getTotalElements());
        statResponse.setStart(start);
        statResponse.setSuccess(true);

        return statResponse;
    }
}

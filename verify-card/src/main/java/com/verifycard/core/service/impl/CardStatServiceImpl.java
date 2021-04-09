package com.verifycard.core.service.impl;

import com.verifycard.core.service.CardStatService;
import com.verifycard.entrypoint.models.BankCardStatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardStatServiceImpl implements CardStatService {

    @Override
    public BankCardStatResponse getBankCardStatistics(int start, int limit) {
        return null;
    }
}

package com.verifycard.core.service;

import com.verifycard.entrypoint.models.BankCardStatResponse;

public interface CardStatService {

    BankCardStatResponse getBankCardStatistics(int start, int limit);
}

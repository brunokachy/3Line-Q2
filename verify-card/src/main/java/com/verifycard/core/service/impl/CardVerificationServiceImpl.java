package com.verifycard.core.service.impl;

import com.verifycard.core.exception.BadRequestException;
import com.verifycard.core.models.RestClientResponse;
import com.verifycard.core.models.CardResolutionResponse;
import com.verifycard.core.service.BankCardPersistenceService;
import com.verifycard.core.service.CardServiceClient;
import com.verifycard.core.service.CardVerificationService;
import com.verifycard.entrypoint.models.BankCardVerificationResponse;
import com.verifycard.infrastructure.persistence.entity.BankCardEntity;
import com.verifycard.infrastructure.persistence.entity.enums.CardType;
import com.verifycard.infrastructure.persistence.entity.enums.SchemeType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardVerificationServiceImpl implements CardVerificationService {

    private final BankCardPersistenceService bankCardPersistenceService;
    private final CardServiceClient cardServiceClient;

    @Override
    public BankCardVerificationResponse verifyCard(String cardNumber) {

       validateCardNumber(cardNumber);

        if(cardNumber.length() > 6){
            cardNumber = cardNumber.substring(0, 6);
        }

        Optional<BankCardEntity> optionalBankCardEntity = bankCardPersistenceService.findBankCardByCardNumber(cardNumber);
        if(optionalBankCardEntity.isPresent()){
            BankCardEntity bankCard = optionalBankCardEntity.get();
            bankCard.setRequestCount(bankCard.getRequestCount() + 1);
            bankCard = bankCardPersistenceService.saveRecord(bankCard);
            return buildResponse(bankCard, true);
        }

        RestClientResponse<CardResolutionResponse> response = cardServiceClient.resolveCard(cardNumber);

        if(!response.isSuccess() || response.getStatusCode() != HttpStatus.OK.value() || response.getData() == null){
            return buildResponse(null, false);
        }

        CardResolutionResponse cardResolutionResponse = response.getData();

        BankCardEntity bankCard = new BankCardEntity();
        bankCard.setBank(cardResolutionResponse.getBank() == null ? "" : cardResolutionResponse.getBank().getName());
        bankCard.setCardNumber(cardNumber);
        bankCard.setScheme(StringUtils.isNotEmpty(cardResolutionResponse.getScheme()) ? SchemeType.valueOf(cardResolutionResponse.getScheme().toUpperCase()) : SchemeType.NOT_AVAILABLE);
        bankCard.setType(StringUtils.isNotEmpty(cardResolutionResponse.getType()) ? CardType.valueOf(cardResolutionResponse.getType().toUpperCase()) : CardType.NOT_AVAILABLE);
        bankCard.setRequestCount(1);
        bankCard = bankCardPersistenceService.saveRecord(bankCard);

        return buildResponse(bankCard, true);
    }

    private BankCardVerificationResponse buildResponse(BankCardEntity bankCard, boolean isSuccess){
        BankCardVerificationResponse response = new BankCardVerificationResponse();
        response.setSuccess(isSuccess);

        if (bankCard != null){
            BankCardVerificationResponse.PayLoad payLoad = new BankCardVerificationResponse.PayLoad();
            payLoad.setBank(bankCard.getBank());
            payLoad.setScheme(bankCard.getScheme().name());
            payLoad.setType(bankCard.getType().name());
            response.setPayload(payLoad);
        }
        return response;
    }

    private void validateCardNumber(String cardNumber){
        if(StringUtils.isEmpty(cardNumber)){
            throw new BadRequestException("Missing required detail: Card number.");
        }

        if(!StringUtils.isNumeric(cardNumber)){
            throw new BadRequestException("Card Number must be only digits");
        }

        if(cardNumber.length() < 6){
            throw new BadRequestException("Card Number must be minimum of 6 digits");
        }

    }
}

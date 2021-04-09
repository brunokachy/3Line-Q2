package com.verifycard.core.service.impl;

import com.verifycard.core.exception.BadRequestException;
import com.verifycard.core.models.BankCard;
import com.verifycard.core.service.CardVerificationService;
import org.apache.commons.lang3.StringUtils;

public class CardVerificationServiceImpl implements CardVerificationService {

    @Override
    public BankCard verifyCard(String cardNumber) {

        if(StringUtils.isEmpty(cardNumber)){
            throw new BadRequestException("Missing required detail: Card number.");
        }

        if(!StringUtils.isNumeric(cardNumber)){
            throw new BadRequestException("Card Number must be only digits");
        }

        if(cardNumber.length() < 6){
            throw new BadRequestException("Card Number must be minimum of 6 digits");
        }

        if(cardNumber.length() > 6){
            cardNumber = cardNumber.substring(0, 6);
        }



        return null;
    }
}

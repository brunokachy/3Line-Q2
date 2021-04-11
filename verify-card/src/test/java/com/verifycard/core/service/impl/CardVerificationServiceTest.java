package com.verifycard.core.service.impl;

import com.verifycard.core.exception.BadRequestException;
import com.verifycard.core.service.BankCardPersistenceService;
import com.verifycard.core.service.CardServiceClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CardVerificationServiceTest {

    @InjectMocks
    private CardVerificationServiceImpl cardVerificationService;

    @Mock
    private CardServiceClient cardServiceClient;

    @Mock
    private BankCardPersistenceService bankCardPersistenceService;

    @Test(expected = BadRequestException.class)
    public void givenEmptyCardNumberWhenVerifyCardThenThrowBadRequestException() {
        String cardNumber = "";

        when(cardVerificationService.verifyCard(cardNumber)).thenThrow(new BadRequestException(anyString()));
    }

    @Test(expected = BadRequestException.class)
    public void givenNonNumericCardNumberWhenVerifyCardThenThrowBadRequestException() {
        String cardNumber = "asdfgliwe";

        when(cardVerificationService.verifyCard(cardNumber)).thenThrow(new BadRequestException(anyString()));
    }

    @Test(expected = BadRequestException.class)
    public void givenCardNumberLessThanSixDigitsWhenVerifyCardThenThrowBadRequestException() {
        String cardNumber = "78478";

        when(cardVerificationService.verifyCard(cardNumber)).thenThrow(new BadRequestException(anyString()));
    }

//    @Test()
//    public void givenCardNumberWhenCardExistInDBThenReturnBankCardDetails() {
//        String cardNumber = "5399235";
//        BankCardEntity bankCard = new BankCardEntity();
//        bankCard.setBank("FIRST BANK OF NIGERIA PLC");
//        bankCard.setScheme(SchemeType.MASTERCARD);
//        bankCard.setType(CardType.CREDIT);
//        bankCard.setCardNumber(cardNumber);
//        bankCard.setId(1L);
//
//        when(bankCardPersistenceService.findBankCardByCardNumber(cardNumber)).thenReturn(Optional.of(bankCard));
//        BankCardVerificationResponse response = cardVerificationService.verifyCard(cardNumber);
//
//        assertEquals(response.getPayload().getBank(), "FIRST BANK OF NIGERIA PLC");
//        assertEquals(response.getPayload().getScheme(), "mastercard");
//        assertEquals(response.getPayload().getType(), "debit");
//        assertTrue(response.isSuccess());
//    }

}

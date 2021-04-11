package com.verifycard.core.service.impl;

import com.verifycard.core.service.BankCardPersistenceService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CardStatServiceTest {

    @InjectMocks
    private CardStatServiceImpl cardStatService;

    @Mock
    private BankCardPersistenceService bankCardPersistenceService;

}
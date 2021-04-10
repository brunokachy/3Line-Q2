package com.verifycard.infrastructure.persistence.entity;

import com.verifycard.infrastructure.persistence.entity.enums.CardType;
import com.verifycard.infrastructure.persistence.entity.enums.SchemeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_card")
public class BankCardEntity extends AbstractBaseEntity<Long>{

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchemeType scheme;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType type;

    private String bank;

    @Builder.Default
    private long requestCount = 1;

}

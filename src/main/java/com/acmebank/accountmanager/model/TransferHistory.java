package com.acmebank.accountmanager.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "transfer_history")
public class TransferHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "from_account_number")
    private String fromAccountNumber;

    @Column(name = "to_account_number")
    private String toAccountNumber;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @PrePersist
    protected void prePersist() {
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
    }
}

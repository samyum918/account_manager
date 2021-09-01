package com.acmebank.accountmanager.controller.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferAmountRequest {
    @NotNull
    String fromAccountNumber;
    @NotNull
    String toAccountNumber;
    @NotNull
    BigDecimal amount;
}

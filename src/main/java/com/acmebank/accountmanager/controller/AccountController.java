package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.controller.request.TransferAmountRequest;
import com.acmebank.accountmanager.exception.ApiBadRequestException;
import com.acmebank.accountmanager.exception.ApiResourceNotFoundException;
import com.acmebank.accountmanager.exception.UnknownErrorException;
import com.acmebank.accountmanager.model.BankAccount;
import com.acmebank.accountmanager.model.TransferHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/balance/{accNo}")
    public ObjectNode getAccountBalance(@PathVariable String accNo) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        EntityManager em = emf.createEntityManager();

        BankAccount bankAccount = getBankAccount(accNo, em);
        objectNode.put("balance", bankAccount.getAccountBalance().toString() + " " + bankAccount.getCurrency());

        return objectNode;
    }

    @PostMapping("/transfer-amount")
    public ObjectNode transferAmount(@RequestBody @Valid TransferAmountRequest request) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        EntityManager em = emf.createEntityManager();

        BankAccount fromBankAccount = getBankAccount(request.getFromAccountNumber(), em);
        BankAccount toBankAccount = getBankAccount(request.getToAccountNumber(), em);

        if(!request.getCurrency().equalsIgnoreCase(fromBankAccount.getCurrency())) {
            throw new ApiBadRequestException("Currency is not correct");
        }

        em.getTransaction().begin();
        if(fromBankAccount.getAccountBalance().compareTo(request.getAmount()) >= 0) {
            fromBankAccount.setAccountBalance(fromBankAccount.getAccountBalance().subtract(request.getAmount()));
            toBankAccount.setAccountBalance(toBankAccount.getAccountBalance().add(request.getAmount()));

            TransferHistory transferHistory = new TransferHistory();
            BeanUtils.copyProperties(request, transferHistory);

            try {
                em.merge(fromBankAccount);
                em.merge(toBankAccount);
                em.persist(transferHistory);
                em.flush();

                em.getTransaction().commit();
            } catch (Exception ex) {
                log.debug("Exception: ", ex);
                em.getTransaction().rollback();
                throw new UnknownErrorException("Unknown error");
            }
        }
        else {
            em.getTransaction().rollback();
            throw new ApiBadRequestException("Account balance doesn't have enough money");
        }

        objectNode.put("status", "success");
        return objectNode;
    }

    BankAccount getBankAccount(String accNo, EntityManager em) {
        List<BankAccount> bankAccountList = em.createQuery("select ba from BankAccount ba where ba.accountNumber = :accNo", BankAccount.class)
                .setParameter("accNo", accNo).getResultList();
        if(bankAccountList.size() >= 1) {
            return bankAccountList.get(0);
        }
        else {
            throw new ApiResourceNotFoundException("Account Number cannot be found: " + accNo);
        }
    }
}

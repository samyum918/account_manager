package com.acmebank.accountmanager.repository;

import com.acmebank.accountmanager.model.BankAccount;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepositoryImplementation<BankAccount, Integer> {
}

package com.acmebank.accountmanager.repository;

import com.acmebank.accountmanager.model.TransferHistory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferHistoryRepository extends JpaRepositoryImplementation<TransferHistory, Integer> {
}

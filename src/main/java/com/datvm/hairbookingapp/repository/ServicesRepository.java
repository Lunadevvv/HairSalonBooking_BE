package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Services;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services, String>, JpaSpecificationExecutor<Services> {

    @Query("select s from Services s where s.status = ?1")
    List<Services> findAllActiveServices(boolean status);

    @Query("SELECT MAX(s.serviceId) FROM Services s")
    String findLastId();
}

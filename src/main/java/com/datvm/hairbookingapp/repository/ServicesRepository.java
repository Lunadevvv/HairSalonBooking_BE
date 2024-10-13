package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Services;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, String>, JpaSpecificationExecutor<Services> {
    List<Services> findAll();
    Services findByServiceId(String id);
    @Transactional
    void deleteByServiceId(String serviceId);

    @Query("SELECT MAX(s.serviceId) FROM Services s")
    String findLastId();
}

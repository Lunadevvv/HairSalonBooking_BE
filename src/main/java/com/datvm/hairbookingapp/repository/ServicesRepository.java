package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Services;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>, JpaSpecificationExecutor<Services> {
    List<Services> findAll();
    Services findByServiceId(Long id);
    @Transactional
    void deleteByServiceId(Long serviceId);

}

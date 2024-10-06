package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.entity.Services;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>, JpaSpecificationExecutor<Services> {

    List<Services> findAll();
    Services findServicesByServiceId(Long id);
    @Modifying
    @Transactional
    @Query("UPDATE Services s SET s.serviceName = ?2, s.description = ?3, " +
            "s.price = ?4, s.duration = ?5, s.categories = ?6 WHERE s.serviceId = ?1")
    int updateServicesByServiceId(Long serviceId, String serviceName, String description
            , BigDecimal price, String duration, Category categories);
    @Transactional
    void deleteServiceByServiceId(Long serviceId);

}

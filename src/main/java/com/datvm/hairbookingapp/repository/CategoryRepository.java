package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findByCategoryId(Long id);
    @Transactional
    void deleteByCategoryId(Long id);
}

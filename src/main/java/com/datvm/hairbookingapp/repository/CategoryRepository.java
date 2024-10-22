package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAll();
    Category findByCategoryId(String id);
    @Transactional
    void deleteByCategoryId(String id);
    @Query("SELECT MAX(s.categoryId) FROM Category s")
    String findLastId();
}

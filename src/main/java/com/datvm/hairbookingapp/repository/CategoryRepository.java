package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findCategoryByCategoryId(Long id);
    @Modifying // thực hiện truy vấn query
    @Transactional // cho phép thay đổi dữ liệu
    @Query("UPDATE Category u SET u.categoryName = ?2, u.categoryDescription = ?3 WHERE u.categoryId = ?1")
    int updateCategoryById(Long categoryId, String categoryName, String categoryDescription);
    @Transactional
    void deleteCategoryByCategoryId(Long id);
    List<Category> findByCategoryNameContaining(String categoryName);
}

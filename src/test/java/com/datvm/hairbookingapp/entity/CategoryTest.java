package com.datvm.hairbookingapp.entity;

import com.datvm.hairbookingapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class CategoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Test
    @Transactional
    @Rollback(false)
    void addTest(){
//        Category category = new Category();
//
//        category.setCategoryId(01L);
//        category.setCategoryName("Haircut");
//        category.setCategoryDescription("Cắt tóc");
//
//        Category category2 = new Category();
//        category2.setCategoryId(02L);
//        category2.setCategoryName("Hair Styling");
//        category2.setCategoryDescription("tạo mẫu");
//
//        Category category3 = new Category();
//        category3.setCategoryId(03L);
//        category3.setCategoryName("Hair Care");
//        category3.setCategoryDescription("Gội đầu");
//
//        Category category4 = new Category();
//        category4.setCategoryId(04L);
//        category4.setCategoryName("Massage");
//        category4.setCategoryDescription("Mát xa");
//
//        categoryRepository.save(category);
//        categoryRepository.save(category2);
//        categoryRepository.save(category3);
//        categoryRepository.save(category4);
    }
}

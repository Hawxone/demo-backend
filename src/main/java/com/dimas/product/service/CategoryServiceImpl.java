package com.dimas.product.service;


import com.dimas.product.entity.CategoryEntity;
import com.dimas.product.model.Category;
import com.dimas.product.model.Product;
import com.dimas.product.repository.CategoryEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryEntityRepository categoryEntityRepository;

    public CategoryServiceImpl(CategoryEntityRepository categoryEntityRepository) {
        this.categoryEntityRepository = categoryEntityRepository;
    }

    @Override
    public List<Category> getCategory() {

        List<CategoryEntity> categoryEntities = categoryEntityRepository.findAll();

        List<Category> categories = new ArrayList<>();

        categories = categoryEntities.stream().map((categoryEntity)->
                Category.builder()
                        .id(categoryEntity.getId())
                        .name(categoryEntity.getName())
                        .build())
                .collect(Collectors.toList());


        return categories;
    }
}

package com.dimas.product.service;

import com.dimas.product.entity.TagEntity;
import com.dimas.product.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TagService {
    List<Tag> getAllTags();

    List<Tag> getAllTagsWithBlog();

    TagEntity getTagById(String id);
}

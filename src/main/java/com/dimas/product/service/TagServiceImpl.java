package com.dimas.product.service;

import com.dimas.product.entity.TagEntity;
import com.dimas.product.model.Blog;
import com.dimas.product.model.Tag;
import com.dimas.product.repository.TagEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private TagEntityRepository tagEntityRepository;

    public TagServiceImpl(TagEntityRepository tagEntityRepository) {
        this.tagEntityRepository = tagEntityRepository;
    }

    @Override
    public List<Tag> getAllTags() {


        List<TagEntity> tagEntities = tagEntityRepository.findAll();
        List<Tag> tags = new ArrayList<>();

                tags = tagEntities.stream().map((tagEntity)->
                Tag.builder()
                        .id(tagEntity.getId())
                        .value(tagEntity.getId())
                        .label(tagEntity.getLabel())
                        .build()).collect(Collectors.toList());

        return tags;
    }

    @Override
    @Transactional
    public List<Tag> getAllTagsWithBlog() {

        List<TagEntity> tagEntities = tagEntityRepository.findAll();
        List<Tag> tags = new ArrayList<>();

        tags = tagEntities.stream().map((tagEntity)->
                Tag.builder()
                        .id(tagEntity.getId())
                        .value(tagEntity.getId())
                        .label(tagEntity.getLabel())
                        .blogs(tagEntity.getBlogs().stream().map((blogEntity)->
                                Blog.builder()
                                .id(blogEntity.getId())
                                .title(blogEntity.getTitle())
                                .image(blogEntity.getImage())
                                .posted(blogEntity.getPosted())
                                .content(blogEntity.getContent())
                                .subtitle(blogEntity.getSubtitle()).build()
                        ).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());

        return tags;
    }

    @Override
    public TagEntity getTagById(String id) {

        return tagEntityRepository.findById(id).get();
    }
}

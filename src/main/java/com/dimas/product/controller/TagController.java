package com.dimas.product.controller;


import com.dimas.product.entity.TagEntity;
import com.dimas.product.model.Blog;
import com.dimas.product.model.Tag;
import com.dimas.product.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTags(){

        return tagService.getAllTags();
    }

    @GetMapping("/all")
    public List<Tag> getAllTagsWithBlogs(){
        return tagService.getAllTagsWithBlog();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Tag> getTagById(@PathVariable("id") String id){

        TagEntity tagEntity = tagService.getTagById(id) ;
        Tag tag = new Tag();


        tag.setValue(tagEntity.getId());
        tag.setLabel(tagEntity.getLabel());
        tag.setId(tagEntity.getId());
        tag.setBlogs(tagEntity.getBlogs().stream().map((blog)->
                Blog.builder()
                        .title(blog.getTitle())
                        .content(blog.getContent())
                        .id(blog.getId())
                        .posted(blog.getPosted())
                        .imageUrl(blog.getImageUrl())
                        .subtitle(blog.getSubtitle())
                        .build()).collect(Collectors.toList()));

        return ResponseEntity.ok(tag);
    }
}

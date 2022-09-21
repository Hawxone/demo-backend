package com.dimas.product.service;

import com.dimas.product.entity.BlogEntity;
import com.dimas.product.entity.TagEntity;
import com.dimas.product.model.Blog;
import com.dimas.product.model.Tag;
import com.dimas.product.repository.BlogEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogEntityRepository blogEntityRepository;

    private final TagService tagService;

    public BlogServiceImpl(BlogEntityRepository blogEntityRepository, TagService tagService) {
        this.blogEntityRepository = blogEntityRepository;
        this.tagService = tagService;
    }

    @Override
    public List<Blog> getAllBlogs() {

        List<BlogEntity> blogEntities = blogEntityRepository.findAll();

        List<Blog> blogs = new ArrayList<>();

        blogs = blogEntities.stream().map((blogEntity)->
                Blog.builder()
                        .id(blogEntity.getId())
                        .title(blogEntity.getTitle())
                        .image(blogEntity.getImage())
                        .content(blogEntity.getContent())
                        .posted(blogEntity.getPosted())
                        .tags(blogEntity.getTags().stream().map((tagEntity)->
                                Tag.builder()
                                        .id(tagEntity.getId())
                                        .value(tagEntity.getId())
                                        .label(tagEntity.getLabel())
                                        .build()
                        ).collect(Collectors.toList()))
                        .subtitle(blogEntity.getSubtitle()).build()).collect(Collectors.toList());

        return blogs;
    }

    @Override
    public Blog saveBlog(Blog saveBlog) throws Exception {

      try {

          BlogEntity blogEntity = new BlogEntity();

          blogEntity.setContent(saveBlog.getContent());
          blogEntity.setId(null);
          blogEntity.setImage(saveBlog.getFile());
          blogEntity.setPosted(saveBlog.getPosted());
          blogEntity.setSubtitle(saveBlog.getSubtitle());
          blogEntity.setTitle(saveBlog.getTitle());

          blogEntity.getTags().addAll(saveBlog.getTags().stream().map(tag -> {
              TagEntity tagEntity = tagService.getTagById(tag.getId());
              tagEntity.getBlogs().add(blogEntity);
              return tagEntity;
          }).toList());

          blogEntityRepository.save(blogEntity);
          saveBlog.setId(blogEntity.getId());
          saveBlog.setFile(null);
          saveBlog.setImage(blogEntity.getImage());


      }catch (Exception e){
          throw new Exception("could not save post : "+ e);
      }

        return saveBlog;
    }

    @Override
    @Transactional
    public Blog getBlogById(String id) {

        BlogEntity blogEntity = blogEntityRepository.findById(id).get();

        Blog blog = new Blog();
        blog.setImage(blogEntity.getImage());
        blog.setTags(blogEntity.getTags().stream().map((tagEntity)->
                Tag.builder()
                        .id(tagEntity.getId())
                        .value(tagEntity.getId())
                        .label(tagEntity.getLabel())
                        .build()
                ).collect(Collectors.toList()));
        blog.setContent(blogEntity.getContent());
        blog.setPosted(blogEntity.getPosted());
        blog.setSubtitle(blogEntity.getSubtitle());
        blog.setTitle(blogEntity.getTitle());
        blog.setId(blogEntity.getId());


        return blog;
    }

    @Override
    @Transactional
    public Blog updateBlog(Blog updateBlog, String id) throws Exception {
        try {
            BlogEntity blogEntity = blogEntityRepository.findById(id).get();
            System.out.println(blogEntity.getTitle());
            blogEntity.setPosted(new Date().toString());
            blogEntity.setContent(updateBlog.getContent());
            blogEntity.setImage(updateBlog.getImage());
            blogEntity.setTitle(updateBlog.getTitle());
            blogEntity.setSubtitle(updateBlog.getSubtitle());

            blogEntity.getTags().addAll(updateBlog.getTags().stream().map(tag -> {
                TagEntity tagEntity = tagService.getTagById(tag.getId());
                tagEntity.getBlogs().add(blogEntity);
                return tagEntity;
            }).toList());

            System.out.println(blogEntity);


        }catch (Exception e){
            throw new Exception("could not update post : "+ e);
        }

        return updateBlog;
    }
}

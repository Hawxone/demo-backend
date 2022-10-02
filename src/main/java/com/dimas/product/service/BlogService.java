package com.dimas.product.service;

import com.dimas.product.model.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    List<Blog> getAllBlogs();

    Blog saveBlog(Blog saveBlog) throws Exception;

    Blog getBlogById(String id);

    Blog updateBlog(Blog updateBlog, String id) throws Exception;

    boolean deleteBlog(String id);

    Map<String,Object> getAllBlogsPaginated(int page, int size);
}

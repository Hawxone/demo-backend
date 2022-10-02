package com.dimas.product.controller;
import com.dimas.product.model.Blog;
import com.dimas.product.model.Tag;
import com.dimas.product.service.BlogService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<Map<?,?>> getAllBlogs(){

        Map<String,Object> response = new HashMap<>();

        List<Blog> blogs = blogService.getAllBlogs();

        response.put("blogs",blogs);
        response.put("currentPage",0);
        response.put("totalItems",0);
        response.put("totalpages",0);


        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Map<String,Object>> getAllBlogsPaginated(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){

        Map<String,Object> blogs = blogService.getAllBlogsPaginated(page,size);
        System.out.println(page);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable("id") String id){


        Blog blog = blogService.getBlogById(id);

        return ResponseEntity.ok(blog);
    }

    @PostMapping
    public Blog saveBlog(@RequestParam Map<String,String> requestParam) throws Exception {

        String title = requestParam.get("title");
        String subtitle = requestParam.get("subtitle");
        String content = requestParam.get("content");
        String imageUrl = requestParam.get("image");
        String tags = requestParam.get("tags");



        JsonArray ja = new Gson().fromJson(tags,JsonArray.class);
        List<Tag> tagList = new ArrayList<>();


        for (int index = 0; index < ja.size();index++){
            Tag tag = new Tag();
            JsonObject convertedObject = new Gson().fromJson(ja.get(index), JsonObject.class);

            tag.setId(convertedObject.get("id").getAsString());
            tag.setLabel(convertedObject.get("label").getAsString());
            tag.setValue(convertedObject.get("value").getAsString());
            tag.setBlogs(null);

            tagList.add(tag);

        }

        Blog saveBlog = Blog.builder()
                .id(null)
                .imageUrl(imageUrl)
                .posted(new Date().toString())
                .title(title)
                .content(content)
                .subtitle(subtitle)
                .tags(tagList)
                .build();

        saveBlog = blogService.saveBlog(saveBlog);


        return saveBlog;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") String id,@RequestParam Map<String,String> requestParam) throws Exception {

        String title = requestParam.get("title");
        String subtitle = requestParam.get("subtitle");
        String content = requestParam.get("content");
        String file = requestParam.get("file");
        String tags = requestParam.get("tags");

        JsonArray ja = new Gson().fromJson(tags,JsonArray.class);
        List<Tag> tagList = new ArrayList<>();

        for (int index = 0; index < ja.size();index++){
            Tag tag = new Tag();
            JsonObject convertedObject = new Gson().fromJson(ja.get(index), JsonObject.class);

            tag.setId(convertedObject.get("id").getAsString());
            tag.setLabel(convertedObject.get("label").getAsString());
            tag.setValue(convertedObject.get("value").getAsString());
            tag.setBlogs(null);

            tagList.add(tag);

        }

        Blog updateBlog = Blog.builder()
                .id(null)
                .file(file)
                .posted(new Date().toString())
                .title(title)
                .content(content)
                .subtitle(subtitle)
                .tags(tagList)
                .build();

        updateBlog = blogService.updateBlog(updateBlog,id);

        return ResponseEntity.ok(updateBlog);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteBlog(@PathVariable("id") String id){

        boolean deleted = false;
        deleted = blogService.deleteBlog(id);
        Map<String,Boolean> response =  new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }

}

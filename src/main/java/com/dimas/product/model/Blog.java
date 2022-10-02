package com.dimas.product.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    String id;
    String title;
    String subtitle;
    String content;
    String imageUrl;
    String file;
    String posted;
    List<Tag> tags;

}

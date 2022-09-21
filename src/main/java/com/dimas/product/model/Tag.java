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
public class Tag {

    private String id;
    private String value;
    private String label;
    private List<Blog> blogs;


}

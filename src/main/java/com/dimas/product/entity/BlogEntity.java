package com.dimas.product.entity;


import com.dimas.product.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="blogs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    String id;
    String title;
    String subtitle;
    String content;

    @Lob
    byte[] image;

    String posted;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_tags",
            joinColumns = { @JoinColumn(name = "blog_id") },
             inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    List<TagEntity> tags = new ArrayList<>();

}

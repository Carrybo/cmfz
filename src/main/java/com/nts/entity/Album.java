package com.nts.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
public class Album implements Serializable {
    @Id
    private String id;
    private String title;
    private String photo;
    private String author;
    private String broadcast;
    private Integer count;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    private String score;
    private String status;
}






















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
public class Counter implements Serializable {
    @Id
    private String id;
    private String name;
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String courseId;
    private String userId;
}

package com.hangeulbada.domain.group.repository;

import com.hangeulbada.domain.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Table(name = "classes")
@Getter
@Setter
@Builder
@Document("classes")
public class Group extends BaseTimeEntity {
    @Id
    private String id;
//    @NotBlank
//    @Column(name = "classname",unique = true)
    private String groupName;

//    @NotBlank
//    @Column(name = "teacher_id",unique = true)
    private String teacherId;

//    @NotBlank
//    @Column(name = "classcode",unique = true)
    private String groupCode;

//    @Column(name = "activated", nullable = false)
    private boolean activated = false;

}

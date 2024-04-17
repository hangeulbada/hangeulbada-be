package com.hangeulbada.domain.classes.domain;

import com.hangeulbada.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "classes")
@Getter
@NoArgsConstructor
public class Classes extends BaseTimeEntity {
    @Id
    @NotBlank
    @Column(name = "classname",unique = true)
    private String classname;

    @NotBlank
    @Column(name = "teacher_id",unique = true)
    private String teacherId;

    @NotBlank
    @Column(name = "classcode",unique = true)
    private String classcode;

    @Column(name = "activated", nullable = false)
    private boolean activated = false;

    @Builder
    public Classes(String classname, String teacherId, String classcode, boolean activated){
        this.classname = classname;
        this.teacherId = teacherId;
        this.classcode = classcode;
        this.activated = activated;
    }

    public void setClasscode(String classCode) {
        this.classcode = classcode;
    }
}

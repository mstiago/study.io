package com.fam.studyio.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class SubjectEntity {

    @Id
    @Column(name = "subject_id")
    @GeneratedValue()
    private Long id;

    @Column(name = "subject_name", unique=true)
    private String name;

    @Column(name = "subject_course")
    private String course;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;
}

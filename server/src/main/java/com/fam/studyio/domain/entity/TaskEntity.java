package com.fam.studyio.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue()
    private Long id;

    @Column
    private String name;

    @Column
    private String activity;

    @Column
    private String description;

    @Column
    private Date initDate;

    @Column
    private Date finishDate;

    @Column
    private String label;

    @Column
    private String type;

    @Column(nullable = true)
    private String testGrade;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable=false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;
}

package com.fam.studyio.domain.models;

import lombok.Data;

import java.util.Date;

@Data
public class TaskModel {

    private String name;
    private String activity;
    private String description;
    private Date initDate;
    private Date finishDate;
    private String label;
    private String type;
    private String testGrade;
    private Long subjectId;

}

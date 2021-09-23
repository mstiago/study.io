package com.fam.studyio.domain.repository;

import com.fam.studyio.domain.entity.SubjectEntity;
import com.fam.studyio.domain.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    List<TaskEntity> findBySubject(SubjectEntity subject);

    Optional<TaskEntity> findByIdAndSubject_User_UserName(Long id, String userName);

}

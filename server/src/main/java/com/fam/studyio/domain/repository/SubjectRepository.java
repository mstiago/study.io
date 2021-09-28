package com.fam.studyio.domain.repository;

import com.fam.studyio.domain.entity.SubjectEntity;
import com.fam.studyio.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Long> {

    Iterable<SubjectEntity> findAllByUser(UserEntity user);

    Optional<SubjectEntity> findByName(String name);

    Optional<SubjectEntity> findByIdAndUser(Long id, UserEntity user);

}

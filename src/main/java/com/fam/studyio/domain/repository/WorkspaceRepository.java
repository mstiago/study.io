package com.fam.studyio.domain.repository;

import com.fam.studyio.domain.entity.UserEntity;
import com.fam.studyio.domain.entity.WorkspaceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WorkspaceRepository extends CrudRepository<WorkspaceEntity, Long> {
    Iterable<WorkspaceEntity> findAllByUser(UserEntity user);

    WorkspaceEntity findByIdAndUser(Long id, UserEntity user);
}

package com.fam.studyio.domain.repository;

import com.fam.studyio.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByUserName(String username);
}

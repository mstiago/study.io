package com.fam.studyio.services;

import com.fam.studyio.domain.entity.SubjectEntity;
import com.fam.studyio.domain.entity.UserEntity;
import com.fam.studyio.domain.models.SubjectModel;
import com.fam.studyio.domain.repository.SubjectRepository;
import com.fam.studyio.domain.repository.UserRepository;
import com.fam.studyio.shared.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectRepositoryImpl{

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String getUserNameByToken(String token){
        return jwtTokenUtil.getUserNameFromToken(token.substring(7));
    }

    public SubjectEntity save(SubjectModel subject, String jwt){
        final UserEntity USER = userRepository.findByUserName(getUserNameByToken(jwt));

        final SubjectEntity newSubject = new SubjectEntity(null, subject.getName(), subject.getCourse(), USER);

        Optional<SubjectEntity> subjectExists = subjectRepository.findByName(newSubject.getName());

        if(subjectExists.isPresent()){
            return null;
        }

        return subjectRepository.save(newSubject);
    }

    public Optional<SubjectEntity> findById(Long id){
        return subjectRepository.findById(id);
    }

    public List<SubjectEntity> findAllSubjects(String jwt){
        final UserEntity USER = userRepository.findByUserName(getUserNameByToken(jwt));

        return (List<SubjectEntity>) subjectRepository.findAllByUser(USER);
    }

    public void deleteById(Long id, String jwt) {
        final UserEntity USER = userRepository.findByUserName(getUserNameByToken(jwt));

        final Optional<SubjectEntity> subject = subjectRepository.findByIdAndUser(id, USER);

        if (subject.isEmpty()){
            throw new EntityNotFoundException();
        }

        subjectRepository.deleteById(id);
    }
}

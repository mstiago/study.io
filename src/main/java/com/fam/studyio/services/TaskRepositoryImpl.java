package com.fam.studyio.services;

import com.fam.studyio.domain.entity.SubjectEntity;
import com.fam.studyio.domain.entity.TaskEntity;
import com.fam.studyio.domain.models.TaskModel;
import com.fam.studyio.domain.repository.TaskRepository;
import com.fam.studyio.domain.repository.UserRepository;
import com.fam.studyio.shared.utils.JwtTokenUtil;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskRepositoryImpl {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private SubjectRepositoryImpl subjectRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  private String getUserNameByToken(String token) {
    return jwtTokenUtil.getUserNameFromToken(token.substring(7));
  }

  public TaskEntity save(TaskModel task, String jwt) {
    Optional<SubjectEntity> subject = subjectRepository.findById(
      task.getSubjectId()
    );

    TaskEntity taskEntity = new TaskEntity();

    taskEntity.setName(task.getName());
    taskEntity.setActivity(task.getActivity());
    taskEntity.setDescription(task.getDescription());
    taskEntity.setInitDate(task.getInitDate());
    taskEntity.setFinishDate(task.getFinishDate());
    taskEntity.setLabel(task.getLabel());
    taskEntity.setType(task.getType());
    taskEntity.setTestGrade(task.getTestGrade());
    taskEntity.setSubject(subject.get());

    return taskRepository.save(taskEntity);
  }

  public void deleteById(String jwt, Long id) {
    Optional<TaskEntity> task = taskRepository.findByIdAndSubject_User_UserName(
      id,
      getUserNameByToken(jwt)
    );

    if (task.isPresent()) {
      taskRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException();
    }
  }

  public List<TaskEntity> findAllTasks(Long subjectId) {
    Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);

    return taskRepository.findBySubject(subject.get());
  }

  public Optional<TaskEntity> findById(Long id) {
    return taskRepository.findById(id);
  }
}

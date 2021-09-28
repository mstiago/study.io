package com.fam.studyio.controllers;

import com.fam.studyio.domain.entity.TaskEntity;
import com.fam.studyio.domain.models.TaskModel;
import com.fam.studyio.services.TaskRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private TaskRepositoryImpl taskRepository;

    @PostMapping
    public ResponseEntity<?> saveTask(@RequestHeader("Authorization") String jwt,
                                      @RequestBody TaskModel task){
        try{
            return ResponseEntity.ok(taskRepository.save(task, jwt));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTask(@RequestHeader("Authorization") String jwt,
                                        @RequestParam(name = "subjectId") Long subjectId,
                                        @RequestParam(name = "id", required = false) Long id){

        if(id == null){
            return ResponseEntity.ok().body(taskRepository.findAllTasks(subjectId));
        }else{
            final Optional<TaskEntity> response = taskRepository.findById(id);

            if(response.isPresent()){
                return ResponseEntity.ok().body(response);
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestHeader("Authorization") String jwt,
                                        @RequestParam(name = "id") Long id){
        try {
            taskRepository.deleteById(jwt, id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return  ResponseEntity.internalServerError().build();
        }
    }
}

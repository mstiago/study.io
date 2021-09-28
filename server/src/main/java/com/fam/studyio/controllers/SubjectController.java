package com.fam.studyio.controllers;

import com.fam.studyio.domain.entity.SubjectEntity;
import com.fam.studyio.domain.models.SubjectModel;
import com.fam.studyio.services.SubjectRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    private SubjectRepositoryImpl subjectRepository;

    @GetMapping
    public ResponseEntity<?> getAllSubjects(@RequestHeader("Authorization") String jwt,
                                            @RequestParam(name = "id", required = false) Long id){

        if(id == null){
            return ResponseEntity.ok().body(subjectRepository.findAllSubjects(jwt));
        }else{
            final Optional<SubjectEntity> response = subjectRepository.findById(id);

            if(response.isPresent()){
                return ResponseEntity.ok().body(response);
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }
    }

    @PostMapping
    public ResponseEntity<?> saveSubject(@RequestHeader("Authorization") String jwt,
                                         @RequestBody SubjectModel subject){

        final SubjectEntity response = subjectRepository.save(subject, jwt);

        if(response == null){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSubject(@RequestHeader("Authorization") String jwt,
                                           @RequestParam(name = "id") Long id){

        try{
            subjectRepository.deleteById(id, jwt);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.noContent().build();
    }
}

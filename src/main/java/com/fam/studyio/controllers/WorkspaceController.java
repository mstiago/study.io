package com.fam.studyio.controllers;

import com.fam.studyio.domain.entity.WorkspaceEntity;
import com.fam.studyio.domain.models.WorkspaceModel;
import com.fam.studyio.services.WorkspaceRepositoryImpl;
import com.fam.studyio.shared.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping(value = "/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceRepositoryImpl workspaceRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<?> saveWorkspace(@RequestHeader("Authorization") String jwt, @RequestBody WorkspaceModel workspaceName) {
        try{
            workspaceRepository.save(workspaceName.getWorkspaceName(), jwt);

            return new ResponseEntity(HttpStatus.CREATED);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }


    }

    @PutMapping
    public ResponseEntity<?> alterWorkspace(@RequestHeader("Authorization") String jwt,
                                            @RequestBody WorkspaceEntity user) {
        return ResponseEntity.ok(workspaceRepository.alter(user, jwt));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteWorkspaceById(@RequestHeader("Authorization") String jwt,
                                                 @RequestParam(name = "id") Long workspaceId) {

        try {
            workspaceRepository.deleteById(workspaceId, jwt);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getWorkspace(HttpServletRequest httpServletRequest,
                                          @RequestHeader("Authorization") String jwt,
                                          @RequestParam(name = "id", required = false) Long workspaceId){

        if(workspaceId == null){
            List<WorkspaceEntity> workspaces = workspaceRepository.findAllWorkspacesByUser(jwt);

            return ResponseEntity.ok().body(workspaces);
        }else{
            Optional<WorkspaceEntity> workspace =  workspaceRepository.findWorkspaceById(workspaceId);
            if(workspace.isPresent()){
                return ResponseEntity.ok().body(workspace);
            }else if(!workspace.isPresent()){
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.internalServerError().body("an error happened, try again.");
            }
        }
    }

}

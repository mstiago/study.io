package com.fam.studyio.services;

import com.fam.studyio.domain.entity.UserEntity;
import com.fam.studyio.domain.entity.WorkspaceEntity;
import com.fam.studyio.domain.repository.UserRepository;
import com.fam.studyio.domain.repository.WorkspaceRepository;
import com.fam.studyio.shared.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WorkspaceRepositoryImpl {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String getUserNameByToken(String token){
        return jwtTokenUtil.getUserNameFromToken(token.substring(7));
    }

    public WorkspaceEntity save(String workspace, String jwt){
        WorkspaceEntity newWorkspace = new WorkspaceEntity();

        newWorkspace.setUser(userRepository.findByUserName(getUserNameByToken(jwt)));
        newWorkspace.setWorkspaceName(workspace);

        return workspaceRepository.save(newWorkspace);
    }

    public WorkspaceEntity alter(WorkspaceEntity workspace, String jwt) throws AccessDeniedException {
        final UserEntity USER = userRepository.findByUserName(getUserNameByToken(jwt));

        WorkspaceEntity workspaceToDelete = workspaceRepository.findByIdAndUser(workspace.getId(), USER);

        if(workspaceToDelete != null){
            return workspaceRepository.save(workspace);
        }else{
            throw new AccessDeniedException("Access Denied");
        }
    }

    public void deleteById(Long id, String jwt) {
        final UserEntity USER = userRepository.findByUserName(getUserNameByToken(jwt));

        final WorkspaceEntity workspace = workspaceRepository.findByIdAndUser(id, USER);

        workspaceRepository.deleteById(id);
    }

    public List<WorkspaceEntity> findAllWorkspacesByUser(String jwt){
        final UserEntity USER = userRepository.findByUserName(getUserNameByToken(jwt));

        List<WorkspaceEntity> workspaces = (List<WorkspaceEntity>) workspaceRepository.findAllByUser(USER);

        return workspaces;
    }

    public Optional<WorkspaceEntity> findWorkspaceById (Long id){
        return workspaceRepository.findById(id);
    }

    public List<WorkspaceEntity> findAllWorkspaces (){
        List<WorkspaceEntity> result =
                StreamSupport
                        .stream(workspaceRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        return result;
    }
}

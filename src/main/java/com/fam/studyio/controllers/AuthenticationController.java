package com.fam.studyio.controllers;

import com.fam.studyio.shared.utils.JwtTokenUtil;
import com.fam.studyio.domain.models.jwt.JwtRequest;
import com.fam.studyio.domain.models.jwt.JwtResponse;
import com.fam.studyio.domain.models.UserModel;
import com.fam.studyio.services.UserDetailsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsDataService userDetailsService;


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserDetails USER_DETAILS = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        final String TOKEN = jwtTokenUtil.generateToken(USER_DETAILS);

        return ResponseEntity.ok(new JwtResponse(TOKEN));
    }

    @PostMapping(value = "/register")
    public @ResponseBody ResponseEntity<?> saveUser(@RequestBody UserModel user) throws Exception {
        if(user.getName() == null)
            return ResponseEntity.badRequest().body(generateRequiredJsonBody("Name"));

        if(user.getUserName() == null)
            return ResponseEntity.badRequest().body(generateRequiredJsonBody("Username"));

        if(user.getEmail() == null)
            return ResponseEntity.badRequest().body(generateRequiredJsonBody("Email"));

        if(user.getBirthDate() == null)
            return ResponseEntity.badRequest().body(generateRequiredJsonBody("BirthDate"));

        if(user.getPhoneNumber() == null)
            return ResponseEntity.badRequest().body(generateRequiredJsonBody("PhoneNumber"));

        if(user.getPassword() == null)
            return ResponseEntity.badRequest().body(generateRequiredJsonBody("Password"));

        userDetailsService.save(user);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/user")
    public @ResponseBody ResponseEntity<?> getUserData(@RequestHeader("Authorization") String jwt) throws Exception{
        final String USER = jwtTokenUtil.getUserNameFromToken(jwt.substring(7));

        return ResponseEntity.ok(userDetailsService.getUserDataByUsername(USER));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public Map<String, String> generateRequiredJsonBody(String param){
        Map<String, String> body = new HashMap<>();
        body.put("Description", String.format("Incorrect request. %s is required.", param));

        return body;
    }
}

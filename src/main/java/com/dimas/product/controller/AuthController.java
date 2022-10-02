package com.dimas.product.controller;

import com.dimas.product.entity.ERole;
import com.dimas.product.entity.RoleEntity;
import com.dimas.product.entity.UserEntity;
import com.dimas.product.payload.request.LoginRequest;
import com.dimas.product.payload.request.SignupRequest;
import com.dimas.product.payload.response.JwtResponse;
import com.dimas.product.payload.response.MessageResponse;
import com.dimas.product.repository.RoleEntityRepository;
import com.dimas.product.repository.UserEntityRepository;
import com.dimas.product.security.jwt.JwtUtils;
import com.dimas.product.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private AuthenticationManager authenticationManager;

    private UserEntityRepository userEntityRepository;

    private RoleEntityRepository roleEntityRepository;

    private PasswordEncoder encoder;

    private JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateuser(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){


        System.out.println(signupRequest);

        if (userEntityRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("error: username is already taken"));
        }

        if (userEntityRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : email is already in use"));
        }


        UserEntity user = new UserEntity(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));




        Set<String> strRoles = signupRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles== null){
            RoleEntity userRole = roleEntityRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Role is not found"));
            roles.add(userRole);
        }else {
            strRoles.forEach(role-> {
                switch (role) {
                    case "admin" :
                        RoleEntity adminRole = roleEntityRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error: role is not found"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleEntity modRole = roleEntityRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(()-> new RuntimeException("Error: role is not found"));
                        roles.add(modRole);
                        break;

                    default:
                        RoleEntity userRole = roleEntityRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(()-> new RuntimeException("Error: role is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userEntityRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("user registered successfully"));
    }



}

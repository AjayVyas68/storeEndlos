package com.storeendlos.user.Controller;


import com.storeendlos.Payload.request.LoginRequest;
import com.storeendlos.Payload.request.SignupRequest;
import com.storeendlos.Payload.response.JwtResponse;
import com.storeendlos.Payload.response.MessageResponse;
import com.storeendlos.exception.ExceptionService.RoleNotFound;
import com.storeendlos.jwt.JwtUtils;
import com.storeendlos.user.Repository.RoleRepository;
import com.storeendlos.user.Repository.UserRepository;
import com.storeendlos.user.Service.UserService;
import com.storeendlos.user.model.ERole;
import com.storeendlos.user.model.Role;
import com.storeendlos.user.model.User;
import com.storeendlos.websecurity.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/store/api/v1/user")
public class UserController {

    public static final Logger log= LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhoneno(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getPhoneno(),
                    userDetails.getAddress(),
                    userDetails.getStatus(),
                    userDetails.getDatetime(), roles));
        } catch (RuntimeException e) {
            throw new RuntimeException("password incorrect ");
        }
    }

    public ResponseEntity<?> userSave(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByPhoneno(signupRequest.getPhoneno())) {
            return ResponseEntity.badRequest().body(new MessageResponse("already exists"));
        }
        User user = new User(signupRequest.getName(), signupRequest.getEmail(), signupRequest.getAddress()
                , signupRequest.getPhoneno(), signupRequest.getTelephoneNumber(), signupRequest.getDob(), signupRequest.getMaritalStatus(), signupRequest.getNativePalace()
                , signupRequest.getNationality(), signupRequest.getPinCode());

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        System.out.println(signupRequest.getRoles());
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
            System.out.println(userRole.getName());
            roles.add(userRole);
            System.out.println("null values");
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(adminRole);
                        System.out.println("admin values");
                        break;
                    case "SUBADMIN":
                        Role modRole = roleRepository.findByName(ERole.ROLE_SUBADMIN)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(modRole);
                        System.out.println("sub admin values");
                    case "USER":
                        Role sudAdmin = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(sudAdmin);
                        System.out.println("sub admin values");
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok((user));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{userId}")
    public ResponseEntity<?> updateUserData(@PathVariable Long userId, @RequestBody Map<Object, Object> changes) {
        return new ResponseEntity<>(userService.updateUser(userId, changes), HttpStatus.OK);
    }

}

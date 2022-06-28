package com.storeendlos;

import com.storeendlos.user.Repository.RoleRepository;
import com.storeendlos.user.Repository.UserRepository;
import com.storeendlos.user.model.ERole;
import com.storeendlos.user.model.Role;
import com.storeendlos.user.model.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class StoreEndlosApplication implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StoreEndlosApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public static void main(String[] args) {
        SpringApplication.run(StoreEndlosApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User use = new User();

        use.setName("Admin");
        use.setSurName("surname");
        use.setDob(LocalDate.parse("1996-09-02"));
        use.setEmail("adminmain@gmail.com");
        use.setMaritalStatus("single");
        use.setNationality("India");
        use.setPhoneno("1234567890");
        use.setNativePalace("Nikol");
        use.setPinCode("382350");
        use.setTelephoneNumber("1234567890");
        use.setAddress("Rajkot");
        use.setPassword(passwordEncoder.encode("admin"));


        User user = new User();
        user.setName("Admin");
        user.setSurName("surname");
        user.setDob(LocalDate.parse("1996-09-02"));
        user.setEmail("adminmain@gmail.com");
        user.setMaritalStatus("single");
        user.setNationality("India");
        user.setPhoneno("1234567899");
        user.setNativePalace("Nikol");
        user.setPinCode("382350");
        user.setTelephoneNumber("1234567899");
        user.setAddress("Rajkot");
        use.setPassword(passwordEncoder.encode("admin"));

        Role role = new Role(ERole.ROLE_ADMIN);
        Role role1 = new Role(ERole.ROLE_SUBADMIN);

        List<User> checkData=userRepository.findAll();
        if (checkData.isEmpty())
        {
            roleRepository.save(role);
            roleRepository.save(role1);
            use.getRoles().add(role);

            userRepository.save(use);
            user.getRoles().add(role1);
            userRepository.save(user);
        }
    }
}

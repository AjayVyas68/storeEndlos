package com.storeendlos.websecurity;


import com.storeendlos.exception.ExceptionService.UserNotFound;
import com.storeendlos.user.Repository.UserRepository;
import com.storeendlos.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneno) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneno(phoneno)
                .orElseThrow(() -> new UserNotFound("User Not Found with username: " + phoneno));
        System.out.println(phoneno);

        return UserDetailsImpl.build(user);
    }

}

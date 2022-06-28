package com.storeendlos.user.Service;

import com.storeendlos.user.Repository.UserRepository;
import com.storeendlos.user.model.User;
import org.hibernate.annotations.common.reflection.ReflectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.naming.RefAddr;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final  UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User saveData(User user)
    {
        return userRepository.save(user);
    }
    public Optional<User> findById(Long id)
    {
        return Optional.of(userRepository.findById(id).orElseThrow(() -> new RuntimeException("user Not Found")));

    }
    public List<User> findAll()
    {
        return userRepository.findAll().stream().sorted(Comparator.comparing(User::getUserId)).collect(Collectors.toList());
    }
    public Object updateUser(long userId, Map<Object,Object> changes)
    {
        User user=findById(userId).get();
        changes.forEach((key,value)->{
            Field field= ReflectionUtils.findField(User.class,(String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,user,value);
        });
        return userRepository.save(user);
    }
}

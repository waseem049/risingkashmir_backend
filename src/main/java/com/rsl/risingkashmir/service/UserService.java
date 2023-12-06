package com.rsl.risingkashmir.service;

import com.rsl.risingkashmir.entiry.UserEntity;
import com.rsl.risingkashmir.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Integer saveUser(UserEntity user) {
        // Encode password before saving to DB
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserEntity> opt = userRepository.findByUsername(username);

        User springUser = null;

        if(opt.isEmpty()) {
            throw new UsernameNotFoundException(
                    "User with username: " + username +" not found"
            );
        }else {
                UserEntity user = opt.get(); //retrieving user from DB
                Set<String> roles = user.getRoles();
                Set<GrantedAuthority> ga = new HashSet<>();
                for(String role:roles) {
                    ga.add(new SimpleGrantedAuthority(role));
                }

                springUser = new User(
                        username,
                        user.getPassword(),
                        ga
                );
        }

        return springUser;
    }
}

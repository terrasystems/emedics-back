package com.terrasystems.emedics.security;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User client =  userRepository.findByUsername(username);
        detailsChecker.check(client);
        return client;
    }
}

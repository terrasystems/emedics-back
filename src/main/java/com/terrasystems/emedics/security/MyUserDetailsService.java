package com.terrasystems.emedics.security;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service(value = "emedics")
public class MyUserDetailsService implements UserDetailsService  {
    @Autowired
    UserRepository userRepository;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User client =  userRepository.findByEmail(username);
        detailsChecker.check(client);
        return client;
    }
}

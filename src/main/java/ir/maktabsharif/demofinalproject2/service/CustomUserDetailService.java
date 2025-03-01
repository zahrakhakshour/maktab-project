package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.model.Authority;
import ir.maktabsharif.demofinalproject2.model.Role;
import ir.maktabsharif.demofinalproject2.model.UserAccount;
import ir.maktabsharif.demofinalproject2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userRepository.findByUserName(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();


        Role role = user.get().getRole();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));


        for (Authority authority : role.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }


        return new org.springframework.security.core.userdetails.User(user.get().getUserName(),
                user.get().getPassword(), grantedAuthorities);
    }
}

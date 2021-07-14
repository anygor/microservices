package com.epam.mentoring.services;

import com.epam.mentoring.models.User;
import com.epam.mentoring.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Autowired private ObjectMapper objectMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("No user found. Username tried: " + username);
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    grantedAuthorities.add(new SimpleGrantedAuthority("admin"));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), grantedAuthorities);
  }
}

package ru.shaldnikita.starter.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shaldnikita.starter.backend.model.User;
import ru.shaldnikita.starter.backend.service.UserService;


import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByLogin(username);
		if (null == user) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		}
	}
}
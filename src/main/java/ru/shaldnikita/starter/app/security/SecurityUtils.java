package ru.shaldnikita.starter.app.security;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shaldnikita.starter.backend.model.User;
import ru.shaldnikita.starter.backend.service.UserService;


import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;



public class SecurityUtils {

	private SecurityUtils() {
	}

	public static String getUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
        LoggerFactory.getLogger(SecurityUtils.class).info("context: {}, {}, {}",
                context==null,context.getAuthentication()==null);
		UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}

	public static boolean isCurrentUserInRole(String role) {
		return getUserRoles().stream().filter(roleName -> roleName.equals(Objects.requireNonNull(role))).findAny()
				.isPresent();
	}

	public static Set<String> getUserRoles() {
		SecurityContext context = SecurityContextHolder.getContext();
		return context.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());
	}

	public static User getCurrentUser(UserService userService) {
		return userService.findByEmail(SecurityUtils.getUsername());
	}
}

package com.treading.coin.service;

import com.treading.coin.model.User;
import com.treading.coin.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Customer user detail service.
 */
@Service
public class CustomerUserDetailService implements UserDetailsService {


	private final UserRepository userRepository;

	/**
	 * Instantiates a new Customer user detail service.
	 *
	 * @param userRepository the user repository
	 */
	protected CustomerUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Load User By Username
	 *
	 * @param username username
	 * @return user
	 * @throws UsernameNotFoundException username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		List<GrantedAuthority> authorityList = new ArrayList<>();

		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), authorityList);
	}
}

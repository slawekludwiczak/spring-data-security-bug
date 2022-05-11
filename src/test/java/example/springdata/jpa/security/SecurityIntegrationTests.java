/*
 * Copyright 2014-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.security;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class SecurityIntegrationTests {

	@Autowired UserRepository userRepository;

	private User joe;
	private UsernamePasswordAuthenticationToken joeAuth;

	@BeforeEach
	void setup() {
		User joe = userRepository.save(new User("joe", "{noop}joepass"));
		joeAuth = new UsernamePasswordAuthenticationToken(joe.getUsername(), "", singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
		SecurityContextHolder.getContext().setAuthentication(joeAuth);
	}

	@Test
	void shouldFindCurrentUserByName() {
		Optional<User> currentUser = userRepository.currentUserByName();
		assertThat(currentUser.get()).isNotNull();
	}

	@Test
	void shouldFindCurrentUserByNameAndAuthentication() {
		Optional<User> currentUser = userRepository.currentUserByNameAndAuthentication();
		assertThat(currentUser.get()).isNotNull();
	}
}

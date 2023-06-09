package ru.oorzhak.socialnetwork.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFound;
import ru.oorzhak.socialnetwork.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username)));
    }
}

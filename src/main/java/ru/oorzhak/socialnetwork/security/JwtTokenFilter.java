package ru.oorzhak.socialnetwork.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.oorzhak.socialnetwork.util.JwtUtil;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            String jwt = header.substring(7);

            if (!jwtUtil.isJwtValid(jwt)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT");
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsernameFromJwtToken(jwt));

            var authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (IOException e) {
            System.out.println("Filter IOException");
            throw new RuntimeException(e);
        } catch (ServletException e) {
            System.out.println("Filter ServletException");
            throw new RuntimeException(e);
        } catch (UsernameNotFoundException e) {
            System.out.println("Filter UsernameNotFoundException");
            throw new RuntimeException(e);
//        } catch (NoAuthenticationHeader | NullPointerException ignored) {

        } catch (IndexOutOfBoundsException ignored) {

        }

        filterChain.doFilter(request, response);
    }

}

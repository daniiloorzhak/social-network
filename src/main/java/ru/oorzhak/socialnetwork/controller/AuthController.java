package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.oorzhak.socialnetwork.dto.UserLoginDTO;
import ru.oorzhak.socialnetwork.dto.UserRegisterDTO;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.service.UserService;
import ru.oorzhak.socialnetwork.util.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Register user", security = {}, responses = {
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad user credentials")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        User user = userService.save(userRegisterDTO);
        return ResponseEntity.ok(user.getUsername());
    }

    @Operation(summary = "Log in user", responses = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in"),
            @ApiResponse(responseCode = "400", description = "Bad user credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        var authToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        var authentication = authenticationManager.authenticate(authToken);
        String jwtToken = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(Map.of("jwt-token", jwtToken));
    }
}
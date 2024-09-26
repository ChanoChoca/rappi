package com.chanochoca.app.controller;

import com.chanochoca.app.models.ReadUserDTO;
import com.chanochoca.app.models.UserAuthorityDTO;
import com.chanochoca.app.models.entity.User;
import com.chanochoca.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final ClientRegistration registration;

    public UserController(UserService userService, ClientRegistrationRepository registration) {
        this.userService = userService;
        this.registration = registration.findByRegistrationId("okta");
    }

    @GetMapping("/get-authenticated-user")
    public ResponseEntity<ReadUserDTO> getAuthenticatedUser(
            @AuthenticationPrincipal OAuth2User user, @RequestParam boolean forceResync) {
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            userService.syncWithIdp(user, forceResync);
            ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
            return new ResponseEntity<>(connectedUser, HttpStatus.OK);
        }
    }

    @PutMapping("/modify-user-authority/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable Long id, @RequestBody UserAuthorityDTO userAuthorityDTO) {
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(id));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAuthorities(userAuthorityDTO.getAuthorities());  // Solo se actualizan las autoridades
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String issuerUri = registration.getProviderDetails().getIssuerUri();
        String originUrl = request.getHeader(HttpHeaders.ORIGIN);
        Object[] params = {issuerUri, registration.getClientId(), originUrl};
        String logoutUrl = MessageFormat.format("{0}v2/logout?client_id={1}&returnTo={2}", params);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("logoutUrl", logoutUrl));
    }
}

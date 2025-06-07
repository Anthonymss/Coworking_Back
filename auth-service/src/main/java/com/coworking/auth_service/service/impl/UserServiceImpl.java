package com.coworking.auth_service.service.impl;

import com.coworking.auth_service.configuration.jwt.JwtTokenProvider;
import com.coworking.auth_service.dto.AuthResponseDto;
import com.coworking.auth_service.exception.*;
import com.coworking.auth_service.entity.Role;
import com.coworking.auth_service.entity.User;
import com.coworking.auth_service.entity.UserAuthentication;
import com.coworking.auth_service.repository.RoleRepository;
import com.coworking.auth_service.repository.UserAuthenticationRepository;
import com.coworking.auth_service.repository.UserRepository;
import com.coworking.auth_service.dto.AuthRequest;
import com.coworking.auth_service.dto.UserDto;
import com.coworking.auth_service.service.IMethodInfoGoogle;
import com.coworking.auth_service.service.NotificationService;
import com.coworking.auth_service.service.feignclient.MailServiceFeignClient;
import com.coworking.auth_service.util.enums.RoleName;
import com.coworking.auth_service.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, IMethodInfoGoogle {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserAuthenticationRepository authenticationRepository;
    private final String clientIdGoogle;
    private final NotificationService notificationService;
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           UserAuthenticationRepository authenticationRepository,
                           @Qualifier("googleClientId") String clientIdGoogle, MailServiceFeignClient mailServiceFeign, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationRepository = authenticationRepository;
        this.clientIdGoogle = clientIdGoogle;
        this.notificationService = notificationService;
    }
    @Override
    @Transactional
    public AuthResponseDto generateTokenRegisterForGoogle(String idTokenString) {
        Map<String,String> map=getInfoGoogle(idTokenString);
        GoogleIdToken.Payload payload = verifyAndGetPayload(idTokenString);
        String email = map.get("email");
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        String profileImageUrl = map.get("profileImageUrl");
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new DuplicateEmailException(email);
        }
        User newUser = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .roles(Set.of(roleRepository.findByName(RoleName.USER)))
                .statusOauthEnabled(true)
                .build();
        UserAuthentication userAuthentication = UserAuthentication.builder()
                .providerId(payload.getSubject())
                .user(newUser)
                .authProvider("google")
                .build();
        User userSave=userRepository.save(newUser);
        authenticationRepository.save(userAuthentication);
        UserDto userDtoSend =UserDto.builder()
                .id(newUser.getId())
                .accountCreated(newUser.getAccountCreated())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .build();
        notificationService.sendWelcomeEmailAsync("WelcomeTemplate", userDtoSend);
        String accessToken  = jwtTokenProvider.generateAccessToken(userSave);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userSave);
        return new AuthResponseDto(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponseDto generateTokenLoginForGoogle(String tokenGoogle) {
        GoogleIdToken.Payload payload = verifyAndGetPayload(tokenGoogle);

        String email = payload.getEmail();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            User userResponse=existingUser.get();
            if(userResponse.getStatusOauthEnabled()){
                String accessToken  = jwtTokenProvider.generateAccessToken(userResponse);
                String refreshToken = jwtTokenProvider.generateRefreshToken(userResponse);
                return new AuthResponseDto(accessToken, refreshToken);
            }
            throw new UserNotOauthForGoogle("User with "+email+" not authorized to use Google OAuth");
        }
        throw new UserNotFoundException(email);

    }

    @Override
    public String registerUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new DuplicateEmailException(userDto.getEmail());
        }
        try{
            User user = User.builder()
                    .lastName(userDto.getLastName())
                    .firstName(userDto.getFirstName())
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .statusOauthEnabled(false)
                    .build();
            System.out.println(":::::::::::::=>>>  "+RoleName.USER.name());
            Role userRole = roleRepository.findByName(RoleName.USER);
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            UserDto userDtoSend =UserDto.builder()
                    .id(user.getId())
                    .accountCreated(user.getAccountCreated())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();
            notificationService.sendWelcomeEmailAsync("WelcomeTemplate", userDtoSend);
            return "User registered successfully with role USER";
        }catch (Exception e){
            return "Failed to register user";
        }
    }


    @Override
    public AuthResponseDto authenticateUser(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(authRequest.getEmail()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        String accessToken  = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        return new AuthResponseDto(accessToken, refreshToken);
    }


    /**
     * Verifies a Google ID token and returns its payload
     *
     * Validates the token against a specific audience Returns the payload if valid;
     * otherwise, throws an exception
     *
     * @param token the Google ID token to verify
     * @return the payload of the verified token
     * @throws InvalidTokenOfGoogleException if the token is invalid
     * @throws RuntimeException if verification fails
     */
    private GoogleIdToken.Payload verifyAndGetPayload(String token) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(clientIdGoogle))
                    .build();
            GoogleIdToken idToken = verifier.verify(token);
            return idToken.getPayload();
        } catch (InvalidTokenOfGoogleException | GeneralSecurityException | IOException e) {
            throw new InvalidTokenOfGoogleException("Invalid token not found");
        }
    }
    private Map<String,String> getInfoGoogle(String idTokenString){
        Map<String, String> mapAccountGoogle=new HashMap<String, String>();
        GoogleIdToken.Payload payload = verifyAndGetPayload(idTokenString);
        mapAccountGoogle.put("id", payload.getSubject());
        mapAccountGoogle.put("firstName",(String) payload.get("given_name"));
        mapAccountGoogle.put("lastName",(String) payload.get("family_name"));
        mapAccountGoogle.put("email", payload.getEmail());
            mapAccountGoogle.put("profileImageUrl", (String) payload.get("picture"));
        return mapAccountGoogle;
    }


    @Override
    public Map<String, String> getInfoForAccountGoogle(String token) {
        return getInfoGoogle(token);
    }

}


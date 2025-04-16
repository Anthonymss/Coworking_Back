package com.coworking.management_user.presentation.dto;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date accountCreated;
    private String profileImageUrl;
    private Boolean statusOauthEnabled;

}
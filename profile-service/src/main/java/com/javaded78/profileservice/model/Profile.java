package com.javaded78.profileservice.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile implements BaseEntity<String> {

    @Id
    private String id;

    @Indexed(unique = true) private String email;
    @NonNull private String username;
    @NonNull private LocalDate joinDate;

    private String bio;
    private String location;
    private String website;
    private LocalDate birthDate;

    private String avatarUrl;
    private String bannerUrl;
}

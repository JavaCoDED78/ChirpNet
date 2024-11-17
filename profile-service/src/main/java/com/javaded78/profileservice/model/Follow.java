package com.javaded78.profileservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "follows")
@CompoundIndexes({
        @CompoundIndex(name = "follower_followee_idx", def = "{'followerProfile': 1, 'followeeProfile': 1}", unique = true)
})
public class Follow implements BaseEntity<String> {

    @Id
    private String id;

    @DBRef private Profile followerProfile;
    @DBRef private Profile followeeProfile;

    private LocalDateTime followDateTime;
}

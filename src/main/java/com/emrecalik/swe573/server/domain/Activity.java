package com.emrecalik.swe573.server.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Locale;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {

    public enum ActivityType {
        TAG,
        RATE,
        UPDATE,
        FOLLOW,
        UNFOLLOW,
        SIGNIN;

        @JsonValue
        public String toLower() {
            return this.toString().toLowerCase(Locale.ENGLISH);
        }
    }

    public enum ObjectType {
        ARTICLE,
        USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summary;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @Enumerated(EnumType.STRING)
    private ObjectType objectType;

    private Long actorId;

    private Long objectId;

    private Instant published;
}

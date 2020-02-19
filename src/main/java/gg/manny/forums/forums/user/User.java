package gg.manny.forums.forums.user;

import gg.manny.forums.forums.user.grant.Grant;
import gg.manny.forums.forums.user.punishment.Punishment;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.*;

@Getter
public class User {

    @Id @NonNull private UUID id;
    @Setter private String name;

    private List<Grant> grants = new ArrayList<>();
    private List<Punishment> punishments = new ArrayList<>();

    private Date dateJoined;
    private Date dateLastSeen;

    private Set<String> ipAddresses = new HashSet<>();

}

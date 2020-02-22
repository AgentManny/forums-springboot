package gg.manny.forums.user;

import gg.manny.forums.user.grant.Grant;
import gg.manny.forums.user.punishment.Punishment;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Document(collection = "users")
public class User {

    @Setter @Id @NonNull private UUID id;
    @Setter private String name;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String email;
    @Setter private String password;

    @Setter private boolean registered = false;

    private List<Grant> grants = new ArrayList<>();
    private List<Punishment> punishments = new ArrayList<>();

    @Setter private Date dateJoined;
    @Setter private Date dateLastSeen;

    private Set<String> ipAddresses = new HashSet<>();

    public void addGrant(Grant grant) {
        grants.add(grant);
    }

}

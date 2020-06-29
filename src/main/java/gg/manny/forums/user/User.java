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

    /** Returns the unique identifer for a user **/
    @Setter @Id @NonNull private UUID id;

    /** Returns the unique name of a user */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @Getter @Setter private String username;

    /** Returns the email address of a user */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @Getter @Setter private String email;

    /** Returns an encrpyted (hash + salted) password  */
    @Getter @Setter private String password;

    /** Returns whether a user is registered or not */
    @Setter private boolean registered = false;

    /** Returns all existing grants of a user including inactives one */
    private List<Grant> grants = new ArrayList<>();

    /** Returns all existing punishments of a user including inactives for historical purposes */
    private List<Punishment> punishments = new ArrayList<>();

    /** Returns the date upon the first registration of a user */
    @Setter private Date dateJoined;

    /** Returns the date upon the last login of a user */
    @Setter private Date dateLastSeen; // todo add a system to check if they are online or not

    /** Returns a map of ip addresses and date used */
    private Map<Date, String> ipAddresses = new HashMap<>();

    private Map<String, Object> metaData = new HashMap<>(); // todo future use for external systems storing data

    public void addGrant(Grant grant) {
        grants.add(grant);
    }

    // Todo get their active role -- or default when not active
    // todo add their active grant and prevent inactive (temporarily ones) from being active



    /**
     * Whether they are online on a server or not, sends data
     * @return
     */
    public boolean isOnline() {
        return false;
    }

    public String getLastServer() {
        return "HG-01";
    }


}

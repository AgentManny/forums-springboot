package gg.manny.forums.forum;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Document(collection = "categories")
@NoArgsConstructor
@RequiredArgsConstructor
public class ForumCategory {

    /** Returns name for forum category */
    @Id
    @Setter @NonNull private String id;

    /** Returns the display name for forum category */
    @Setter @NonNull private String displayName;

    /** Returns the forum it's located in */
    @Setter @NonNull @DBRef private Forum forum;

    /** Returns the description of a category */
    @Setter private String description = "";

    /** List all threads included inside a category */
    @DBRef private Set<ForumThread> threads = new HashSet<>(); // Changed to only include identifiers as Threads are stored in different repo

    /** Weight of the category which will be sorted on listing */
    @Setter private int weight = -1;

    /**
     * Timestamp on last modified which is updated
     * when a user creates or replies to a category
     */
    @Setter private long lastActivity = -1;

    /**
     * Permission node required to view a categories' threads,
     * if empty or null, it'll be viewable by anyone including non-registered
     * users.
     */
    @Setter private String permission = "";

    public String getFriendlyUrl() { // Prevents allowing spaces and stuff
        return "/forums/" + this.getId();
    }


}

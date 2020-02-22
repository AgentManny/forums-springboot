package gg.manny.forums.forum;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "categories")
@RequiredArgsConstructor
public class ForumCategory {

    /** Returns name for forum category */
    @Setter @NonNull private String name;

    /** Returns the description of a category */
    @Setter private String description = "";

    /** List all threads included inside a category*/
    private List<ForumThread> threads = new ArrayList<>();

    /** Weight of the category which will be sorted on listing */
    @Setter private int weight = -1;

    /**
     * Permission node required to view a categories' threads,
     * if empty or null, it'll be viewable by anyone including non-registered
     * users.
     */
    @Setter private String permission = "";

    public String getId() {
        return name.toLowerCase().replace(" ", "-");
    }
}

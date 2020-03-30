package gg.manny.forums.forum;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "forums")
public class Forum {

    /** Returns name of forum section */
    @Id @NonNull private String name;

    /** List all categories included inside a section */
    private List<ForumCategory> categories = new ArrayList<>();

    /** Weight of the category which will be sorted on listing */
    private int weight = -1;

}

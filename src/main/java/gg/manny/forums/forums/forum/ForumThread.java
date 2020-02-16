package gg.manny.forums.forums.forum;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Document
public class ForumThread {

    /** Generates a unique identifier for each thread */
    @Id private final int id;

    /** Timestamp on thread creation, this cannot be altered */
    private final long timestamp = System.currentTimeMillis();

    /**
     * Creator's identifier {@link UUID} which links them to
     * to a thread, it cannot be null.
     */
    private final UUID author;

    /** Title of the thread */
    @Setter @NonNull private String title;

    /** Message that is displayed when viewing the thread */
    @Setter @NonNull private String message;

    /**
     * Timestamp on last modified which is updated
     * when a user edits the message, title or the category
     */
    @Setter private Long lastEdited;

    /**
     * User who modified the thread after the creation, useful for
     * moderators editing the thread who isn't the creator
     */
    @Setter private UUID lastEditedBy;
    
    private int upvotes = 0;

    protected List<ForumThread> replies = new ArrayList<>();
    private long lastReply = -1;

    public ForumThread(int id, UUID author, String title) {
        this.id = id;
        this.title = title;

        this.author = author;
    }

    public ForumThread(int id, UUID author) {
        this.id = id;

        this.author = author;
    }


}

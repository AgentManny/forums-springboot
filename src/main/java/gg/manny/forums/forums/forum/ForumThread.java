package gg.manny.forums.forums.forum;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Document(collection = "threads")
public class ForumThread {

    /** Generates a unique identifier for each thread */
    private final int id;

    /** Timestamp on thread creation, this cannot be altered */
    private final long timestamp = System.currentTimeMillis();

    /**
     * Creator's identifier {@link UUID} which links them to
     * to a thread, it cannot be null.
     */
    private final UUID author;

    /** Title of the thread */
    @Setter @NonNull private String title;

    /** Message (body) that is displayed when viewing the thread */
    @Setter @NonNull private String body;

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

    /** Users who have liked the thread which is used for sorting */
    @Setter private int upvotes = 0;

    /**
     * Comments posted on parent thread which are visible
     * when viewing the thread, allows infinite replying as
     * it allows support to comment on replies
     *
     * TODO link to parent thread?
     **/
    private List<ForumThread> replies = new ArrayList<>();

    /**
     * Timestamp on last replied which is updated
     * when a user replies to the parent thread
     *
     * TODO update when a sub-thread is replied to.
     */
    @Setter private Long lastReply;

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

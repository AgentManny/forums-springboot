package gg.manny.forums.forum;

import gg.manny.forums.Application;
import gg.manny.forums.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.commonmark.node.Node;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Document(collection = "threads")
public class ForumThread {

    /** Gets the location of thread, shouldn't be stored **/
    @NonNull @Setter @Transient private String forum;

    /** Gets the location of thread **/
    @NonNull @Setter @DBRef private ForumCategory category;


    /** Generates a unique identifier for each thread */
    @NonNull @Setter private String id;

    /** Timestamp on thread creation, this cannot be altered */
    @NonNull private long timestamp = System.currentTimeMillis();

    /**
     * Creator's identifier {@link UUID} which links them to
     * to a thread, it cannot be null.
     */
    @NonNull @Setter @DBRef private User author;

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
    private List<ForumThread> replies = new LinkedList<>(); // Changed to link because it's in order

    /**
     * Timestamp on last replied which is updated
     * when a user replies to the parent thread
     *
     * TODO update when a sub-thread is replied to.
     */
    @Setter @DBRef private ForumThread lastReply;

    public ForumThread(String id, User author, String title) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public ForumThread(String id, User author) {
        this.id = id;
        this.author = author;
    }

    public String getFriendlyUrl() { // Prevents allowing spaces and stuff
        return "/thread/" + this.id + "/" + this.title.toLowerCase()
                .replace("/[^a-z0-9]+/g", "-")
                .replace("/^-+|-+$/g", "-")
                .replace("/^-+|-+$/g", "")
                .replace(" ", "-");
    }

    // markdown support
    // todo only allow people with certain permissions to format markdown
    public String getFormattedBody() {
        Node body = Application.MARKDOWN_PARSER.parse(this.body);
        return Application.MARKDOWN_RENDERER.render(body);
    }
}

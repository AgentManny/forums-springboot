package gg.manny.forums.user.grant;

import gg.manny.forums.rank.Rank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.UUID;

@Getter
@Setter
public class Grant implements Comparable<Grant> {

    @NonNull private String id;

    @Transient private String rankId; // Used for POST data to convert id into rank (limitations of Springboot)
    @DBRef @NonNull private Rank rank;

    public String reason;

    private UUID issuedBy;
    private long issuedAt = System.currentTimeMillis();

    private Long expiresAt;

    private String removalReason;
    private UUID removedBy;
    private Long removedAt;

    public boolean isActive() {
        if (removedAt == null) {
            if (expiresAt != null) {
                if (System.currentTimeMillis() >= expiresAt) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Grant o) {
        return Boolean.compare(!this.isActive(), !o.isActive());
    }
}

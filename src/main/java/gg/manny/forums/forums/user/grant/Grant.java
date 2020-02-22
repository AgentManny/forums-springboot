package gg.manny.forums.forums.user.grant;

import gg.manny.forums.forums.role.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.UUID;

@Getter
@Setter
public class Grant implements Comparable<Grant> {

    private final UUID id = UUID.randomUUID();
    @DBRef private Role role;

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

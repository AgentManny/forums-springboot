package gg.manny.forums.forums.user.punishment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Punishment {

    private final UUID id = UUID.randomUUID();
    private String rank;

    public String reason;

    private UUID issuedBy;
    private long issuedAt = System.currentTimeMillis();

    @Setter private Long expiresAt;

    @Setter private String removalReason;
    @Setter private UUID removedBy;
    @Setter private Long removedAt;

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
}

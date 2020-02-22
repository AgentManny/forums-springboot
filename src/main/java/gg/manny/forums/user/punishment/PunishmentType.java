package gg.manny.forums.user.punishment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PunishmentType {

    BLACKLIST("blacklisted", "#B03A2E"),
    BAN("banned", "#B03A2E"),
    MUTE("muted", "#F8C471"),
    KICK("kicked", "#E74C3C");

    private final String action;
    private final String color;

}

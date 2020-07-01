package gg.manny.forums.rank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.Ints;
import gg.manny.forums.rank.json.RankListDeserializer;
import gg.manny.forums.rank.json.RankListSerializer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Document(collection = "roles")
public class Rank {

    public static final Comparator<Rank> GENERAL_WEIGHT_COMPARATOR = (a, b) -> Ints.compare(b.getWeight(), a.getWeight());
    public static final Comparator<Rank> DISPLAY_WEIGHT_COMPARATOR = (a, b) -> Ints.compare(b.getDisplayOrder(), a.getDisplayOrder());

    /** Returns the id of a role, it doesn't include spaces or capitalization **/
    @Id @NonNull @Setter private String id;

    /** Returns the display name of a role */
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @Setter private String name;

    /** Returns the order of a role, this sorts roles into hierarchy order for viewing */
    @Setter private int displayOrder = -1;

    /** Returns if the rank is a default rank which is assigned to users on creations **/
    @Setter private boolean defaultRank = false;

    /** Returns if the rank should be visible on the user's profile **/
    @Setter private boolean hidden = false;

    /** Returns the prefix of a role */
    @Setter private String prefix = "";

    /** Returns the color of a role, it uses hex color codes otherwise will return without a colour */
    @Setter private String color = "&f";

    /** Returns the weight of a role, this sorts roles into hierarchy order */
    @Setter private int weight = -1;

    /**
     * Returns permission nodes that a role has, these nodes are given to the user
     * which allows them access to certain features of the forum. They are
     * transformed into GrantedAuthority
     * @see org.springframework.security.core.GrantedAuthority
     */
    private List<String> permissions = new ArrayList<>();

    /** Returns roles that are subsidiaries of parent role */
    @JsonSerialize(using = RankListSerializer.class)
    @JsonDeserialize(using = RankListDeserializer.class)
    @DBRef private List<Rank> inherits = new ArrayList<>();

    public void sync(Rank newRank) {
        this.name = newRank.name;
        this.displayOrder = newRank.displayOrder;
        this.defaultRank = newRank.defaultRank;
        this.hidden = newRank.hidden;
        this.prefix = newRank.prefix;
        this.color = newRank.color;
        this.weight = newRank.weight;
        this.permissions = newRank.permissions;
        this.inherits = newRank.inherits;
    }

    /**
     * Permissions that are from parent role included with child
     * role permissions.
     *
     * @return Permissions from parent and child roles
     */
    public List<String> getCompoundedPermissions() {
        List<String> toReturn = new ArrayList<>(this.permissions);
        for (Rank inheritedRole : this.inherits) {
            toReturn.addAll(inheritedRole.getCompoundedPermissions());
        }
        return toReturn;
    }

    /**
     * Returns whether a parent role or child of the
     * parent contains a permission node
     *
     * @param node Permission to check
     * @return Whether it contains it or not.
     */
    public boolean hasPermission(String node) {
        return permissions.contains(node) || getCompoundedPermissions().contains(node);
    }

    /**
     * Adds a permission node to the parent role
     *
     * @param node Permission to add
     */
    public void addPermission(String node) {
        permissions.add(node);
    }

}
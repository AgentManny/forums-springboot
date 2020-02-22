package gg.manny.forums.forums.role;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "ranks")
public class Role implements Comparable<Role> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Id
    @NonNull
    private String id;

    @Setter
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String name;

    @Setter
    private String prefix = "";
    @Setter
    private String color = "#ffff";
    @Setter
    private int weight = -1;

    private List<String> permissions = new ArrayList<>();
    @DBRef private List<Role> inherits = new ArrayList<>();

    public String getDisplayName() {
        return color + name;
    }

    public List<String> getCompoundedPermissions() {
        List<String> toReturn = new ArrayList<>(this.permissions);
        for (Role inheritedRole : getInheritedRoles()) {
            toReturn.addAll(inheritedRole.getCompoundedPermissions());
        }
        return toReturn;
    }

    public boolean hasPermission(String node) {
        return permissions.contains(node) || getCompoundedPermissions().contains(node);
    }

    public void addPermission(String node) {
        permissions.add(node);
    }

    public List<Role> getInheritedRoles() {
        return inherits;
    }

    @Override
    public int compareTo(Role role) {
        return role.getWeight() - this.getWeight();
    }
}
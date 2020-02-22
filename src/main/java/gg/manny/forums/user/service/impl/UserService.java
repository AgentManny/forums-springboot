package gg.manny.forums.user.service.impl;

import gg.manny.forums.role.Role;
import gg.manny.forums.role.RoleRepository;
import gg.manny.forums.user.User;
import gg.manny.forums.user.UserRepository;
import gg.manny.forums.user.grant.Grant;
import gg.manny.forums.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserService implements IUserService, UserDetailsService {

    public static final UUID TEST_UUID = UUID.fromString("2f6f44cf-19a2-442a-944b-ede88be55651");

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public void saveUser(User user) {
        user.setId(TEST_UUID);

        user.setDateJoined(new Date(System.currentTimeMillis()));
        user.setDateLastSeen(new Date(System.currentTimeMillis()));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegistered(true);

        Role userRole = roleRepository.findByName("ADMIN");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("Owner");
            userRole.setColor("#4444");
            userRole.setWeight(Integer.MAX_VALUE);
            userRole.addPermission("permission.test");
            roleRepository.save(userRole);
        }

        Grant userGrant = new Grant();
        userGrant.setIssuedBy(TEST_UUID);
        userGrant.setRole(userRole);
        userGrant.setReason("Test :)");
        user.addGrant(userGrant);

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getGrants());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(List<Grant> grants) {
        List<GrantedAuthority> permissions = new ArrayList<>();
        for (Grant grant : grants) {
            Role role = grant.getRole();
            role.getCompoundedPermissions().forEach(node -> {
                permissions.add(new SimpleGrantedAuthority(node));
            });
        }

        return permissions;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}

package gg.manny.forums.user.service;

import gg.manny.forums.rank.Rank;
import gg.manny.forums.rank.RankRepository;
import gg.manny.forums.user.User;
import gg.manny.forums.user.UserRepository;
import gg.manny.forums.user.grant.Grant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private RankRepository roleRepository;

    @Autowired private BCryptPasswordEncoder encoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    /**
     * Creating registered users for the first time
     *
     * @param user User to register
     */
    public void createUser(User user) {
        user.setId(user.getUsername().equalsIgnoreCase("Mannys") ? UUID.fromString("2f6f44cf-19a2-442a-944b-ede88be55651") : UUID.randomUUID());
        user.setDateJoined(new Date(System.currentTimeMillis()));
        user.setDateLastSeen(new Date(System.currentTimeMillis()));

        user.setPassword(encoder.encode(user.getPassword()));

        user.setRegistered(true); // todo add confirmations by email

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (username.contains("@") ? userRepository.findByEmail(username) : userRepository.findByUsernameIgnoreCase(username))
                .orElse(null);
        if(user != null) {
            System.out.println("USER FROUND" + user.getUsername());
            List<GrantedAuthority> authorities = getUserAuthority(user.getGrants());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(List<Grant> grants) {
        List<GrantedAuthority> permissions = new ArrayList<>();
        for (Grant grant : grants) {
            if (grant.isActive()) {
                Rank rank = grant.getRank();
                rank.getCompoundedPermissions().forEach(node -> {
                    permissions.add(new SimpleGrantedAuthority(node));
                });
            }
        }

        return permissions;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}

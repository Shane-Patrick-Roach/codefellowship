package com.codefellowship.codefellowship.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bio;


    @OneToMany(mappedBy = "postsOfUser", cascade = CascadeType.ALL)
    @OrderBy("createdAt")
    List<Post> postsOfThisUser;


    @ManyToMany(mappedBy = "usersWhoFollowMe")
    Set<ApplicationUser> usersWhoIFollow;


    @ManyToMany
    @JoinTable(
            name = "iFollow_to_followMe",
            joinColumns = {@JoinColumn(name="iFollow")},
            inverseJoinColumns = {@JoinColumn(name="followMe")}
    )
    Set<ApplicationUser> usersWhoFollowMe;

    public Set<ApplicationUser> getUsersWhoIFollow() {
        return usersWhoIFollow;
    }

    public void setUsersWhoIFollow(Set<ApplicationUser> usersWhoIFollow) {
        this.usersWhoIFollow = usersWhoIFollow;
    }

    public Set<ApplicationUser> getUsersWhoFollowMe() {
        return usersWhoFollowMe;
    }

    public void setUsersWhoFollowMe(Set<ApplicationUser> usersWhoFollowMe) {
        this.usersWhoFollowMe = usersWhoFollowMe;
    }

    public List<Post> getPostsOfThisUser() {
        return postsOfThisUser;
    }

    public void setPostsOfThisUser(List<Post> postsOfThisUser) {
        this.postsOfThisUser = postsOfThisUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

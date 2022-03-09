package com.codefellowship.codefellowship.controllers;

import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.model.Post;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import com.codefellowship.codefellowship.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/signup")
    public String getSignUpPage(Principal p, Model m){
        if (p != null){
            String username = p.getName();
            m.addAttribute("username", username);
        }
        return "signup.html";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal p, Model m){
        if (p != null){
            String username = p.getName();
            m.addAttribute("username", username);
        }
        return "login.html";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username,
                                   String password,
                                   String firstName,
                                   String lastName,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
                                   String bio){
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setDateOfBirth(dateOfBirth);
        applicationUser.setBio(bio);
        String hashedPassword = passwordEncoder.encode(password);
        applicationUser.setPassword(hashedPassword);
        applicationUserRepository.save(applicationUser);

        //automatically logs in authorized users.
        authWithHttpServletRequest(username, password);

        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (SecurityException | ServletException se) {
            System.out.println("Error with auto login.");
            se.printStackTrace();
        }
    }


    @GetMapping("/myprofile")
    public RedirectView getMyProfilePage(Principal p, Model m){
        if (p != null){
            String username = p.getName();
            ApplicationUser applicationUser = (ApplicationUser) applicationUserRepository.findByUsername(username);
            String firstName = applicationUser.getFirstName();
            LocalDate dateOfBirth = applicationUser.getDateOfBirth();
            m.addAttribute("username", username);
            m.addAttribute("firstName", firstName);
            m.addAttribute("applicationUser", applicationUser);
        }
        return new RedirectView("/");
    }

    @GetMapping("/")
    public String getHomePage(Principal p, Model m){
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = (ApplicationUser) applicationUserRepository.findByUsername(username);
            String firstName = applicationUser.getFirstName();
            LocalDate dateOfBirth = applicationUser.getDateOfBirth();
            List<Post> posts = applicationUser.getPostsOfThisUser();
            m.addAttribute("username", username);
            m.addAttribute("firstName", firstName);
            m.addAttribute("applicationUser", applicationUser);
            m.addAttribute("posts", posts);
        }
        return "secretPage.html";
    }

    @PostMapping("/add-post")
    public RedirectView createUser(Long applicationUserId, String body){

        Date date = new Date();
        long millis = date.getTime();
        Timestamp createdAt = new Timestamp(millis);

        ApplicationUser postsOfUser = applicationUserRepository.getById(applicationUserId);
        Post postToAdd = new Post(body,createdAt);
        postToAdd.setPostsOfUser(postsOfUser);
        postRepository.save(postToAdd);

        return new RedirectView("/");
    }





}

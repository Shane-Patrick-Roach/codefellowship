package com.codefellowship.codefellowship.controllers;


import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.model.Post;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import com.codefellowship.codefellowship.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@Controller
public class CodefellowsController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/finduserprofile/{id}")
    public String getProfileOfUser(@PathVariable long id, Principal p, Model m){

        if (p != null){
            String username = p.getName();
            m.addAttribute("username", username);
        }

        ApplicationUser findUserProfile;
        String findUsername;

        try{
            findUserProfile = applicationUserRepository.getById(id);
            findUsername = findUserProfile.getUsername();
        } catch (EntityNotFoundException enf){
            enf.printStackTrace();
            return "findUser.html";
        }

        m.addAttribute("findUsername", findUsername);
        m.addAttribute("findUserProfile", findUserProfile);

        return "findUser.html";
    }


    @GetMapping("/view-profile-home")
    public RedirectView getProfileFromHome(long userId){
        ApplicationUser findUser = applicationUserRepository.getById(userId);
        Long profileId = findUser.getId();

        return new RedirectView("/finduserprofile/" + profileId);
    }


    @GetMapping("/view-profile-post")
    public RedirectView getProfileFromPost(long postId){
        Post clickedPost = postRepository.getById(postId);

        ApplicationUser originalUserPoster = clickedPost.getPostsOfUser();
        Long userId = originalUserPoster.getId();

       return new RedirectView("/finduserprofile/" + userId);
    }




}

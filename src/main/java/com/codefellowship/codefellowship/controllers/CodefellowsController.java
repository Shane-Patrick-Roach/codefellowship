package com.codefellowship.codefellowship.controllers;


import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@Controller
public class CodefellowsController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;


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




}

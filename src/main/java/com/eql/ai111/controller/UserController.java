package com.eql.ai111.controller;

import com.eql.ai111.entities.Produit;
import com.eql.ai111.entities.User;
import com.eql.ai111.service.ProduitService;
import com.eql.ai111.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProduitService produitService;

    @GetMapping("/")
    public String home( Model model){

        List<Produit> produits = produitService.getAllProduit();
        model.addAttribute("produits", produits);

        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationfrom(Model model){
        User user =new User();
        model.addAttribute("user",user);
        return  "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        User existingUser = userService.findUserByEmail(user.getEmail());


        if (existingUser!= null && existingUser.getEmail() !=null){
            result.rejectValue("email",null,"Email already in use");
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "/register";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/indexUsers")
    public String indexUsers(@AuthenticationPrincipal UserDetails userPrincipal ,Model model){

        User user = userService.findUserByEmail(userPrincipal.getUsername());

        if (user != null ) {

            model.addAttribute("connectedUser", user);
        }

        List<Produit> produits = produitService.getAllProduit();
        model.addAttribute("produits", produits);
        System.out.println(model);
        return "indexUsers";}

    @GetMapping("/monCompte")
    public String monCompte(@AuthenticationPrincipal UserDetails userPrincipal ,Model model){

        User user = userService.findUserByEmail(userPrincipal.getUsername());

        if (user != null ) {

            model.addAttribute("connectedUser", user);
        }

        return "monCompte";}

    @GetMapping("/showFormUpdate")
    public String showFormUpdate(@AuthenticationPrincipal UserDetails userPrincipal ,Model model){
        User user = userService.findUserByEmail(userPrincipal.getUsername());

        if (user != null ) {

            model.addAttribute("connectedUser", user);
        }
        return "/update_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("connectedUser") User user){
        userService.updateUser(user);
        return "redirect:/indexUsers";
    }

//    @PostMapping("/saveUser")
//    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
//        User existingUser = userService.findUserByEmail(user.getEmail());
//        if (existingUser!= null && existingUser.getEmail() !=null){
//            result.rejectValue("email",null,"Email already in use");
//        }
//        if(result.hasErrors()){
//            model.addAttribute("user",user);
//            return "/indexUsers";
//        }
//        userService.saveUser(user);
//        return "redirect:/indexUsers";
//    }



    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUserById(id);

        return "redirect:/indexUsers";
    }
}

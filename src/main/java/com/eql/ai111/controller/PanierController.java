package com.eql.ai111.controller;


import com.eql.ai111.entities.Produit;
import com.eql.ai111.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PanierController {

    @Autowired
    private ProduitService produitService;



    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<Produit> panier = (List<Produit>) session.getAttribute("panier");
        Long nombre = (Long) session.getAttribute("nombre");

        if (panier == null) {
            panier = new ArrayList<>();
            nombre = 80L;
            Produit produit = produitService.getProduitById(id);
            panier.add(produit);
            session.setAttribute("panier", panier);
            session.setAttribute("nombre", nombre);
        } else {
            nombre += 80L;
            Produit produit = produitService.getProduitById(id);
            panier.add(produit);
            session.setAttribute("panier", panier);
            session.setAttribute("nombre", nombre);
        }

        return "redirect:/indexUsers";
    }


    @GetMapping("/voirPanier")
    public String voirPanier(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();

        List<Produit> panier = (List<Produit>) session.getAttribute("panier");
        Long nombre = (Long) session.getAttribute("nombre");

        model.addAttribute("produits", panier);
        model.addAttribute("nombre", nombre);
        System.out.println("test" + model );

        return "/panier";

    }

    @GetMapping("/confirmation")
    public String confirmation(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();

        List<Produit> panier = (List<Produit>) session.getAttribute("panier");
        Long nombre = (Long) session.getAttribute("nombre");

        model.addAttribute("produits", panier);
        model.addAttribute("nombre", nombre);
        System.out.println("test" + model );
        return "/confirmation";
    }

    @GetMapping("/vide")
    public String vide(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();

        List<Produit> panier = (List<Produit>) session.getAttribute("panier");
        panier.removeAll(panier);

        Long nombre = (Long) session.getAttribute("nombre");
        nombre = 0L;
        session.setAttribute("nombre", nombre);


        return "redirect:/indexUsers";
    }


}

package com.eql.ai111.service;

import com.eql.ai111.entities.Produit;
import com.eql.ai111.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public List<Produit> getAllProduit() {
        return produitRepository.findAll();
    }

    @Override
    public Produit getProduitById(Long id) {
        Optional<Produit> optional = produitRepository.findById(id);

        Produit produit = null;

        if (optional.isPresent()){
            produit = optional.get();
        } else {
            throw  new RuntimeException("Produit not found for id :" +id);
        }
        return produit;
    }
}

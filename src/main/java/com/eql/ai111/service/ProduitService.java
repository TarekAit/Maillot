package com.eql.ai111.service;

import com.eql.ai111.entities.Produit;

import java.util.List;

public interface ProduitService {

    List<Produit> getAllProduit();

    Produit getProduitById (Long id);
}

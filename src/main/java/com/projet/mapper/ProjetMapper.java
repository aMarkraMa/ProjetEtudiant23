package com.projet.mapper;

import com.projet.entity.Projet;

import java.util.ArrayList;

public interface ProjetMapper {
    int insertProjet(Projet projet);

    int deleteProjet(Projet projet);
    int updateProjet(Projet projet);
    ArrayList<Projet> selectProjet(Projet projet);
}

package com.projet.mapper;

import com.projet.entity.Projet;

import java.util.List;

public interface ProjetMapper {

    List<Projet> selectAll();
    Projet selectById(int idProjet);
    List<Projet> selectByCondition(Projet projet);
    void addProjet(Projet projet);
    int updateProjet(Projet projet);
    void deleteById(int idProjet);
}

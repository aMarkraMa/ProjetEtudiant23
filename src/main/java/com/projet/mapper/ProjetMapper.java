package com.projet.mapper;

import com.projet.entity.Projet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjetMapper {

    List<Projet> selectAll();
    Projet selectById(int idProjet);
    List<Projet> selectByCondition(Projet projet);
    void addProjet(Projet projet);
    int updateProjet(Projet projet);
    void deleteById(int idProjet);
    
    List<Integer> getIdsProjet();
    
    List<Integer> getIdsProjetByCondition(@Param("nomMatiere") String nomMatiere, @Param("sujet") String sujet);
}

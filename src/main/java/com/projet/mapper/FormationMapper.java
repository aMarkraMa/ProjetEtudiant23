package com.projet.mapper;

import com.projet.entity.Etudiant;
import com.projet.entity.Formation;

import java.util.List;

public interface FormationMapper {
    Formation selectById(int idFormation);
    List<Formation> selectByCondition(Formation formation);
    void addFormation(Formation formation);
    int updateFormation(Formation formation);
    void deleteById(int idFormation);

    List<String> getNomsFormation();

    List<String> getPromotions();
    
    List<Formation> selectAll();
}

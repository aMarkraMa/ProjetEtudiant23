package com.projet.mapper;

import com.projet.entity.Etudiant;

import java.util.List;

public interface EtudiantMapper {
    /*cherche tous les attributs*/
    List<Etudiant> selectAll();
    Etudiant selectById(Etudiant etudiant);
    List<Etudiant> selectByCondition(Etudiant etudiant);
    void addEtudiant(Etudiant etudiant);
    int updateEtudiant(Etudiant etudiant);
    void deleteById(int idEtudiant);
    
    List<String> getNomsEtudiant();
    
    List<String> getPrenomsEtudiant();
    
    List<Integer> getIdsEtudiant();

}

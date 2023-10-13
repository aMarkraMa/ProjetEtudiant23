package com.projet.mapper;

import com.projet.entity.Etudiant;

import java.util.List;

public interface EtudiantMapper {
    public List<Etudiant> selectAll();
    public Etudiant selectById();
}

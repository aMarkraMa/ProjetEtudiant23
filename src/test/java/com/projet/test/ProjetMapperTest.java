package com.projet.test;

import com.projet.entity.Projet;
import com.projet.mapper.ProjetMapper;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProjetMapperTest {
    private SqlSession sqlSession;
    @Before
    public void setUp() {
        sqlSession = MyBatisUtils.getSqlSession();
    }

 
    @Test
    public void testSelectById(){
        // Obtenir une instance de l'interface UserMapper
        ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);

        int id = 1;

        Projet projet = projetMapper.selectById(id);

        if(projet != null){
            // Résultats des assertions
            assertEquals(1, (int)projet.getIdProjet());
        }


    }
    @Test
    public void testSelectByCondition(){
        // Obtenir une instance de l'interface UserMapper
        ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);

        //Appeler la méthode selectByCondition
        Projet projet = new Projet();
        projet.setIdProjet(1);

        List<Projet> projets = projetMapper.selectByCondition(projet);
        
        System.out.println(projets.get(0).getIdProjet());

        // Résultats des assertions
        assertEquals(1, (int)projets.get(0).getIdProjet());

        //Appeler la méthode selectByCondition
        projet = new Projet();
        projet.setDatePrevueRemise(LocalDate.of(2023,9,8));

        projets = projetMapper.selectByCondition(projet);
        if(!projets.isEmpty()){
            // Résultats des assertions
            assertEquals(LocalDate.of(2023,9,8), projets.get(0).getDatePrevueRemise());
        }


    }
    @Test
    public void testAddProjet(){
        // Obtenir une instance de l'interface UserMapper
        ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);

        Projet projet = new Projet();

        projet.setSujet("JeuVideo");
        projet.setNomMatiere("JAVA");
        projet.setDatePrevueRemise(LocalDate.of(2023,12,10));


        projetMapper.addProjet(projet);


        //System.out.println(etudiant.getIdEtudiant());

        sqlSession.commit();

        List<Projet> projets = projetMapper.selectByCondition(projet);
        if(!projets.isEmpty()){
            // Résultats des assertions
            assertEquals(projet.getNomMatiere(), projets.get(0).getNomMatiere());
            assertEquals(projet.getSujet(), projets.get(0).getSujet());
            assertEquals(projet.getDatePrevueRemise(), projets.get(0).getDatePrevueRemise());
        }
    }

    @Test
    public void testUpdateProjet(){
        // Obtenir une instance de l'interface UserMapper
        ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);

        Projet projet = new Projet();

        projet.setIdProjet(3);
        projet.setSujet("Jeu");
        projet.setNomMatiere("C++");
        projet.setDatePrevueRemise(LocalDate.of(2023,12,11));

        int colomn = projetMapper.updateProjet(projet);

        //System.out.println(etudiant.getIdEtudiant());

        sqlSession.commit();
        System.out.println(colomn);

        List<Projet> projets = projetMapper.selectByCondition(projet);

        if(!projets.isEmpty()){
            // Résultats des assertions
            assertEquals(projet.getNomMatiere(), projets.get(0).getNomMatiere());
            assertEquals(projet.getSujet(), projets.get(0).getSujet());
            assertEquals(projet.getDatePrevueRemise(), projets.get(0).getDatePrevueRemise());
        }
    }
    @Test
    public void testDeleteById(){
        // Obtenir une instance de l'interface UserMapper
        ProjetMapper projetMapper = sqlSession.getMapper(ProjetMapper.class);

        int id = 4;

        projetMapper.deleteById(id);
        sqlSession.commit();

        Projet projet = projetMapper.selectById(id);

        // Résultats des assertions
        assertNull(projet);
    }
}

package com.projet.test;

import com.projet.entity.Formation;
import com.projet.mapper.FormationMapper;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FormationMapperTest {

    private SqlSession sqlSession;
    @Before
    public void setUp() {
        sqlSession = MyBatisUtils.getSqlSession();
    }

    @After
    public void tearDown() {
        MyBatisUtils.closeSqlSession(sqlSession);
    }
    @Test
    public void testSelectById(){
        // Obtenir une instance de l'interface UserMapper
        FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);

        int id = 1;

        Formation formation = formationMapper.selectById(id);

        // Résultats des assertions
        assertEquals(1, (int)formation.getIdFormation());

    }
    @Test
    public void testSelectByCondition(){
        // Obtenir une instance de l'interface UserMapper
        FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);

        //Appeler la méthode selectByCondition
        Formation formation = new Formation();
        formation.setIdFormation(1);

        List<Formation> formations = formationMapper.selectByCondition(formation);

        // Résultats des assertions
        assertEquals(1, (int)formations.get(0).getIdFormation());

        //Appeler la méthode selectByCondition
        formation = new Formation();
        formation.setNomFormation("MIAGE");

        formations = formationMapper.selectByCondition(formation);

        // Résultats des assertions
        assertEquals("MIAGE", formations.get(0).getNomFormation());
    }
    @Test
    public void testAddFormation(){
        // Obtenir une instance de l'interface UserMapper
        FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);

        Formation formation = new Formation();

        formation.setNomFormation("MIAGE");
        formation.setPromotion("FA");


        formationMapper.addFormation(formation);


        //System.out.println(etudiant.getIdEtudiant());

        sqlSession.commit();

        List<Formation> formations = formationMapper.selectByCondition(formation);

        // Résultats des assertions
        assertEquals("MIAGE", formations.get(0).getNomFormation());
        assertEquals("FA", formations.get(0).getPromotion());
    }

    @Test
    public void testUpdateFormation(){
        // Obtenir une instance de l'interface UserMapper
        FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);

        Formation formation = new Formation();

        formation.setIdFormation(1);
        formation.setNomFormation("I2D");

        int colomn = formationMapper.updateFormation(formation);

        //System.out.println(etudiant.getIdEtudiant());

        sqlSession.commit();
        System.out.println(colomn);

        List<Formation> formations = formationMapper.selectByCondition(formation);

        // Résultats des assertions
        assertEquals("I2D", formations.get(0).getNomFormation());
        assertEquals("FI", formations.get(0).getPromotion());
    }
    @Test
    public void testDeleteById(){
        // Obtenir une instance de l'interface UserMapper
        FormationMapper formationMapper = sqlSession.getMapper(FormationMapper.class);

        int id = 2;


        formationMapper.deleteById(id);
        sqlSession.commit();

        Formation formation = formationMapper.selectById(id);

        // Résultats des assertions
        assertNull(formation);
    }


}

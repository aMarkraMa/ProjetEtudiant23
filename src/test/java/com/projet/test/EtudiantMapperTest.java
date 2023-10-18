package com.projet.test;

import com.projet.entity.Etudiant;
import com.projet.mapper.EtudiantMapper;
import com.projet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EtudiantMapperTest {
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
    public void testSelectByCondition(){
        // Obtenir une instance de l'interface UserMapper
        EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);

        //Appeler la méthode getUserById.
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomEtudiant("LI");
        etudiant.setPrenomEtudiant("Yingxuan");
        etudiant.setIdFormation(1);
        List<Etudiant> etudiants = etudiantMapper.selectByCondition(etudiant);

        // Résultats des assertions
        assertEquals(1, (int)etudiants.get(0).getIdEtudiant());
        assertEquals("LI", etudiants.get(0).getNomEtudiant());
        assertEquals("Yingxuan", etudiants.get(0).getPrenomEtudiant());
        assertEquals(1, (int)etudiants.get(0).getIdFormation());
    }
}

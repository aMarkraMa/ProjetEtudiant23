// package com.projet.test;
//
// import com.projet.entity.Etudiant;
// import com.projet.entity.Formation;
// import com.projet.mapper.EtudiantMapper;
// import com.projet.utils.MyBatisUtils;
// import org.apache.ibatis.session.SqlSession;
// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;
//
// import java.util.List;
//
// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;
//
// public class EtudiantMapperTest {
// 	private SqlSession sqlSession;
//
// 	@Before
// 	public void setUp() {
// 		sqlSession = MyBatisUtils.getSqlSession();
// 	}
//
//
//
// 	@Test
// 	public void testSelectByCondition() {
// 		// Obtenir une instance de l'interface UserMapper
// 		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
//
// 		// Appeler la méthode selectByCondition
// 		Etudiant etudiant = new Etudiant();
// 		etudiant.setIdEtudiant(1);
// 		etudiant.setNomEtudiant("LI");
// 		etudiant.setPrenomEtudiant("Yingxuan");
// 		etudiant.setFormation(new Formation(1, "I2D", "FI"));
// 		List<Etudiant> etudiants = etudiantMapper.selectByCondition(etudiant);
//
// 		// Résultats des assertions
// 		assertEquals(1, (int) etudiants.get(0).getIdEtudiant());
// 		assertEquals("LI", etudiants.get(0).getNomEtudiant());
// 		assertEquals("Yingxuan", etudiants.get(0).getPrenomEtudiant());
// 		assertEquals(1, (int) etudiants.get(0).getFormation().getIdFormation());
//
// 		// Appeler la méthode selectByCondition
// 		etudiant = new Etudiant();
// 		etudiant.setNomEtudiant("LI");
// 		etudiant.setPrenomEtudiant("Yingxuan");
// 		etudiant.setFormation(new Formation(1, "I2D", "FI"));
// 		etudiants = etudiantMapper.selectByCondition(etudiant);
//
// 		// Résultats des assertions
// 		assertEquals(1, (int) etudiants.get(0).getIdEtudiant());
// 		assertEquals("LI", etudiants.get(0).getNomEtudiant());
// 		assertEquals("Yingxuan", etudiants.get(0).getPrenomEtudiant());
// 		assertEquals(1, (int) etudiants.get(0).getFormation().getIdFormation());
// 	}
//
// 	@Test
// 	public void testAddEtudiant() {
// 		// Obtenir une instance de l'interface UserMapper
// 		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
//
// 		Etudiant etudiant = new Etudiant();
//
// 		etudiant.setNomEtudiant("Bernard");
// 		etudiant.setPrenomEtudiant("Henri");
// 		etudiant.setFormation(new Formation(1, "I2D", "FI"));
//
// 		etudiantMapper.addEtudiant(etudiant);
//
//
// 		// System.out.println(etudiant.getIdEtudiant());
//
// 		sqlSession.commit();
//
// 		List<Etudiant> etudiants = etudiantMapper.selectByCondition(etudiant);
//
// 		// Résultats des assertions
// 		assertEquals("Bernard", etudiants.get(0).getNomEtudiant());
// 		assertEquals("Henri", etudiants.get(0).getPrenomEtudiant());
// 		assertEquals(1, (int) etudiants.get(0).getFormation().getIdFormation());
// 	}
//
// 	@Test
// 	public void testUpdateEtudiant() {
// 		// Obtenir une instance de l'interface UserMapper
// 		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
//
// 		Etudiant etudiant = new Etudiant();
//
// 		etudiant.setNomEtudiant("Petit");
// 		etudiant.setIdEtudiant(1);
// 		// etudiant.setFormation(new Formation());
//
// 		int colomn = etudiantMapper.updateEtudiant(etudiant);
//
//
// 		// System.out.println(etudiant.getIdEtudiant());
//
// 		sqlSession.commit();
// 		System.out.println(colomn);
//
// 		List<Etudiant> etudiants = etudiantMapper.selectByCondition(etudiant);
//
// 		// Résultats des assertions
// 		assertEquals("Petit", etudiants.get(0).getNomEtudiant());
// 		assertEquals(1, (int) etudiants.get(0).getFormation().getIdFormation());
// 	}
//
// 	@Test
// 	public void testDeleteById() {
// 		// Obtenir une instance de l'interface UserMapper
// 		EtudiantMapper etudiantMapper = sqlSession.getMapper(EtudiantMapper.class);
//
// 		int id = 5;
//
//
// 		etudiantMapper.deleteById(id);
// 		sqlSession.commit();
//
// 		// Etudiant etudiant = etudiantMapper.selectById(id);
//
// 		// Résultats des assertions
// 		// assertNull(etudiant);
// 	}
//
//
// }
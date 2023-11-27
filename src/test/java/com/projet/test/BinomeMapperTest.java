// package com.projet.test;
//
// import com.projet.entity.Binome;
// import com.projet.entity.Projet;
// import com.projet.mapper.BinomeMapper;
// import com.projet.utils.MyBatisUtils;
// import org.apache.ibatis.session.SqlSession;
// import org.junit.Test;
//
// public class BinomeMapperTest {
// 	@Test
// 	public void testSelectByIdBinomeAndIdProje() {
// 		SqlSession sqlSession = null;
// 		try {
// 			sqlSession = MyBatisUtils.getSqlSession();
// 			BinomeMapper mapper = sqlSession.getMapper(BinomeMapper.class);
// 			Binome binome = new Binome();
// 			binome.setIdBinome(3);
// 			Projet projet = new Projet();
// 			projet.setIdProjet(1);
// 			binome.setProjet(projet);
// 			Binome binomeDB = mapper.selectByIdBinomeAndIdProjet(binome);
// 			System.out.println(binomeDB);
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		} finally {
// 			if (sqlSession != null) {
// 				sqlSession.close();
// 			}
// 		}
// 	}
//
//
// }

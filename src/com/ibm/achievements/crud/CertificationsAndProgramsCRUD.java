package com.ibm.achievements.crud;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.achievements.model.Achievement;
import com.ibm.achievements.model.BoardReview;
import com.ibm.achievements.model.CertificationsAndProgram;
import com.ibm.db2.jcc.am.mp;

public class CertificationsAndProgramsCRUD extends AchievementCRUD {

	@Override
	public boolean addAchievement(Achievement achievement,
			String achievementJson) {

		try {
			JSONObject jsonObject = new JSONObject(achievementJson);
			CertificationsAndProgram certificationsAndProgram = new CertificationsAndProgram();
			certificationsAndProgram.setAchievementId(achievement.getAchievementId());
			certificationsAndProgram.setTypeOfCertification(jsonObject.get("TypeOfCertification").toString());
			certificationsAndProgram.setAchievement(achievement);
			final String PERSISTENT_UNIT_NAME = "Achievements-App";
			final EntityManagerFactory factory = Persistence
					.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
			EntityManager entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(certificationsAndProgram);
			entityManager.getTransaction().commit();
			entityManager.close();
			try {
				Class classDifination = Class.forName("com.ibm.achievements.crud."
						+"CertificationsAndPrograms"+ certificationsAndProgram.getTypeOfCertification() + "CRUD");
				CertificationAndProgramsTypesCRUDI certificationAndProgramsTypesCRUDI = (CertificationAndProgramsTypesCRUDI)classDifination.newInstance() ;
				certificationAndProgramsTypesCRUDI.addCertificationAndPrograms(certificationsAndProgram, jsonObject) ; 				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateAchievement(JSONObject achievementJson) { // edit
																	// values
		final String PERSISTANT_UNIT_NAME = "Achievements-App";
		final EntityManagerFactory factroy = Persistence
				.createEntityManagerFactory(PERSISTANT_UNIT_NAME);
		EntityManager entityManager = factroy.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager
				.createQuery("UPDATE CertificationsAndProgram cab SET  cab.typeOfCertification =:typeOfCertification WHERE cab.achievementId =:achievementId");
		try {
			query.setParameter("typeOfCertification",
					achievementJson.get("typeOfCertification").toString());
			query.setParameter("achievementId",
					Integer.valueOf(achievementJson.get("achievementId").toString()));
			query.executeUpdate();
			entityManager.getTransaction().commit();
			entityManager.close();
			try {
				Class classDifination = Class.forName("com.ibm.achievements.crud."
						+"CertificationsAndPrograms"+ achievementJson.get("typeOfCertification").toString() + "CRUD");
				CertificationAndProgramsTypesCRUDI certificationAndProgramsTypesCRUDI = (CertificationAndProgramsTypesCRUDI)classDifination.newInstance() ;
				certificationAndProgramsTypesCRUDI.updateCertificationAndPrograms(achievementJson) ; 				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public String getAchievement(Achievement achievement) {

		final String PERSISTENCE_UNIT_NAME = "Achievements-App";
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = factory.createEntityManager();
		Query query = entityManager
				.createQuery("SELECT cp from CertificationsAndProgram cp WHERE cp.achievementId=:achievementId ");
		query.setParameter("achievementId", achievement.getAchievementId());
		CertificationsAndProgram certificationandprograms = (CertificationsAndProgram) query
				.getSingleResult();
		certificationandprograms.setAchievement(achievement);
		if(certificationandprograms.getTypeOfCertification().equals("Product")||certificationandprograms.getTypeOfCertification().equals("Professional")){
		try {
			Class classDifination = Class.forName("com.ibm.achievements.crud."
					+"CertificationsAndPrograms"+ certificationandprograms.getTypeOfCertification() + "CRUD");
			CertificationAndProgramsTypesCRUDI certificationAndProgramsTypesCRUDI = (CertificationAndProgramsTypesCRUDI)classDifination.newInstance() ;
			return certificationAndProgramsTypesCRUDI.getCertificationsAndProgram(certificationandprograms.getAchievementId()) ; 				
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		ObjectMapper mapper = new ObjectMapper() ; 
		try {
			return mapper.writeValueAsString(achievement) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}

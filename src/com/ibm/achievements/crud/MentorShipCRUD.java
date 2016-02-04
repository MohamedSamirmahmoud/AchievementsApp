package com.ibm.achievements.crud;
import java.io.IOException;









import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.achievements.model.Achievement;
import com.ibm.achievements.model.Mentorship;

public class MentorShipCRUD extends AchievementCRUD {

	@Override
	public boolean addAchievement(Achievement achievement,
			String achievementJson) {
		try {
			JSONObject jsonObject = new JSONObject(achievementJson);
			Mentorship mentorship = new Mentorship();
			mentorship.setAchievement(achievement);
			mentorship.setAchievementId(achievement.getAchievementId());
			mentorship.setTypeOfMentorship(jsonObject.get("typeOfMentorship")
					.toString());
			mentorship.setDescription(jsonObject.getString("description"));
			
			final String PERSISTENT_UNIT_NAME = "Achievements-App";
			final EntityManagerFactory factory = Persistence
					.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
			EntityManager entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(mentorship);
			entityManager.getTransaction().commit();
			entityManager.close();
			if(mentorship.getTypeOfMentorship().equals("Certification")||(mentorship.getTypeOfMentorship().equals("Skill"))){
			Class classDefenation = Class.forName("com.ibm.achievements.crud."
					+"Mentorship"+ mentorship.getTypeOfMentorship() + "CRUD");
			MentorshipTypesCRUDI mentorshipTypesCRUDI = (MentorshipTypesCRUDI) classDefenation.newInstance() ; 
			mentorshipTypesCRUDI.addMentorship(mentorship, jsonObject) ;
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateAchievement(JSONObject achievementJson) {

		final String PERSISTENCE_UNIT_NAME = "Achievements-App";
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query = em
				.createQuery("UPDATE Mentorship a SET a.typeOfMentorship= :type where a.achievementId= :achievementId" );
		try {
			query.setParameter("type", achievementJson.get("typeOfMentorship")
					.toString());
	 		query.setParameter("achievementId", Integer.valueOf(achievementJson.get("achievementId").toString()));

			query.executeUpdate();
			em.getTransaction().commit();
			em.close();
			if(achievementJson.get("typeOfMentorship").toString().equals("Certification")||(achievementJson.get("typeOfMentorship").toString().equals("Skill"))){
			Class classDefenation = Class.forName("com.ibm.achievements.crud."
					+"Mentorship"+ achievementJson.get("typeOfMentorship").toString() + "CRUD");
			MentorshipTypesCRUDI mentorshipTypesCRUDI = (MentorshipTypesCRUDI) classDefenation.newInstance() ; 
			mentorshipTypesCRUDI.updateMentorship(achievementJson) ;
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return false ;
	}

	@Override
	public String getAchievement(Achievement achievement) {
		final String PERSISTENCE_UNIT_NAME = "Achievements-App";
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = factory.createEntityManager();
		Query query = entityManager
				.createQuery("SELECT m  FROM Mentorship m WHERE m.achievementId=:achievementId");
		query.setParameter("achievementId", achievement.getAchievementId());
		Mentorship mentorship = (Mentorship) query.getSingleResult();
		if(mentorship.getTypeOfMentorship().equals("Skill")||mentorship.getTypeOfMentorship().equals("Certification")){
		Class classDefenation;
		try {
			classDefenation = Class.forName("com.ibm.achievements.crud."
					+"Mentorship"+ mentorship.getTypeOfMentorship() + "CRUD");
			MentorshipTypesCRUDI mentorshipTypesCRUDI = (MentorshipTypesCRUDI) classDefenation.newInstance() ; 
			return mentorshipTypesCRUDI.getAchievement(mentorship.getAchievementId()+"") ;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		  ObjectMapper mapper = new ObjectMapper() ;
		  DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			mapper.setDateFormat(df);
 		  try {
			return mapper.writeValueAsString(mentorship) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
	

}

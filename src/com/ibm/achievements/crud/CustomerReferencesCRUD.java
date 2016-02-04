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
import com.ibm.achievements.model.BoardReview;
import com.ibm.achievements.model.CustomerReference;
public class CustomerReferencesCRUD extends AchievementCRUD {

	@Override
	public boolean addAchievement(Achievement achievement,
			String achievementJson) {
		try {
			JSONObject jsonObject = new JSONObject(achievementJson);
			CustomerReference customerReference = new CustomerReference();
			customerReference.setAchievement(achievement);
			customerReference.setAchievementId(achievement.getAchievementId());
			customerReference.setBrand(jsonObject.get("brand").toString());
			customerReference.setBusUnit(jsonObject.get("busUnit").toString());
			customerReference.setCountry(jsonObject.get("country").toString());
			customerReference.setCustomerName(jsonObject.get("customerName").toString());
			customerReference.setEngagementName(jsonObject.getString("engagmentName").toString());
		    final String PERSISTENT_UNIT_NAME = "Achievements-App";
			final EntityManagerFactory factory = Persistence
					.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
			EntityManager entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(customerReference);
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		} catch (JSONException e) {
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
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin(); 
		Query query = entityManager
				.createQuery("UPDATE CustomerReference cr SET cr.busUnit=:busUnit ,cr.brand=:brand ,cr.country=:country , cr.engagementName=:engagementName , cr.customerName=:customerName WHERE cr.achievementId=:achievementId ");
		try {
			query.setParameter("brand", achievementJson.get("brand").toString());
			query.setParameter("busUnit", achievementJson.get("busUnit").toString()) ;
			query.setParameter("country", achievementJson.get("country").toString()) ;
			query.setParameter("engagementName", achievementJson.get("engagementName").toString()) ;
			query.setParameter("customerName", achievementJson.get("customerName").toString()) ;
			query.setParameter("achievementId", Integer.valueOf(achievementJson.get("achievementId").toString())) ;
			query.executeUpdate() ;
			entityManager.getTransaction().commit();
			entityManager.close();
			return true ;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public String getAchievement(Achievement achievement) {
		     final String PERSISTENCE_UNIT_NAME = "Achievements-App" ; 
		     final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		     EntityManager entityManager = factory.createEntityManager();
		     Query query = entityManager.createQuery("SELECT cr from CustomerReference cr WHERE cr.achievementId=:achievementId ") ;
		     query.setParameter("achievementId", achievement.getAchievementId()) ;
		     CustomerReference customerreference = (CustomerReference)query.getSingleResult() ;
		     customerreference.setAchievement(achievement);
		     ObjectMapper mapper = new ObjectMapper() ;
		     DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				mapper.setDateFormat(df);
		     try {
			  return mapper.writeValueAsString(customerreference) ;
		     }catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				return null;
			}
	
}

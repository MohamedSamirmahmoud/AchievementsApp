package com.ibm.achievements.crud;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.achievements.model.Achievement;
import com.ibm.achievements.model.CustomerSave;
import com.ibm.achievements.model.Disclosure;

public class CustomerSaveCRUD extends AchievementCRUD {

	@Override
	public boolean addAchievement(Achievement achievement,
			String achievementJson) {

		try {
			JSONObject jsonObject = new JSONObject(achievementJson);
			CustomerSave customerSave = new CustomerSave();
			customerSave.setAchievement(achievement);
			customerSave.setAchievementId(achievement.getAchievementId());
			customerSave.setCountry(jsonObject.get("country").toString());
			customerSave.setCustomerName(jsonObject.get("customerName")
					.toString());
			customerSave.setCustomerProblem(jsonObject.get("customerProblem")
					.toString());
			customerSave.setEmployeeContribution(jsonObject.get(
					"employeeContribution").toString());
			customerSave.setEngagementName(jsonObject.get("engagementName")
					.toString());
			final String PERSISTENT_UNIT_NAME = "Achievements-App";
			final EntityManagerFactory factory = Persistence
					.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
			EntityManager entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(customerSave);
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

		final String PERSISTANT_UNIT_NAME = "Achievements-App";
		final EntityManagerFactory factroy = Persistence
				.createEntityManagerFactory(PERSISTANT_UNIT_NAME);
		EntityManager entityManager = factroy.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager
				.createQuery("UPDATE CustomerSave C SET  C.country=:country ,C.customerName=:customerName , C.customerProblem=:customerProblem  ,C.employeeContribution=:employeeContribution ,C.engagementName=:engagementName  WHERE C.achievementId=:achievementId");
		try {
			query.setParameter("country", achievementJson.get("country")
					.toString());
			query.setParameter("customerName",
					achievementJson.get("customerName").toString());
			query.setParameter("customerProblem",
					achievementJson.get("customerProblem").toString());
			query.setParameter("employeeContribution",
					achievementJson.get("employeeContribution").toString());
			query.setParameter("engagementName",
					achievementJson.get("engagementName").toString());
			query.setParameter("achievementId", Integer.valueOf(achievementJson
					.get("achievementId").toString()));
			query.executeUpdate();
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
	public String getAchievement(Achievement achievement) {

		final String PERSISTANT_UNIT_NAME = "Achievements-App";
		final EntityManagerFactory factroy = Persistence
				.createEntityManagerFactory(PERSISTANT_UNIT_NAME);
		EntityManager entityManager = factroy.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager
				.createQuery("SELECT c FROM CustomerSave c WHERE c.achievementId=:achievementId");
		query.setParameter("achievementId", achievement.getAchievementId());
		CustomerSave customerSave = (CustomerSave) query.getSingleResult();
		customerSave.setAchievement(achievement);
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		mapper.setDateFormat(df);
		try {

			return mapper.writeValueAsString(customerSave);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

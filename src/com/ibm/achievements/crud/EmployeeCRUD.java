package com.ibm.achievements.crud;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ibm.achievements.model.Achievement;
import com.ibm.achievements.model.Employee;

public class EmployeeCRUD {
	public static Employee getEmployeeById(int employeeId){
		final String PERSISTENCE_UNIT_NAME = "Achievements-App" ;
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = factory.createEntityManager() ;
		Query query = entityManager.createQuery("SELECT e from Employee e where e.employeeId=:employeeIdParam") ;
		query.setParameter("employeeIdParam", employeeId) ;
		return (Employee) query.getSingleResult() ;
	}
	public static void addAchievementToEmployee(int employeeId , Achievement achievement){
		final String PERSISTENCE_UNIT_NAME = "Achievements-App" ;
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager entityManager = factory.createEntityManager() ;
		Employee employee = entityManager.find(Employee.class, employeeId);
		entityManager.getTransaction().begin();
		employee.getAchievements().add(achievement);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	

}

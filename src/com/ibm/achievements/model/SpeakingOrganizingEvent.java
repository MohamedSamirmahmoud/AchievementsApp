package com.ibm.achievements.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SPEAKINGORGANIZINGEVENTS database table.
 * 
 */
@Entity
@Table(name="SPEAKINGORGANIZINGEVENTS")
@NamedQuery(name="SpeakingOrganizingEvent.findAll", query="SELECT s FROM SpeakingOrganizingEvent s")
public class SpeakingOrganizingEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int achievementId;


	private String country;

	private String session;
   
	private String sessionType ; 
	
    private String role ;	

	private String roleOfOrganizer;

	private String titleOfConference;

	private String titleOfEvent;

	private String typeOfEvent;

	//uni-directional one-to-one association to Achievement
	@OneToOne
	@JoinColumn(name="ACHIEVEMENTID", insertable=false , updatable=false)
	private Achievement achievement;

	public SpeakingOrganizingEvent() {
	}

	public int getAchievementId() {
		return this.achievementId;
	}

	public void setAchievementId(int achievementId) {
		this.achievementId = achievementId;
	}

	

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	

	public String getSessionType() {
		return this.sessionType;
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}

	

	public String getTitleOfConference() {
		return this.titleOfConference;
	}

	public void setTitleOfConference(String titleOfConference) {
		this.titleOfConference = titleOfConference;
	}

	public String getTitleOfEvent() {
		return this.titleOfEvent;
	}

	public void setTitleOfEvent(String titleOfEvent) {
		this.titleOfEvent = titleOfEvent;
	}

	public String getTypeOfEvent() {
		return this.typeOfEvent;
	}

	public void setTypeOfEvent(String typeOfEvent) {
		this.typeOfEvent = typeOfEvent;
	}

	public Achievement getAchievement() {
		return this.achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleOfOrganizer() {
		return roleOfOrganizer;
	}

	public void setRoleOfOrganizer(String roleOfOrganizer) {
		this.roleOfOrganizer = roleOfOrganizer;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

}
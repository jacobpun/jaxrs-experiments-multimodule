package org.punnoose.jersey.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * Represents an activity performed by a {@link User}
 * @author "Punnoose Pullolickal"
 * @since V1.0
 */
@Entity
@Table(name="ACTIVITIES")
public class Activity {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="DESCRIPTION", length=64)
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name="DURATION")
	private int duration;
	
	@Version
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdatedDate;	
	
	/** Default Constructor. 
	 *  The default behaviour of this object is 
	 *  <ul> 
	 *  <li>description is null</li> 
	 *  <li>duration is 0</li> 
	 *  <li>{@link User} is null</li> 
	 *  </ul> 
	 */  
	public Activity() {}
	
	/**
	 * Returns the description of this activity
	 * @return description of this activity
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of this activity
	 * @param description description of this activity
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the duration of this activity
	 * @return duration of this activity
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Sets the duration of this activity
	 * @param duration duration of this activity
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * Returns the Id of this activity
	 * @return id of this activity
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Returns the {@link User} who performed this activity
	 * @return {@link User} who performed this activity
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the {@link User} who performed this activity
	 * @param user {@link User} who performed this activity
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Returns the last update date of this activity
	 * @return last update date of this activity
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
}
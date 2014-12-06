package org.punnoose.jersey.search;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchCriteria {
	private List<String> description;
	private String userName;
	private Long userId;

	public List<String> getDescriptions() {
		return description;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
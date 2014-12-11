package org.punnoose.jersey.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "user")
@JsonRootName(value = "user")
@JsonPropertyOrder({"id"})
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserDto {
	private Long id;
	@NotNull(message="Name should be valid")
	private String name;
	private Map<String, String> extras = new HashMap<String, String>();
	
	@XmlElement(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + "]";
	}

	@JsonAnyGetter
	public Map<String, String> getExtras() {
		return extras;
	}

	@JsonAnySetter
	public void setExtra(String key, String value) {
		this.getExtras().put(key, value);
	}

	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}
}
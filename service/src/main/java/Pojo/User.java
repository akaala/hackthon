package Pojo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {
	private int userid;

	private String name;

	private String gender;

	private String birth;

	private String income;

	private Map<String, Integer> tags = new HashMap<>();

	private Map<String, Integer> favorites = new HashMap<>();

	private int bidTimes;
	
	private double bidThanOthers;

	private int bidReturnTimes;

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public int getBidTimes() {
		return bidTimes;
	}

	public void setBidTimes(int bidTimes) {
		this.bidTimes = bidTimes;
	}

	public int getBidReturnTimes() {
		return bidReturnTimes;
	}

	public void setBidReturnTimes(int bidReturnTimes) {
		this.bidReturnTimes = bidReturnTimes;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Map<String, Integer> getTags() {
		return tags;
	}

	public void setTags(Map<String, Integer> tags) {
		this.tags = tags;
	}

	public Map<String, Integer> getFavorites() {
	   return favorites;
   }

	public void setFavorites(Map<String, Integer> favorites) {
	   this.favorites = favorites;
   }

	public double getBidThanOthers() {
	   return bidThanOthers;
   }

	public void setBidThanOthers(double bidThanOthers) {
	   this.bidThanOthers = bidThanOthers;
   }
}

package Pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {
	private int userid;

	private String name;

	private String gender;

	private String birth;

	private String income;

	private int bidTimes;

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
}

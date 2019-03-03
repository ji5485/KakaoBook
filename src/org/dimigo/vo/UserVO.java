package org.dimigo.vo;

public class UserVO {	

	private String nickname;
	private int age;
	private String gender;
	private String favorite;
	
	public UserVO() {
		
	}
	
	public UserVO(String nickname, int age, String gender, String favorite) {
		this.nickname = nickname;
		this.age = age;
		this.gender = gender;
		this.favorite = favorite;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getFavorite() {
		return favorite;
	}
	
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	
	@Override
	public String toString() {
		return "UserVO [nickname=" + nickname + ", age=" + age + ", gender=" + gender + ", favorite=" + favorite + "]";
	}
}

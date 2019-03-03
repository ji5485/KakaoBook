package org.dimigo.vo;

public class ScrapVO {
	private String nickname;
	private String title;
	private String blogger;
	private String date;
	private String link;
	
	public ScrapVO() {}
	
	public ScrapVO(String nickname, String title, String blogger, String date, String link) {
		this.nickname = nickname;
		this.title = title;
		this.blogger = blogger;
		this.date = date;
		this.link = link;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBlogger() {
		return blogger;
	}
	
	public void setBlogger(String blogger) {
		this.blogger = blogger;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "ScrapVO [title=" + title + ", blogger=" + blogger + ", date=" + date + ", link=" + link + "]";
	}
	
}

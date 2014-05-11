package nca.jenkins2logitec;

import java.util.Date;

public class Project {

	private final String name;
	private final String url;
	private String message;
	private Date producedAt;
	
	public Project(String name, long id, String url) {
		super();
		this.name = name;
		this.url = url;
		this.producedAt = new Date();
	}

	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public Date getProducedAt() {
		return this.producedAt;
	}
}

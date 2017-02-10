package etu.bassem.facebook.model;

public class FBComment {

	private String id;
	private String message;
	private FBUser user;
	
	public String getMessage() {
		return message;
	}
	
	public String getId() {
		return id;
	}
	
	public FBUser getUser() {
		return user;
	}

	public FBComment(String id, String message, FBUser user) {
		super();
		this.id = id;
		this.message = message;
		this.user = user;
	}

	public Integer getWordCount() {
		String[] wordArray = message.split("\\s+");
		return wordArray.length;
	}
	
	
}

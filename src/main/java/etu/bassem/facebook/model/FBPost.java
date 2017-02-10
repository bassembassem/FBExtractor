package etu.bassem.facebook.model;

import java.util.ArrayList;
import java.util.List;

import com.restfb.types.Post;
import com.restfb.util.StringUtils;

public class FBPost {
	
	private String id;
	private String message;
	private List<FBComment> comments = new ArrayList<FBComment>();
	
	
	public String getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public List<FBComment> getComments() {
		return comments;
	}
	
	public void setComments(List<FBComment> comments) {
		this.comments = comments;
	}
	
	public FBPost(String id, String message) {
		super();
		this.id = id;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}

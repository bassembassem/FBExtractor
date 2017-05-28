package etu.bassem.facebook;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.types.Comment;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.restfb.util.StringUtils;

import etu.bassem.facebook.model.FBComment;
import etu.bassem.facebook.model.FBPost;
import etu.bassem.facebook.model.FBUser;

public class FBExtroctor {

	public static DefaultFacebookClient facebookClient;
	public static int postCountLimit = 100;
	
	public static List<FBPost> fbPosts = new ArrayList<FBPost>();
	public static Set<FBUser> fbUsers = new HashSet<FBUser>();
	
	
	public static void main(String[] args) {
		processing("https://www.facebook.com/aljazeerachannel");
	}
	
	public static void processing(String pageUrl){
		
		String MY_APP_ID = "1678270942463858";
		String MY_APP_SECRET = "858fe8e4ca9bc98ecdc2d391f32a4f1e";
		
		facebookClient = new LoggedInFacebookClient(MY_APP_ID, MY_APP_SECRET);
		
		getFacebookPosts(pageUrl);
	}
	
	public static void getFacebookPosts(String url) {
		try {

			Page page = facebookClient.fetchObject(url, Page.class);

			System.out.println(StringUtils.toString(page.getName().getBytes()));

			Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class);

			int postCount = 0;
		
			pageLoop:
		    for (List<Post> feedConnectionPage : pageFeed) {
		        for (Post post : feedConnectionPage) {
		            if (post != null) {
		            	
		                if (postCount == postCountLimit)
		                    break pageLoop;
		                
		                postCount++;
		                
		                try {
		                	
		                	JsonObject jsonObject = facebookClient.fetchObject(post.getId(), JsonObject.class);
			                String message = jsonObject.getString("message");
			                String id = jsonObject.getString("id");
			                
			                System.out.println(postCount + " => " + message);
			                System.out.println();
			                
			                FBPost fbPost = new FBPost(id, jsonObject.getString("message"));
			                
			                getAllPostComments(post.getId(), fbPost);
			                
			                fbPosts.add(fbPost);
			                
						} catch (Exception e) {
							e.printStackTrace();
						}
		                
		                
		            }
		        }
		    }
		
			
			System.out.println("Post count : " + postCount);
			
		} catch (FacebookOAuthException ex) {
			ex.printStackTrace();
		}
		
		/*
		if(true) {
			return;
		}
		*/
		
		// write file
		try {
			
			PrintWriter writer = new PrintWriter("out.csv", "UTF-8");
			
			writer.println("id;name");
			
			for (FBPost post : fbPosts) {
				writer.println(post.getId() + ";" + post.getMessage());
				
				for (FBComment comment : post.getComments()) {
					writer.println(comment.getId() + ";" + comment.getMessage());
				}
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		// write file
		try {
			
			PrintWriter writer = new PrintWriter("users.csv", "UTF-8");
			
			writer.println("id;name;nbWords;age;gender");
			
			for (FBUser user : fbUsers) {
				writer.println(user.getId() + ";" + user.getName() + ";" + user.getNbWords() + ";;;");
				
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			byte[] utf8JsonString = new Gson().toJson(fbPosts).getBytes("UTF8");
			
			FileOutputStream fos = new FileOutputStream("out.json");
			fos.write(utf8JsonString, 0, utf8JsonString.length);
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	public static void getAllPostComments(String postId, FBPost fbPost) {
		
		List<FBComment> fbComments = new ArrayList<FBComment>();
		
		int currentCount = 0;
		
		JsonObject jsonObject = facebookClient.fetchObject(postId + "/comments", JsonObject.class, Parameter.with("summary", true), Parameter.with("limit", 1));
		long commentsTotalCount = jsonObject.getJsonObject("summary").getLong("total_count");

		System.out.println("\nComments:");
        
		boolean pom = true;

		try {
			
			Connection<Comment> comments = facebookClient.fetchConnection(postId + "/comments", Comment.class, Parameter.with("limit", 50000), Parameter.with("offset", currentCount));

			for (Comment comment : comments.getData()) {
				
				pom = true;
				
				currentCount++;
				
				String mess = comment.getMessage().replaceAll("\n", " ").replaceAll("\r", " ");
				
				if (!mess.trim().isEmpty()) {
					
					FBUser fbUser = new FBUser(comment.getFrom().getId(), comment.getFrom().getName(), "?", "?");
					fbComments.add(new FBComment(comment.getId(), mess, fbUser));
					
					if(fbUsers.contains(fbUser)) {
						fbUser.addNbWords(comment.getMessage().split(" ").length);
					} else {
						fbUser.setNbWords(comment.getMessage().split(" ").length);
						fbUsers.add(fbUser);
					}
					
				}
				
				
				System.out.println("    [" + currentCount + "]: " + comment.getFrom().getName() + " ## " + mess);
				
				getUserInfo(comment.getFrom().getId());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fbPost.setComments(fbComments);
		
		System.out.println(currentCount + " / " + commentsTotalCount);
	}
	
	private static void getUserInfo(String userId) {
		
		JsonObject jsonObject = facebookClient.fetchObject(userId, JsonObject.class, Parameter.with("fields", "name,first_name,last_name,gender,age_range,birthday"));
		System.out.println(jsonObject);

		if (!jsonObject.isNull("birthday")) {
			System.out.println(jsonObject.getString("birthday"));
			throw new RuntimeException();
		}

		User user = facebookClient.fetchObject(userId, User.class, Parameter.with("fields", "name,email,first_name,last_name,gender,age_range,birthday"));
		System.out.println("getName : " + user.getName());
		System.out.println("getBirthday : " + user.getBirthday());
		System.out.println("getAgeRange : " + user.getAgeRange());
		System.out.println("getGender : " + user.getGender());
	}

	protected static PrintStream outputFile(String name) throws FileNotFoundException {
		return new PrintStream(new BufferedOutputStream(new FileOutputStream(name)));
	}
}

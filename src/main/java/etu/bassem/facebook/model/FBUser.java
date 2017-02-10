package etu.bassem.facebook.model;

public class FBUser {

	private String id;
	private String name;
	private String gender;
	private String age;
	private int nbWords;
	
	public FBUser(String id, String name, String gender, String age) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}
	
	public String getAge() {
		return age;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}
	
	public int getNbWords() {
		return nbWords;
	}
	
	public void setNbWords(int nbWords) {
		this.nbWords = nbWords;
	}
	
	public void addNbWords(int nbWords) {
		this.nbWords += nbWords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FBUser other = (FBUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

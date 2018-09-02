package client.logic.entities;

public class Contact {
	
	private String name;
	private int hash=0;
	
	public Contact(String name){
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof Contact){
			Contact contact = (Contact) obj;
			return name.equals(contact.getName());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		if (hash == 0){
			int result = 21;
			result = 31*result+name.hashCode();
			hash=result;
		}
		return hash;
	}
	
	@Override
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}
	
	

}

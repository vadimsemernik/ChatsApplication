package client.logic.entities;

public class Message {
	
	private Contact author;
	private String content;
	
	public Message(Contact author, String content){
		this.author=author;
		this.content=content;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof Message){
			Message message = (Message) obj;
			return author.equals(message.getAuthor())&&content.equals(message.getContent());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int result = 21;
		result = 31*result+author.hashCode();
		result = 31*result+content.hashCode();
		return result;
	}

	public Contact getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}
	
	

}

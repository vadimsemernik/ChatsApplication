package server.logic.entities;

public class ServerMessage {
	
	private String author;
	private String content;
	private int number;

	public ServerMessage(String author, String content) {
		this.author = author;
		this.content=content;
	}
	
	public ServerMessage( int number, String author, String content) {
		this.author = author;
		this.content=content;
		this.number=number;
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	public int getNumber() {
		return number;
	}
	
	
	
	

}

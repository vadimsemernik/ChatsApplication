package server.logic.entities;

public class TalkID {
	
	private int id;
	private int number;
	
	public TalkID (int number, int id) {
		this.id=id;
		this.number=number;
	}
	
	public TalkID (){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}

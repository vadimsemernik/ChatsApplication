package server.logic.entities;

public class TalkParticipant {
	
	private String name;
	private int number;
	private int hash = 0;
	
	public TalkParticipant(int number, String name){
		this.name=name;
		this.number=number;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof TalkParticipant){
			TalkParticipant participant = (TalkParticipant) obj;
			return participant.name.equals(this.name)&&this.number==participant.number;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		if (hash == 0){
			int result = 21;
			result = 31*result+name.hashCode()+ number;
			hash=result;
		}
		return hash;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	

}

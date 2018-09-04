package server.protocol;

public class Protocol {
	
	public static final String SERVICE_HEADER = "Service";
	public static final String INFO_HEADER = "Logic";
	public static final char END = '\r';
	
	public static enum Message {
		
		Ready ("Ready"),
		OK ("OK"),
		End ("End"),
		WrongName("Wrong name"),
		WrongPassword("Wrong password");
		
		private String content;
		
		@Override
		public String toString(){
			return content;
		}
		
		Message (String content){
			this.content = content;
		}
	}
	
	
	
	public static enum Size {
		
		Header("1"),
		Talk("5"),
		Contact("2"),
		Message("4"),
		TalkDescription("4");
		;
		
		private String content;
		
		@Override
		public String toString(){
			return content;
		}
		
		Size (String content){
			this.content = content;
		}
		
	}
	
	
	public static enum LogicHeader {
		Login("Login"),
		Name ("Name"),
		Password("Password"),
		Profile("Profile"),
		Talk("Talk"),
		Message("Message"),
		Participant("Participant"),
		Contact("Contact"),
		ContactsUpdate("Contacts update"),
		TalkUpdate("Talk update"),
		ParticipantsCount("Participants count"),
		MessagesCount("Messages Count")
		;
		
		private String content;
		
		@Override
		public String toString(){
			return content;
		}
		
		LogicHeader (String content){
			this.content = content;
		}
	}
	
	
	public static enum Delimiter {
		Outer ("\n\n"),
		Inner ("\n"),
		Message(":");
		
		private String content;
		
		
		@Override
		public String toString(){
			return content;
		}
		
		Delimiter (String content){
			this.content=content;
		}
		
	}
	
	public static enum Login{
		InvalidPassword("Password is not correct"),
		InvalidLogin("There is no such user"),
		NotFound ("Not found"),
		Found("Found"),
		Load("Load"),
		Update("Update")
		;
		private String content;
		
		
		@Override
		public String toString(){
			return content;
		}
		
		Login (String content){
			this.content=content;
		}
	}
	
	
	
}

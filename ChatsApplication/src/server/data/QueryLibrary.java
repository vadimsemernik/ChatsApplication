package server.data;

public class QueryLibrary {
	
	public static enum Profiles {
		
		Table("Profiles"),
		ClientName("ClientName"),
		Password("Password"),
		ContactsTable("ContactsTable"),
		ContactsCount("ContactsCount"),
		TalksIDTable("TalksIDTable"),
		TalksIDCount("TalksIDCount"),
		TalkID("TalkID"),
		Number("Number"),
		Contact("Contact"),
		
		;
		private String content;
		
		@Override
		public String toString(){
			return content;
		}
		
		 Profiles (String content){
			this.content = content;
		}
	}
	
	public static enum Talks {
		
		Table("Talks"),
		TalkID("TalkID"),
		TalkTitle("TalkTitle"),
		Number("Number"),
		ParticipantsTable("ParticipantsTable"),
		MessagesTable("MessagesTable"),
		ParticipantsCount("ParticipantsCount"),
		MessagesCount("MessagesCount"),
		Author("Author"),
		Content("Content"),
		Participant("Participant")
		;
		private String content;
		
		@Override
		public String toString(){
			return content;
		}
		
		 Talks (String content){
			this.content = content;
		}
	}

	public static enum Select {
		
		Select("Select"),
		From("From"),
		Asterisk("*"),
		
		
		;
		
		private String content;
		
		@Override
		public String toString(){
			return content;
		}
		
		 Select (String content){
			this.content = content;
		}
	}
	
	public static enum Change {
			
			Insert("Insert into"),
			Create("Create"),
			Values("Values"),
			Table("Table")
			
			
			;
			
			private String content;
			
			@Override
			public String toString(){
				return content;
			}
			
			Change (String content){
				this.content = content;
			}
		}

}

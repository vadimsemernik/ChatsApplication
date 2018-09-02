package database;

import java.util.ArrayList;
import java.util.List;

public class TableDescription {
	
	private String title;
	private String primaryKey;
	List <Column> columns;
	
	public TableDescription(String title){
		this.title=title;
		columns=new ArrayList();
	}
	
	public void addColumn(String title, String type){
		columns.add(new Column(title, type,false));
	}
	
	public void addColumn(String title, String type, boolean notNull){
		columns.add(new Column(title, type,notNull));
	}
	
	public boolean setPrimaryKey(String candidate){
		for (Column column : columns){
			if (column.title.equals(candidate) && column.notNull==true){
				primaryKey=candidate;
				return true;
			}
		}
		return false;
	}
	
	
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateQuery(){
		StringBuilder builder = new StringBuilder();
		builder.append("Create table ");
		builder.append(title);
		builder.append(columnsDescription());
		builder.append(';');
		return builder.toString();
	}
	
	
	private String columnsDescription() {
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		for (Column column : columns){
			builder.append(column.title +" ");
			builder.append(column.type +" ");
			if (column.notNull){
				builder.append("NOT NULL");
			}
			builder.append(',');
		}
		builder.append("PRIMARY KEY ("+primaryKey+")");
		builder.append(')');
		return builder.toString();
	}


	private class Column {
	
		
		private String title;
		private String type;
		private boolean notNull;
		
		public Column(String title, String type, boolean notNull) {
			this.title=title;
			this.type=type;
			this.notNull=notNull;
		}
	}

}

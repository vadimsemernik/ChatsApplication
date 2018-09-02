package client.gui.utils.formatters;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;

public class Formatter {
	
	public static Formatter getFormatter(LayoutManager layout){
		if (layout instanceof CardLayout){
			return new CardLayoutFormatter();
		} else if (layout instanceof BorderLayout){
			return new BorderLayoutFormatter();
		} else{
			return new GridBagLayoutFormatter();
		}
	}
	
	Formatter(){}
	
	
	public GridBagConstraints getConstraints(int gridx, int gridy) {
		return getConstraints(gridx,gridy,1,1);
	}
	
	public GridBagConstraints getConstraints(int gridx, int gridy,  int gridwidth, int gridheight) {
		return getConstraints(gridx, gridy, gridwidth, gridheight, 10, 10);
	}
	
	public GridBagConstraints getConstraints(int gridx, int gridy, int gridwidth, int gridheight, int cellWidth, int cellHeight) {
		GridBagConstraints con = new GridBagConstraints();
		con.gridy = gridy;
		con.gridx = gridx;
		con.gridheight=gridheight;
		con.gridwidth=gridwidth;
		con.ipadx=cellWidth;
		con.ipady=cellHeight;
		con.fill=GridBagConstraints.BOTH;
		con.insets = new Insets(5,5,5,5);
		return con;
	}

}

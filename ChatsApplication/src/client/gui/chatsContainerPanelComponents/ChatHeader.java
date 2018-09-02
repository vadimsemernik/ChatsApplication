package client.gui.chatsContainerPanelComponents;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

import client.gui.AppPanel;
import client.gui.utils.formatters.Formatter;

public class ChatHeader extends JPanel implements AppPanel{
	
	private String title;
	private JLabel label;
	private Formatter form;
	
	public ChatHeader (String title) {
		this.title=title;
		initLayout(new GridBagLayout());
		addComponents();
	}
	
	@Override
	public void initLayout(LayoutManager layout) {
		this.setLayout(layout);
		form = Formatter.getFormatter(layout);
		
	}

	@Override
	public void addComponents() {
		label=new JLabel(title);
		this.add(label, form.getConstraints(0, 0));
		
	}


	String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}


	
	

}

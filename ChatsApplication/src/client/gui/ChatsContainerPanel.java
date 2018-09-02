package client.gui;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import client.gui.chatsContainerPanelComponents.ChatsPanel;
import client.gui.utils.formatters.Formatter;


public class ChatsContainerPanel extends JPanel implements AppPanel{
	
	
	private ChatsPanel chatsPanel;
	
	private Formatter form;
	
	ChatsContainerPanel () {
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
		initChatsPanel();
		
	}


	private void initChatsPanel() {
		chatsPanel = new ChatsPanel ();
		this.add(chatsPanel);
		
	}


	public ChatsPanel getChatsPanel() {
		return chatsPanel;
	}

	
	
	

}

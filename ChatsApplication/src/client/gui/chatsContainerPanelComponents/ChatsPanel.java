package client.gui.chatsContainerPanelComponents;

import java.awt.CardLayout;

import javax.swing.JPanel;

import client.gui.AppPanel;
import client.logic.entities.ClientTalk;
import client.logic.handlers.UserMessageReceiver;

public class ChatsPanel extends JPanel {
	
	public ChatsPanel(){
		this.setLayout(new CardLayout());
	}
	
	public void addChat (ClientTalk talk){
		Chat chat = new Chat(talk);
		this.add(String.valueOf(chat.getId()), chat);
	}


}

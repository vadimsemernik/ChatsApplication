package client.gui.navigationPanelComponents;

import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import client.gui.chatsContainerPanelComponents.Chat;
import client.gui.chatsContainerPanelComponents.ChatsPanel;
import client.logic.entities.ClientTalk;

public class ChatSwitch extends JButton {
	
	private ClientTalk conversation;
	private ChatsPanel chats;
	
	public ChatSwitch (ClientTalk talk, ChatsPanel chats){
		super(talk.getTitle());
		this.conversation = talk;
		this.chats = chats;
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CardLayout card = (CardLayout) chats.getLayout();
				card.show(chats, String.valueOf(talk.getId()));
			}
		});
	}

}

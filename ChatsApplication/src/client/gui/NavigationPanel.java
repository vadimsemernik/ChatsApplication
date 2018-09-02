package client.gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.gui.chatsContainerPanelComponents.ChatsPanel;
import client.gui.navigationPanelComponents.ChatSwitch;
import client.gui.settings.ComponentSizes;
import client.gui.settings.ViewInfo;
import client.gui.utils.formatters.Formatter;
import client.logic.ClientLogic;
import client.logic.entities.Contact;
import client.logic.entities.ClientTalk;
import client.logic.handlers.UserMessageReceiver;

public class NavigationPanel extends JPanel implements AppPanel{
	
	private Formatter form;
	private JButton contactsButton;
	private JButton conversationsButton;
	private JPanel paneWithScroll;				// panel, which contains scrollPanes
	private JScrollPane contactsScroll;
	private JScrollPane conversationsScroll;
	private String contactsTitle = ViewInfo.CONTACTS_TITLE;
	private String conversationsTitle = ViewInfo.CONVERSATIONS_TITLE;
	
	private ChatsPanel chatsPane;
	private Set <Contact> contacts;
	private Collection <ClientTalk> conversations;
	
	NavigationPanel (Set <Contact> contacts, Collection <ClientTalk> conversations, ChatsPanel chats) {
		this.contacts = contacts;
		this.conversations = conversations;
		this.chatsPane = chats;
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
		this.add(initContactsButton(), form.getConstraints(0, 0));
		this.add(initConversationsButton(), form.getConstraints(1, 0));
		initContactsScroll();
		initConversationsScroll();
		initScrollPane();
	}



	private JButton initConversationsButton() {
		conversationsButton = new JButton(conversationsTitle);
		conversationsButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout) paneWithScroll.getLayout();
				card.show(paneWithScroll, conversationsTitle);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return conversationsButton;
	}

	private JButton initContactsButton() {
		contactsButton = new JButton(contactsTitle);
		contactsButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout card = (CardLayout) paneWithScroll.getLayout();
				card.show(paneWithScroll, contactsTitle);
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return contactsButton;
	}

	private void initConversationsScroll() {
		JPanel pane = new JPanel (new GridBagLayout());
		conversationsScroll = new JScrollPane(pane);
		if (conversations!=null){
			addConversations(pane);
		} else {
			addEmptyConversation();
			
		}
		
	}

	private void addConversations(JPanel place) {
		Iterator <ClientTalk> iterator = conversations.iterator();
		ClientTalk chat;
		int i = 0;
		while(iterator.hasNext()){
			chat = iterator.next();
			chatsPane.addChat(chat);						// add new chat to chats
			place.add(new ChatSwitch(chat,chatsPane), form.getConstraints(0,i));	// add switcher to the new chat	
			i++;
		}
	}

	private void initContactsScroll() {
		JPanel pane = new JPanel (new GridBagLayout());
		contactsScroll = new JScrollPane(pane);
		if (contacts != null){
			Iterator <Contact> iterator = contacts.iterator();
			Contact contact;
			int i=0;
			while(iterator.hasNext()){
				contact = iterator.next();
				pane.add(new JButton(contact.getName()), form.getConstraints(0,i));
				i++;
			}
		}
		
	}
	
	private void addEmptyConversation() {
		chatsPane.addChat(null);
	}

	private void initScrollPane() {
		paneWithScroll = new JPanel(new CardLayout());
		GridBagConstraints con = form.getConstraints(0, 1, 2, 0);
		con.ipadx=ComponentSizes.leftPanelWidth;
		con.ipady=ComponentSizes.leftPanelHeight;
		paneWithScroll.add(contactsTitle, contactsScroll);
		paneWithScroll.add(conversationsTitle, conversationsScroll);
		this.add(paneWithScroll, con);
		
	}

	


}

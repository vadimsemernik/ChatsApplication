package client.gui.chatsContainerPanelComponents;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.Iterator;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.gui.AppPanel;
import client.gui.utils.formatters.Formatter;
import client.logic.entities.Message;

public class ChatMessages extends JPanel implements AppPanel{
	
	private Formatter form;
	private Queue <Message> messages;
	private JPanel messagesPane;
	private int currentPosition=0;
	
	

	public ChatMessages(Queue<Message> messages) {
		this.messages=messages;
		initLayout(new BorderLayout());
		addComponents();
		
	}
	
	@Override
	public void initLayout(LayoutManager layout) {
		this.setLayout(layout);
		form = Formatter.getFormatter(layout);
		
	}

	@Override
	public void addComponents() {
		initMessagesPane();
		displayMessages();
		initScroll();
		
	}


	private void initMessagesPane() {
		messagesPane = new JPanel(new GridBagLayout());
		
	}



	private void displayMessages() {
		if (messages != null){
			Iterator <Message> iterator = messages.iterator();
			while(iterator.hasNext()){
				MessageView view = new MessageView (iterator.next());
				showNextMessage(view);
			}
		}
		
	}
	
	public void addMessage(Message message) {
		if (message != null){
			MessageView view = new MessageView (message);
			showNextMessage(view);
			this.repaint();
		}
		
	}



	private void showNextMessage(MessageView view) {
		if (messagesPane == null) System.out.println("Message pane is null");
		messagesPane.add(view, form.getConstraints(0, currentPosition));
		currentPosition++;
	}



	private void initScroll() {
		JScrollPane scroll = new JScrollPane(messagesPane);
		this.add(scroll, BorderLayout.CENTER);
	}	

}

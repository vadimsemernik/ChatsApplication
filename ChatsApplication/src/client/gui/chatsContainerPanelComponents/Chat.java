package client.gui.chatsContainerPanelComponents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import client.gui.AppPanel;
import client.gui.utils.formatters.Formatter;
import client.logic.TalkAssistant;
import client.logic.entities.Message;
import client.logic.entities.ClientTalk;
import client.logic.handlers.UserMessageReceiver;

public class Chat extends JPanel implements AppPanel, TalkView {
	
	private ChatHeader header;
	private ClientTalk talk;
	private ChatMessages messages;
	
	private Formatter form;

	public Chat(ClientTalk talk) {
		if (talk == null){
			talk=TalkAssistant.getEmptyConversation();
		}
		this.talk = talk;
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
		initHeader();
		displayMessages();
		initMessageSender();
		talk.bindView(this);		// bind a talk presentation to the talk
		
	}

	private void initMessageSender() {
		ChatMessageSender sender = new ChatMessageSender(this);
		GridBagConstraints con = form.getConstraints(0, 2);
		this.add(sender, con);
		
		
	}

	private void displayMessages() {
		messages = new ChatMessages (talk.getMessages());
		GridBagConstraints con = form.getConstraints(0,1,1,1, 600, 400);
		this.add(messages, con);
		
	}

	private void initHeader() {
		header = new ChatHeader (talk.getTitle());
		GridBagConstraints con = form.getConstraints(0, 0);
		this.add(header, con);
	}

	
	public ClientTalk getTalk() {
		return talk;
	}
	
	

	@Override
	public void addMessage(Message message) {
		messages.addMessage(message);
		
	}

	public int getId() {
		return talk.getId();
	}

	@Override
	public void repaintView() {
		this.revalidate();
		
	}

	
	

}

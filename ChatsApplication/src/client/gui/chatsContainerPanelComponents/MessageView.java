package client.gui.chatsContainerPanelComponents;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import client.logic.entities.Message;

public class MessageView  extends JPanel {
	
	private Message message;


	public MessageView(Message message) {
		this.message=message;
		this.setLayout(new GridBagLayout());
		this.add(getAuthor());
		GridBagConstraints con = new GridBagConstraints();
		con.gridy=1;
		this.add(getText(), con);
	}

	

	private Component getAuthor() {
		JLabel author = new JLabel(message.getAuthor().getName());
		return author;
	}

	private Component getText() {
		JLabel text = new JLabel(message.getContent());
		return text;
	}

}

package client.gui.chatsContainerPanelComponents;

import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.gui.utils.formatters.Formatter;
import client.logic.entities.ClientTalk;
import client.logic.handlers.UserMessageReceiver;
import client.logic.handlers.ReceiversFactory;

public class ChatMessageSender extends JPanel {
	
	private JTextField input;
	private JButton sendButton;
	private Formatter form;
	private UserMessageReceiver receiver;
	private Chat chat;
	
	public ChatMessageSender (Chat chat) {
		this.chat=chat;
		receiver = ReceiversFactory.getMessageReceiver(chat.getTalk());
		initLayout();
		initInputField();
		initSendButton();
		this.add(input, form.getConstraints(0, 0, 1, 1, 500, 40));
		this.add(sendButton);
	}

	private void initLayout() {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		form = Formatter.getFormatter(layout);
		
	}

	private void initSendButton() {
		sendButton = new JButton ("Send");
		sendButton.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (input.getText().equals("")) return;
				receiver.receive(input.getText());
				input.setText("");
				chat.revalidate();
			}
		});
		
	}

	private void initInputField() {
		input = new JTextField();
		input.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					if (input.getText().equals("")) return;
					//send user message to the handler
					receiver.receive(input.getText());
					input.setText("");
					chat.revalidate();
				}	
			}

		});	
	}
	
	void setReceiver (UserMessageReceiver receiver){
		this.receiver=receiver;
	}
	
}

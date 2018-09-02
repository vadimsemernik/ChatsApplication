package client.login;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.logic.handlers.AccountHandler;

public class NewAccountWindow extends JFrame {

	private JPanel mainContentPane;
	private JLabel login = new JLabel("Enter a login");
	private JTextField loginField = new JTextField();
	private JLabel password = new JLabel("Enter a password (letters, digits and undescore, "
			+ "at least 6 symbols");
	private JPasswordField passField = new JPasswordField();
	private JLabel confirm = new JLabel("Confirm the password");
	private JPasswordField confirmField = new JPasswordField();
	private String checkTitle = "Check";
	private JButton check;
	private boolean checked=false;
	private String sendTitle = "Send";
	private JButton send;
	private int height = 0;
	
	private AccountHandler handler = new AccountHandler();
	

	public NewAccountWindow(String accountTitle) {
		this.setTitle(accountTitle);
		initFrame();
		initContentPane();
		initLayout();
		addComponents();
		this.pack();
		setVisible(true);
	}
	
	private void initFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 480);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		initContentPane();
		initLayout();
		
	}
	
	private void initContentPane() {
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5,5,5,5));
		this.setContentPane(mainContentPane);
		mainContentPane.setBackground(Color.CYAN);
		
	}
	
	private void initLayout(){
		GridBagLayout contentPaneLayout = new GridBagLayout();
		mainContentPane.setLayout(contentPaneLayout);
	}
	
	
	private void addComponents() {
		addComponent(login, 20,10);
		addComponent(loginField,200,10);
		addComponent(password, 20,10);
		addComponent(passField, 200,10);
		addComponent(confirm, 20,10);
		addComponent(confirmField, 200,10);
		initChecker();
		addComponent(check, 20,10);
		initSender();
		addComponent(send, 20,10);
	}

	
	private void addComponent(JComponent component, int ipadx, int ipady) {
		GridBagConstraints con = new GridBagConstraints();
		con.gridy=height;
		con.ipadx=ipadx;
		con.ipady=ipady;
		mainContentPane.add(component, con);
		height++;
		
	}


	private void initChecker() {
		check = new JButton(checkTitle);
		check.addMouseListener(new MouseListener() {
			
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
			
			@Override
			public void mouseClicked(MouseEvent e) {
				handler.checkLogin(loginField.getText());
				handler.checkPassword(passField.getPassword());
				handler.checkConfirmation(passField.getPassword(), confirmField.getPassword());
				
			}
		});
		
	}


	private void initSender() {
		send = new JButton ("Send");
		
	}


	
	
	public static void main (String [] args){
		NewAccountWindow account = new NewAccountWindow("AccountTest");
	}

}

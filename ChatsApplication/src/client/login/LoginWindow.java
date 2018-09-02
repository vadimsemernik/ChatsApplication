package client.login;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.logic.handlers.LoginHandler;
import client.net.client_side.connections.InitialServerConnection;


public class LoginWindow  extends JFrame{
	
	
	private JPanel mainContentPane;
	private JLabel login = new JLabel("Enter a login");
	private JTextField loginField = new JTextField();
	private JLabel password = new JLabel("Enter a password");
	private JPasswordField passField = new JPasswordField();
	private JButton sendButton = new SendButton("Send", this);
	private String accountIsNotFound = "There is no such account";
	private String invalidPassword = "Password does not match the login";
	private int height=0;
	
	private InitialServerConnection init;
	private int port=2018;
	
	
	

	public LoginWindow (String title) {
		this.setTitle(title);
		initFrame();
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
		addComponent(login,20,10);
		addComponent(loginField, 200,10);
		addComponent(password, 20,10);
		addComponent(passField,200,10);
		addComponent(sendButton, 20,10);
	}
	
	private void addComponent(JComponent component, int ipadx, int ipady) {
		GridBagConstraints con = new GridBagConstraints();
		con.gridy=height;
		con.ipadx=ipadx;
		con.ipady=ipady;
		mainContentPane.add(component, con);
		height++;
		
	}
	
	private void addComponent(JComponent component, GridBagConstraints con, int ipadx, int ipady) {
		con.ipadx=ipadx;
		con.ipady=ipady;
		mainContentPane.add(component, con);
		con.gridy++;
		
	}

	public void accountIsNotFound() {
		JLabel warning = new JLabel(accountIsNotFound);
		addComponent(warning, 150, 20);
		
	}
	
	public void invalidPassword() {
		JLabel warning = new JLabel(accountIsNotFound);
		addComponent(warning, 150, 20);
		
	}

	
	
	public static void main (String [] args){
		LoginWindow login = new LoginWindow("Login Window");
	}
	
	
	private class SendButton extends JButton{
		
		private LoginHandler handler;
		private String loginWarning = "Login must contain only letters and numbers";
		private String passWarning = "Password must  contain at least 6 symbols with letters and digits";

		public SendButton(String title, LoginWindow loginWindow) {
			super(title);
			handler = new LoginHandler(loginWindow);
			this.addMouseListener(new MouseListener() {
				
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
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String loginText = loginField.getText();
					char [] passChars = passField.getPassword();
					if (checkCorrectness(loginText, passChars)){
						handler.handle(loginText, new String(passChars));
					}
					
				}

				private boolean checkCorrectness(String loginText, char[] passChars) {
					boolean result=false;
					if (loginText.equals("") || !handler.loginIsValid(loginText)){
						login.setText(loginWarning);
						login.revalidate();
					} else if(!handler.passwordIsValid(passChars)) {
						password.setText(passWarning);
						password.revalidate();
					} else{
						result = true;
					}
					return result;
				}
			});
		}
		
		
		
	}


}

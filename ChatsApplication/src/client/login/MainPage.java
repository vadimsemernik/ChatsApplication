package client.login;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainPage  extends JFrame{
	
	private String title = "Application";
	private JPanel mainContentPane;
	private JButton login;
	private JButton account;
	
	private String loginTitle = "Login";
	private String accountTitle = "New Account";
	
	public MainPage () {
		this.setTitle(title);
		initFrame();
		addComponents();
		//this.pack();
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
		GridBagConstraints con = new GridBagConstraints();
		initLoginButton();
		con.ipadx = 100;
		con.ipady = 20;
		mainContentPane.add(login, con);
		initAccountButton();
		mainContentPane.add(account, con);
	}

	private void initLoginButton() {
		login = new JButton(loginTitle);
		login.addMouseListener(new MouseListener() {
			
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
				LoginWindow login = new LoginWindow(loginTitle);
				closeMainPage();
				
			}
		});
		
	}

	

	private void initAccountButton() {
		account = new JButton(accountTitle);
		account.addMouseListener(new MouseListener() {
			
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
				NewAccountWindow newAccount = new NewAccountWindow(accountTitle);
				closeMainPage();
				
			}
		});
		
	}

	

	public static void main(String[] args) {
		MainPage page = new MainPage();

	}
	
	protected void closeMainPage() {
		this.dispose();
		
	}

}

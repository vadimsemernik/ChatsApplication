package client.gui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.gui.utils.formatters.Formatter;
import client.login.MainPage;

public class SettingsPanel extends JPanel implements AppPanel{
	
	private Formatter form;
	private JButton logout = new JButton("Log out");
	private JLabel connectionLost = new JLabel("Connection lost");
	
	
	SettingsPanel () {
		initLayout(new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);
		addComponents();
		
	}

	@Override
	public void initLayout(LayoutManager layout) {
		this.setLayout(layout);
		form = Formatter.getFormatter(layout);
		
	}

	@Override
	public void addComponents() {
		this.add(new JButton("Settings"));
		this.add(new JButton("Account"));
		initLogoutButton();
		this.add(logout);
		
	}
	
	public void showLostConnectionButton(){
		this.add(connectionLost);
		revalidate();
	}
	
	public void hideLostConnectionButton(){
		this.remove(connectionLost);
		revalidate();
	}

	private void initLogoutButton() {
		logout.addMouseListener(new MouseListener() {
			
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
				MainPage page = new MainPage();
				
				
			}
		});
		
	}

}

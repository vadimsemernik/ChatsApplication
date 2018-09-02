package client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import client.ClientProfile;
import client.gui.settings.ComponentSizes;
import client.logic.ClientLogic;
import client.logic.entities.Contact;

public class WindowImp extends JFrame implements AppPanel, ClientWindow{

	private JPanel mainContentPane;
	private ClientProfile profile;
	private SettingsPanel upper;
	private NavigationPanel left;
	private ChatsContainerPanel right;
	
	public static ClientWindow getClientWindow(ClientProfile profile){
		return new WindowImp(profile);
	}
	
	
	public WindowImp(ClientProfile profile) {
		if (profile == null){
			throw new AssertionError("Profile is null", null);
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.profile=profile;
		initFrame();
		initContentPane();
		initLayout(new GridBagLayout());
		addComponents();
		this.pack();
		setVisible(true);
	}
	
	

	private void initFrame() {
		this.setTitle("Client Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(ComponentSizes.windowWidth, ComponentSizes.windowHeight);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		
	}
	
	private void initContentPane() {
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(ComponentSizes.mainBorder,ComponentSizes.mainBorder,
				ComponentSizes.mainBorder,ComponentSizes.mainBorder));
		this.setContentPane(mainContentPane);
		mainContentPane.setBackground(Color.CYAN);
		
	}
	
	@Override
	public void initLayout(LayoutManager layout){
		mainContentPane.setLayout(layout);
	}
	
	@Override
	public void addComponents(){
		initUpperPanel();
		initRightPanel();
		initLeftPanel();
		
	}
	
	private void initUpperPanel() {
		GridBagConstraints con = new GridBagConstraints();
		con.gridx=0;
		con.gridy=0;
		con.gridwidth=2;
		con.gridheight=1;
		con.fill = GridBagConstraints.BOTH;
		con.insets = new Insets(2,3,5,3);
		upper = new SettingsPanel();
		mainContentPane.add(upper, con);
		
	}
	
	private void initLeftPanel() {
		if (right==null || right.getChatsPanel()==null){
			throw new AssertionError("Chats panel is not initialized yet", null);
		}
		GridBagConstraints con = new GridBagConstraints();
		con.gridx=0;
		con.gridy=1;
		con.gridwidth=1;
		con.gridheight=1;
		con.ipadx=100;
		con.fill = GridBagConstraints.BOTH;
		con.insets = new Insets(2,3,5,3);
		left = new NavigationPanel(profile.getContacts(),profile.getTalks(), right.getChatsPanel());
		left.setBorder(BorderFactory.createEtchedBorder());
		mainContentPane.add(left, con);
		
	}
	

	private void initRightPanel() {
		GridBagConstraints con = new GridBagConstraints();
		con.gridx=1;
		con.gridy=1;
		con.gridwidth=1;
		con.gridheight=1;
		con.ipadx=200;
		con.ipady=300;
		con.fill = GridBagConstraints.BOTH;
		con.insets = new Insets(2,3,5,3);
		right = new ChatsContainerPanel();
		right.setBorder(BorderFactory.createEmptyBorder());
		mainContentPane.add(right, con);
		
		
	}



	@Override
	public void lostConnection() {
		upper.showLostConnectionButton();
		
	}



	@Override
	public void resumeConnection() {
		upper.hideLostConnectionButton();
		
	}

}

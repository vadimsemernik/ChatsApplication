package client.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import client.ClientProfile;
import client.logic.ClientLogic;
import client.logic.entities.Contact;

public class ClientWindowExample extends JFrame {
	private JPanel mainContentPane;
	private ClientProfile profile;
	private ClientLogic logic;
	
	
	public ClientWindowExample(ClientProfile profile) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.profile=profile;
		initFrame();
		initContentPane();
		initLayout();
		addComponents();
		setVisible(true);
	}

	private void initFrame() {
		this.setTitle("Client Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1048, 740);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		
	}
	
	private void initContentPane() {
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5,5,5,5));
		this.setContentPane(mainContentPane);
		
	}
	
	private void initLayout(){
		GridBagLayout contentPaneLayout = new GridBagLayout();
		mainContentPane.setLayout(contentPaneLayout);
	}
	
	private void addComponents(){
		mainContentPane.add(new JButton("0,0, 4 columns, 1 rows"), getConstraints(0, 0, 4, 1));
		mainContentPane.add(new JButton("Conversations"), getConstraints(0, 1));
		mainContentPane.add(new JButton("Contacts"), getConstraints(1, 1));
		mainContentPane.add(new JButton("Something else 1"), getConstraints(2, 1));
		mainContentPane.add(new JButton("Something else 2"), getConstraints(3, 1));
		
		Scroll scrollPane = initScroll(200, 500);
		Set <Contact> contacts = profile.getContacts();
		if (contacts != null){
			Iterator <Contact> iterator = contacts.iterator();
			Contact contact;
			int i=0;
			while(iterator.hasNext()){
				contact = iterator.next();
				scrollPane.add(new JButton(contact.getName()), getConstraints(0,i,1,1,200,10));
				i++;
			}
		}
		scrollPane.putScrollToContentPane(mainContentPane, 0, 4, 2, 2);
		scrollPane = initScroll(600,400);
		JTextArea chat = new JTextArea();
		chat.setEditable(false);
		scrollPane.add(chat, getConstraints(0,0,2,1,650,400));
		scrollPane.putScrollToContentPane(mainContentPane, 2, 4, 1, 1);
		mainContentPane.add(new JTextField("Your message",10), getConstraints(2, 5, 1,1,400, 20));
		mainContentPane.add(new JButton("Send"), getConstraints(3, 5, 1, 1));
		 
	}
	
	
	


	private GridBagConstraints getConstraints(int gridx, int gridy) {
		return getConstraints(gridx,gridy,1,1);
	}
	
	private GridBagConstraints getConstraints(int gridx, int gridy,  int gridwidth, int gridheight) {
		return getConstraints(gridx, gridy, gridwidth, gridheight, 10, 10);
	}
	
	private GridBagConstraints getConstraints(int gridx, int gridy, int gridwidth, int gridheight, int cellWidth, int cellHeight) {
		GridBagConstraints con = new GridBagConstraints();
		con.gridy = gridy;
		con.gridx = gridx;
		con.gridheight=gridheight;
		con.gridwidth=gridwidth;
		con.ipadx=cellWidth;
		con.ipady=cellHeight;
		con.fill=GridBagConstraints.BOTH;
		con.insets = new Insets(5,5,5,5);
		return con;
	}
	
	private Scroll initScroll(int width, int height){
		Scroll scroll = new Scroll (width, height);
		return scroll;
	}


	private void initScrollPane(Container contentPane, int gridx, int gridy, int cellHeight, int cellWidth) {
		Scroll scroll = new Scroll (400, 400);
		scroll.putScrollToContentPane(contentPane,  gridx, gridy, cellHeight, cellWidth);
		
	}

	private class Scroll {
		private JPanel contentPane;
		private GridBagLayout layout;
		private int width;
		private int height;
		private JScrollPane scrollPane;
		
		Scroll(int width, int height){
			this.width=width;
			this.height=height;
			contentPane = new JPanel();
			layout = new GridBagLayout();
			contentPane.setLayout(layout);
			scrollPane = new JScrollPane(contentPane);
		}

		public void add(JComponent component, GridBagConstraints constraints) {
			contentPane.add(component, constraints);
		}
		
		//put the scroll on the pane
		public void putScrollToContentPane(Container pane, int gridx, int gridy, int cellWidth, int cellHeight) {
			GridBagConstraints con = new GridBagConstraints();
			con.gridy=gridy;
			con.gridx=gridx;
			con.gridheight=cellHeight;
			con.gridwidth=cellWidth;
			con.ipadx=width;
			con.ipady=height;
			if (scrollPane == null){
				throw new AssertionError("ScrollPane is not created yet!" , null);
			}
			pane.add(scrollPane, con);
		}
		
		
	}

	public void bindLogic(ClientLogic logic) {
		this.logic=logic;
	}

}

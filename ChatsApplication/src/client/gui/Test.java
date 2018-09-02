package client.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;



public class Test {
	
	static JFrame window;
	static JPanel buttonPanel;
	static JPanel labelPanel;
	static int panelCount=0;
	
 	private static JFrame initFrame(String title) {
		JFrame frame = new JFrame();
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1048, 740);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		JPanel mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5,5,5,5));
		frame.setContentPane(mainContentPane);
		GridBagLayout contentPaneLayout = new GridBagLayout();
		mainContentPane.setLayout(contentPaneLayout);
		return frame;
		
	}

	public static void main(String[] args) {
		window = initFrame("Test");
		buttonPanel = createPanelonWindow(100, 200);
		labelPanel = createPanelonWindow(300, 300);
		JButton button = new JButton("Add item");
		button.addMouseListener(new MouseListener() {
			
			int counter = 1;
			
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
				 JLabel label = new JLabel("Label ¹ "+String.valueOf(counter));
				 GridBagConstraints con = new GridBagConstraints();
				 con.gridy=counter;
				labelPanel.add(label,con);
				counter++;
				labelPanel.revalidate();
				
				
			}
		});
		buttonPanel.add(button);
		
		
		
		window.pack();
		window.setVisible(true);

	}

	private static JPanel createPanelonWindow(int ipadx, int ipady) {
		GridBagConstraints con = new GridBagConstraints();
		con.gridy=panelCount++;
		con.ipadx = ipadx;
		con.ipady = ipady;
		JPanel panel = new JPanel(new GridBagLayout());
		window.getContentPane().add(panel, con);
		return panel;
	}
	
	
}

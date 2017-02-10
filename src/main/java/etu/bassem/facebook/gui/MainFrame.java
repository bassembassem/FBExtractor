package etu.bassem.facebook.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.UIManager;

import etu.bassem.facebook.FBExtroctor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtAljazeerachannel;
	private JButton btnShowPosts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Facebook Page Corpus Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblFacebookPageCorpus = new JLabel("Facebook Page Corpus Generator");
		lblFacebookPageCorpus.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblPageUrl = new JLabel("Page Url :");
		
		JLabel lblHttpswwwfacebookcom = new JLabel("https://www.facebook.com/");
		
		txtAljazeerachannel = new JTextField();
		txtAljazeerachannel.setText("aljazeerachannel");
		txtAljazeerachannel.setColumns(10);
		
		JLabel lblPostNumber = new JLabel("Post Number :");
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "20", "50", "100"}));
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FBExtroctor.postCountLimit = Integer.valueOf(comboBox.getSelectedItem().toString());
				FBExtroctor.processing("https://www.facebook.com/" + txtAljazeerachannel.getText());
				
				ResultsFrame frame = new ResultsFrame();
				frame.setVisible(true);
				
				btnShowPosts.setEnabled(true);
			}
		});
		
		btnShowPosts = new JButton("Show Posts");
		btnShowPosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultsFrame frame = new ResultsFrame();
				frame.setVisible(true);
			}
		});
		btnShowPosts.setEnabled(false);
		
		JButton btnShowUsers = new JButton("Show Users");
		btnShowUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultsFrame frame = new ResultsFrame();
				frame.setVisible(true);
			}
		});
		btnShowUsers.setEnabled(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPostNumber)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblFacebookPageCorpus)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblPageUrl)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblHttpswwwfacebookcom))
								.addComponent(btnGenerate))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(4)
									.addComponent(btnShowPosts)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnShowUsers))
								.addComponent(txtAljazeerachannel))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addComponent(lblFacebookPageCorpus)
					.addGap(52)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPageUrl)
						.addComponent(lblHttpswwwfacebookcom)
						.addComponent(txtAljazeerachannel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPostNumber)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnShowUsers)
						.addComponent(btnShowPosts)
						.addComponent(btnGenerate))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}

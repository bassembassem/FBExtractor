package etu.bassem.facebook.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JList;

import etu.bassem.facebook.FBExtroctor;
import etu.bassem.facebook.ValueComparator;
import etu.bassem.facebook.model.FBComment;
import etu.bassem.facebook.model.FBPost;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultsFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JList list;

	
	private Map<String, Integer> wordMap = new HashMap<String, Integer>();
	
	/**
	 * Create the frame.
	 */
	public ResultsFrame() {
		
		setTitle("Facebook Page Corpus Generator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1097, 698);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Message", "Word Count", "Id User", "Name", "Age", "Gender"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(table);
		
		list = new JList();
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
		
		DefaultListModel<FBPost> model = new DefaultListModel<FBPost>();
		
		for(FBPost post : FBExtroctor.fbPosts) {
			model.addElement(post);
		}
		
		list.setModel(model);
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent arg0) {
				
				
				FBPost postSelected = (FBPost) list.getSelectedValue();
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				model.getDataVector().clear();
				
				for (FBComment comment : postSelected.getComments()) {
					
					model.addRow(new Object[]{comment.getId(), comment.getMessage(), comment.getWordCount(), comment.getUser().getId(), comment.getUser().getName(), comment.getUser().getAge(), comment.getUser().getGender()});
				
				
					String[] wordArray = comment.getMessage().split("\\s+");
					for (String string : wordArray) {
						
						if (wordMap.containsKey(string)) {
							wordMap.put(string, wordMap.get(string) + 1);
						} else{
							wordMap.put(string, 1);
						}
					}
					
				
				}
				
				ValueComparator bvc =  new ValueComparator(wordMap);
		        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
		        sorted_map.putAll(wordMap);
		        
				
		        // write file
				try {
					
					PrintWriter writer = new PrintWriter("count-out.csv", "UTF-8");
					
					writer.println("word;count");
					
					for (Entry<String, Integer> entry : sorted_map.entrySet()) {
						writer.println(entry.getKey() + ";" + entry.getValue());
						
					}
					
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		        
			}
		});
	}
}

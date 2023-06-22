package me.pepsi.xeros.vorkath;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GUI {

	public VorkathSlayer pray;
	private JPanel contentPane;
	public JFrame frame;
	
	public JPanel generalPanel;
	public JComboBox<Info> comboBoxPrayers;
	public JSpinner spinnerHealAt;
	public JButton btnApplyNewSettings;
	public JButton btnCloseGui;
	public JList<String> listNearbyMonsters;
	public JLabel lblMonsterList;
	public JList<String> listSelectedMonsters;
	public JScrollPane scrollPaneNearbyMonsters;
	public JScrollPane scrollPaneSelectedMonsters;
	public JList<String> listLoot;
	public JScrollPane scrollPaneLoot;
	public DefaultListModel<String> modelLoot = new DefaultListModel<String>();
	public DefaultListModel<String> modelNearbyMonsters = new DefaultListModel<String>();
	public DefaultListModel<String> modelSelectedMonsters = new DefaultListModel<String>();
	
	public GUI(VorkathSlayer pray) {
		try {
			this.pray = pray;
			SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {

			frame = new JFrame();
			frame.setResizable(false);
			frame.setTitle("Vorkath Slayer");
			frame.setBounds(100, 100, 220, 181);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			frame.setContentPane(contentPane);
			contentPane.setLayout(null);
			frame.setLocationRelativeTo(VorkathSlayer.ctx.mouse.getComponent());
			initComponents();
			frame.repaint();
			frame.setVisible(true);
				     }
			   });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initComponents() {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 190, 65);
		contentPane.add(tabbedPane);
		
		generalPanel = new JPanel();
		generalPanel.setName("");
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);
		
		JLabel lblprayerType = new JLabel("Prayer Type:");
		lblprayerType.setBounds(10, 6, 81, 20);
		generalPanel.add(lblprayerType);
		
		comboBoxPrayers = new JComboBox<Info>(new DefaultComboBoxModel<Info>(Info.values()));
		comboBoxPrayers.setBounds(93, 6, 91, 20);
		generalPanel.add(comboBoxPrayers);
		
		btnApplyNewSettings = new JButton("Apply New Settings");
		btnApplyNewSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onApplyNewSettings();
			}
		});
		btnApplyNewSettings.setBounds(45, 81, 125, 23);
		contentPane.add(btnApplyNewSettings);
		
		btnCloseGui = new JButton("Close GUI");
		btnCloseGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseGUI();
			}
		});
		btnCloseGui.setBounds(64, 108, 89, 23);
		contentPane.add(btnCloseGui);
	}
	
	public void onApplyNewSettings() {
		Info prayerType = (Info) comboBoxPrayers.getSelectedItem();
		pray.setupInfo(prayerType.getSelection());
		pray.started = true;
	}
	
	public void onCloseGUI() {
		frame.dispose();
	}
}
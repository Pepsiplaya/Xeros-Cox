package me.pepsi.xeros.crafting;

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

	public AIOGemCutter cut;
	private JPanel contentPane;
	public JFrame frame;
	
	public JPanel generalPanel;
	public JComboBox<Gems> comboBoxOres;
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
	
	public GUI(AIOGemCutter cut) {
		try {
			this.cut = cut;
			SwingUtilities.invokeLater(new Runnable() {  public void run() {

			frame = new JFrame();
			frame.setResizable(false);
			frame.setTitle("AIO Gem Cutter");
			frame.setBounds(100, 100, 220, 210);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			frame.setContentPane(contentPane);
			contentPane.setLayout(null);
			frame.setLocationRelativeTo(cut.ctx.mouse.getComponent());
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
		tabbedPane.setBounds(10, 11, 190, 75);
		contentPane.add(tabbedPane);
		
		generalPanel = new JPanel();
		generalPanel.setName("");
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);
		
		btnApplyNewSettings = new JButton("Apply New Settings");
		btnApplyNewSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onApplyNewSettings();
			}
		});
		btnApplyNewSettings.setBounds(45, 93, 125, 23);
		contentPane.add(btnApplyNewSettings);
		
		btnCloseGui = new JButton("Close GUI");
		btnCloseGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseGUI();
			}
		});
		btnCloseGui.setBounds(65, 120, 89, 23);
		contentPane.add(btnCloseGui);
		
		JLabel lblgemType = new JLabel("Gem Type:");
		lblgemType.setBounds(10, 11, 81, 20);
		generalPanel.add(lblgemType);
		
		comboBoxOres = new JComboBox<Gems>(new DefaultComboBoxModel<Gems>(Gems.values()));
		comboBoxOres.setBounds(93, 11, 91, 20);
		generalPanel.add(comboBoxOres);
	}
	
	public void onApplyNewSettings() {
		Gems gemType = (Gems) comboBoxOres.getSelectedItem();
		cut.setupGems(gemType.getItemId());
		cut.started = true;
	}
	
	public void onCloseGUI() {
		frame.dispose();
	}
}
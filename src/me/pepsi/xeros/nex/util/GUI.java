package me.pepsi.xeros.nex.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import me.pepsi.xeros.nex.core.Nex;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class GUI {

	public Nex gui;
	private JPanel contentPane;
	public JFrame frame;
	
	public JPanel generalPanel;
	public JComboBox<Prayer> comboBoxPrayer;
	public JButton btnApplyNewSettings;
	public JButton btnCloseGui;
	public DefaultListModel<String> modelLoot = new DefaultListModel<String>();
	public DefaultListModel<String> modelNearbyMonsters = new DefaultListModel<String>();
	public DefaultListModel<String> modelSelectedMonsters = new DefaultListModel<String>();
	private JSpinner eatAt;
	private JSpinner leaderDelay;
	private JLabel lbloreEat;
	private JLabel lblLeaderDelay;
	
	public GUI(Nex gui) {
		try {
			this.gui = gui;
			SwingUtilities.invokeLater(new Runnable() {  public void run() {

			frame = new JFrame();
			frame.setResizable(false);
			frame.setTitle("Pepsi Nex");
			frame.setBounds(100, 100, 249, 231);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			frame.setContentPane(contentPane);
			contentPane.setLayout(null);
			frame.setLocationRelativeTo(gui.ctx.mouse.getComponent());
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
		tabbedPane.setBounds(10, 11, 213, 118);
		contentPane.add(tabbedPane);
		
		generalPanel = new JPanel();
		generalPanel.setName("");
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);
		
		JLabel lblorePrayer = new JLabel("Prayer Type:");
		lblorePrayer.setBounds(10, 10, 81, 20);
		generalPanel.add(lblorePrayer);
		
		comboBoxPrayer = new JComboBox<Prayer>(new DefaultComboBoxModel<Prayer>(Prayer.values()));
		comboBoxPrayer.setBounds(93, 10, 91, 20);
		generalPanel.add(comboBoxPrayer);
		
		lbloreEat = new JLabel("Eat At:");
		lbloreEat.setBounds(10, 36, 81, 20);
		generalPanel.add(lbloreEat);
		
		eatAt = new JSpinner ();
		eatAt.setModel(new SpinnerNumberModel(75, 1, 80, 1));
		eatAt.setBounds(93, 36, 45, 20);
		generalPanel.add(eatAt);
		
		lblLeaderDelay = new JLabel("Leader Delay:");
		lblLeaderDelay.setBounds(10, 62, 81, 20);
		generalPanel.add(lblLeaderDelay);
		
		leaderDelay = new JSpinner();
		leaderDelay.setModel(new SpinnerNumberModel(10, 10, 100, 1));
		leaderDelay.setBounds(93, 62, 45, 20);
		generalPanel.add(leaderDelay);
		
		btnApplyNewSettings = new JButton("Apply New Settings");
		btnApplyNewSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onApplyNewSettings();
			}
		});
		btnApplyNewSettings.setBounds(54, 133, 125, 23);
		contentPane.add(btnApplyNewSettings);
		
		btnCloseGui = new JButton("Close GUI");
		btnCloseGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseGUI();
			}
		});
		btnCloseGui.setBounds(74, 160, 89, 23);
		contentPane.add(btnCloseGui);
	}
	
	public void onApplyNewSettings() {
		Nex.eatAt = (Integer) eatAt.getValue();
		Prayer prayerType = (Prayer) comboBoxPrayer.getSelectedItem();
		gui.setupPrayer(prayerType.getSelection());
		gui.started = true;
	}
	
	public void onCloseGUI() {
		frame.dispose();
	}
}
package me.pepsi.xeros.raids.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import simple.api.ClientContext;
import javax.swing.border.EmptyBorder;

import me.pepsi.xeros.raids.Raids;
import javax.swing.JTextArea;

public class GUI {

	public Raids gui;
	private JPanel contentPane;
	public static JFrame frame;
	
	public JPanel prayersPanel;
	public JComboBox<Range> comboBoxRange;
	public JComboBox<Mage> comboBoxMage;
	public JButton btnApplyNewSettings;
	public JButton btnCloseGui;
	private JSpinner leaderDelay;
	private JLabel lblLeaderDelay;
	private JCheckBox chckbxLeader;
	private JCheckBox chckbxPickupFood;
	private JCheckBox chckbxStaffBeep;
	public static JTextArea usernames;
	private JPanel staffPanel;
	public GUI(Raids gui, ClientContext ctx) {
		try {
			this.gui = gui;
			SwingUtilities.invokeLater(new Runnable() {  public void run() {

			frame = new JFrame();
			frame.setResizable(false);
			frame.setTitle("Ultra Instict Raids");
			frame.setBounds(100, 100, 220, 244);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			frame.setContentPane(contentPane);
			contentPane.setLayout(null);
			frame.setLocationRelativeTo(gui.ctx.mouse.getComponent());
			initComponents(ctx);
			frame.repaint();
			frame.setVisible(true);
				     }
			   });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initComponents(ClientContext ctx) {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 195, 161);
		contentPane.add(tabbedPane);
		
		prayersPanel = new JPanel();
		prayersPanel.setName("");
		tabbedPane.addTab("General", null, prayersPanel, null);
		prayersPanel.setLayout(null);
		
		btnApplyNewSettings = new JButton("Start");
		btnApplyNewSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onApplyNewSettings();
			}
		});
		
		btnApplyNewSettings.setBounds(10, 176, 85, 23);
		contentPane.add(btnApplyNewSettings);
		
		btnCloseGui = new JButton("Close GUI");
		btnCloseGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseGUI();
			}
		});
		
		btnCloseGui.setBounds(105, 176, 85, 23);
		contentPane.add(btnCloseGui);
		
		JLabel lblmageType = new JLabel("Mage Prayer:");
		lblmageType.setBounds(10, 11, 110, 20);
		prayersPanel.add(lblmageType);
		
		comboBoxMage = new JComboBox<Mage>(new DefaultComboBoxModel<Mage>(Mage.values()));
		comboBoxMage.setBounds(93, 11, 91, 20);
		prayersPanel.add(comboBoxMage);
		
		JLabel lblrangeType = new JLabel("Range Prayer:");
		lblrangeType.setBounds(10, 35, 90, 20);
		prayersPanel.add(lblrangeType);
		
		comboBoxRange = new JComboBox<Range>(new DefaultComboBoxModel<Range>(Range.values()));
		comboBoxRange.setBounds(93, 35, 90, 20);
		prayersPanel.add(comboBoxRange);
		
		chckbxLeader = new JCheckBox("Leader");
		chckbxLeader.setBounds(61, 108, 66, 23);
		prayersPanel.add(chckbxLeader);
		
		chckbxPickupFood = new JCheckBox("Pickup Food");
		chckbxPickupFood.setBounds(46, 83, 97, 23);
		prayersPanel.add(chckbxPickupFood);
		
		lblLeaderDelay = new JLabel("Leader Delay:");
		lblLeaderDelay.setBounds(10, 63, 81, 20);
		prayersPanel.add(lblLeaderDelay);
		
		leaderDelay = new JSpinner();
		leaderDelay.setModel(new SpinnerNumberModel(10, 1, 100, 1));
		leaderDelay.setBounds(93, 63, 45, 20);
		prayersPanel.add(leaderDelay);
		
		staffPanel = new JPanel();
		staffPanel.setLayout(null);
		staffPanel.setName("");
		tabbedPane.addTab("Anti-Staff", null, staffPanel, null);
		
		chckbxStaffBeep = new JCheckBox("Staff Beeping");
		chckbxStaffBeep.setBounds(49, 54, 97, 23);
		staffPanel.add(chckbxStaffBeep);
		
		JLabel usernameHere = new JLabel("Enter part of username");
		usernameHere.setBounds(10, 16, 127, 14);
		staffPanel.add(usernameHere);
		
		usernames = new JTextArea();
		usernames.setBounds(134, 11, 46, 22);
		staffPanel.add(usernames);
	}
	
	public void onApplyNewSettings() {
		Range rangeType = (Range) comboBoxRange.getSelectedItem();
		Mage mageType = (Mage) comboBoxMage.getSelectedItem();
		gui.setupRange(rangeType.getSelection());
		gui.setupMage(mageType.getSelection());
		Raids.leaderDelay = (Integer) leaderDelay.getValue();
		Raids.isLeader = chckbxLeader.isSelected();
		Raids.staffBeep = chckbxStaffBeep.isSelected();
		Raids.pickupFood = chckbxPickupFood.isSelected();
		Raids.username = usernames.getText();
		Raids.started = true;
	}
	
	public void onCloseGUI() {
		frame.setVisible(false);
	}
}
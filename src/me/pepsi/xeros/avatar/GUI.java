package me.pepsi.xeros.avatar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import simple.api.ClientContext;
import javax.swing.border.EmptyBorder;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUI {

	public Avatar gui;
	private JPanel contentPane;
	public static JFrame frame;
	
	public JPanel generalPanel;
	public JButton btnApplyNewSettings;
	public JButton btnCloseGui;
	private JLabel lblorePrayer;
	private JComboBox<Prayer> comboBoxPrayer;
	public GUI(Avatar gui, ClientContext ctx) {
		try {
			this.gui = gui;
			SwingUtilities.invokeLater(new Runnable() {  public void run() {

			frame = new JFrame();
			frame.setResizable(false);
			frame.setTitle("Avatar");
			frame.setBounds(100, 100, 220, 162);
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
		tabbedPane.setBounds(10, 11, 195, 68);
		contentPane.add(tabbedPane);
		
		generalPanel = new JPanel();
		generalPanel.setName("");
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);
		
		btnApplyNewSettings = new JButton("Start");
		btnApplyNewSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onApplyNewSettings();
			}
		});
		
		btnApplyNewSettings.setBounds(10, 86, 85, 23);
		contentPane.add(btnApplyNewSettings);
		
		btnCloseGui = new JButton("Close GUI");
		btnCloseGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseGUI();
			}
		});
		
		btnCloseGui.setBounds(105, 86, 85, 23);
		contentPane.add(btnCloseGui);
		
		lblorePrayer = new JLabel("Prayer Type:");
		lblorePrayer.setBounds(10, 8, 81, 20);
		generalPanel.add(lblorePrayer);
		
		comboBoxPrayer = new JComboBox<Prayer>(new DefaultComboBoxModel<Prayer>(Prayer.values()));
		comboBoxPrayer.setBounds(88, 8, 91, 20);
		generalPanel.add(comboBoxPrayer);
	}
	
	public void onApplyNewSettings() {
		Avatar.started = true;
		Prayer prayerType = (Prayer) comboBoxPrayer.getSelectedItem();
		gui.setupPrayer(prayerType.getSelection());
	}
	
	public void onCloseGUI() {
		frame.setVisible(false);
	}
}
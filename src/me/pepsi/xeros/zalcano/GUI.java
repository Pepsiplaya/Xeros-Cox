package me.pepsi.xeros.zalcano;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import simple.api.ClientContext;
import javax.swing.border.EmptyBorder;

public class GUI {

	public Zalcano gui;
	private JPanel contentPane;
	public static JFrame frame;
	
	public JPanel generalPanel;
	public JButton btnApplyNewSettings;
	public JButton btnCloseGui;
	private JSpinner presetSlot;
	private JLabel lblpresetSlot;
	public GUI(Zalcano gui, ClientContext ctx) {
		try {
			this.gui = gui;
			SwingUtilities.invokeLater(new Runnable() {  public void run() {

			frame = new JFrame();
			frame.setResizable(false);
			frame.setTitle("Zalcano");
			frame.setBounds(100, 100, 220, 142);
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
		tabbedPane.setBounds(10, 11, 195, 65);
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
		
		btnApplyNewSettings.setBounds(10, 76, 85, 23);
		contentPane.add(btnApplyNewSettings);
		
		btnCloseGui = new JButton("Close GUI");
		btnCloseGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseGUI();
			}
		});
		
		btnCloseGui.setBounds(105, 76, 85, 23);
		contentPane.add(btnCloseGui);
		
		lblpresetSlot = new JLabel("Preset Slot:");
		lblpresetSlot.setBounds(10, 11, 81, 20);
		generalPanel.add(lblpresetSlot);
		
		presetSlot = new JSpinner ();
		presetSlot.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		presetSlot.setBounds(78, 11, 45, 20);
		generalPanel.add(presetSlot);
	}
	
	public void onApplyNewSettings() {
		Zalcano.slot = (Integer) presetSlot.getValue();
		Zalcano.started = true;
	}
	
	public void onCloseGUI() {
		frame.setVisible(false);
	}
}
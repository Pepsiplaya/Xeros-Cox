package me.pepsi.xeros.mercher;

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
import javax.swing.JTextArea;

public class GUI {

	public Mercher gui;
	private JPanel contentPane;
	public static JFrame frame;
	private JLabel lblBuy;
	private JLabel lblSell;
	private JPanel generalPanel;
	private JButton btnApplyNewSettings;
	private JButton btnCloseGui;
	public JSpinner itemPriceBuy1;
	public JSpinner itemPriceBuy2;
	public JSpinner itemPriceBuy3;
	public JSpinner itemPriceBuy4;
	public JSpinner itemPriceBuy5;
	public JSpinner itemPriceBuy6;
	public JSpinner itemPriceBuy7;
	public JSpinner itemPriceBuy8;
	public JSpinner itemPriceBuy9;
	public JSpinner itemPriceBuy10;
	public JSpinner itemPriceBuy11;
	public JSpinner itemPriceBuy12;
	public JSpinner itemPriceBuy13;
	public JSpinner itemPriceBuy14;
	public JSpinner itemPriceBuy15;
	public JSpinner itemPriceBuy16;
	public JSpinner itemPriceBuy17;
	public JSpinner itemPriceBuy18;
	public JSpinner itemPriceBuy19;
	public JSpinner itemPriceBuy20;
	public JTextArea itemBuy1;
	public JTextArea itemBuy2;
	public JTextArea itemBuy3;
	public JTextArea itemBuy4;
	public JTextArea itemBuy5;
	public JTextArea itemBuy6;
	public JTextArea itemBuy7;
	public JTextArea itemBuy8;
	public JTextArea itemBuy9;
	public JTextArea itemBuy10;
	public JTextArea itemBuy11;
	public JTextArea itemBuy12;
	public JTextArea itemBuy13;
	public JTextArea itemBuy14;
	public JTextArea itemBuy15;
	public JTextArea itemBuy16;
	public JTextArea itemBuy17;
	public JTextArea itemBuy18;
	public JTextArea itemBuy19;
	public JTextArea itemBuy20;
	public JSpinner itemPriceSell1;
	public JSpinner itemPriceSell2;
	public JSpinner itemPriceSell3;
	public JSpinner itemPriceSell4;
	public JSpinner itemPriceSell5;
	public JSpinner itemPriceSell6;
	public JSpinner itemPriceSell7;
	public JSpinner itemPriceSell8;
	public JSpinner itemPriceSell9;
	public JSpinner itemPriceSell10;
	public JSpinner itemPriceSell11;
	public JSpinner itemPriceSell12;
	public JSpinner itemPriceSell13;
	public JSpinner itemPriceSell14;
	public JSpinner itemPriceSell15;
	public JSpinner itemPriceSell16;
	public JSpinner itemPriceSell17;
	public JSpinner itemPriceSell18;
	public JSpinner itemPriceSell19;
	public JSpinner itemPriceSell20;

	public GUI(Mercher gui, ClientContext ctx) {
		try {
			this.gui = gui;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					frame = new JFrame();
					frame.setResizable(false);
					frame.setTitle("Mercher");
					frame.setBounds(100, 100, 603, 483);
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
		tabbedPane.setBounds(10, 11, 567, 381);
		contentPane.add(tabbedPane);

		generalPanel = new JPanel();
		generalPanel.setName("");
		tabbedPane.addTab("General", null, generalPanel, null);
		generalPanel.setLayout(null);

		itemBuy1 = new JTextArea();
		itemBuy1.setText("Crystalline key");
		itemBuy1.setBounds(10, 28, 87, 22);
		generalPanel.add(itemBuy1);

		lblBuy = new JLabel("Buy");
		lblBuy.setBounds(128, 11, 23, 14);
		generalPanel.add(lblBuy);

		itemBuy2 = new JTextArea();
		itemBuy2.setText("Hespori key");
		itemBuy2.setToolTipText("");
		itemBuy2.setBounds(10, 58, 87, 22);
		generalPanel.add(itemBuy2);

		itemPriceBuy1 = new JSpinner();
		itemPriceBuy1.setModel(new SpinnerNumberModel(11000000, 0, 2147000000, 1));
		itemPriceBuy1.setBounds(106, 28, 71, 20);
		generalPanel.add(itemPriceBuy1);

		itemPriceBuy2 = new JSpinner();
		itemPriceBuy2.setModel(new SpinnerNumberModel(10000000, 0, 2147000000, 1));
		itemPriceBuy2.setBounds(107, 58, 71, 20);
		generalPanel.add(itemPriceBuy2);

		itemBuy3 = new JTextArea();
		itemBuy3.setText("Vote crystal");
		itemBuy3.setBounds(10, 88, 87, 22);
		generalPanel.add(itemBuy3);

		itemPriceBuy3 = new JSpinner();
		itemPriceBuy3.setModel(new SpinnerNumberModel(3500000, 0, 2147000000, 1));
		itemPriceBuy3.setBounds(107, 88, 71, 20);
		generalPanel.add(itemPriceBuy3);

		lblSell = new JLabel("Sell");
		lblSell.setBounds(211, 11, 23, 14);
		generalPanel.add(lblSell);

		itemPriceSell1 = new JSpinner();
		itemPriceSell1.setModel(new SpinnerNumberModel(0, 0, 2147000000, 1));
		itemPriceSell1.setBounds(187, 28, 71, 20);
		generalPanel.add(itemPriceSell1);

		itemBuy4 = new JTextArea();
		itemBuy4.setText("Ring of wealth");
		itemBuy4.setBounds(10, 118, 87, 22);
		generalPanel.add(itemBuy4);

		itemBuy5 = new JTextArea();
		itemBuy5.setText("Abyssal whip");
		itemBuy5.setBounds(10, 148, 87, 22);
		generalPanel.add(itemBuy5);

		itemBuy6 = new JTextArea();
		itemBuy6.setText("Black mask");
		itemBuy6.setBounds(10, 178, 87, 22);
		generalPanel.add(itemBuy6);

		itemBuy7 = new JTextArea();
		itemBuy7.setText("25,000 Fo\r\n");
		itemBuy7.setBounds(10, 208, 87, 22);
		generalPanel.add(itemBuy7);

		itemBuy8 = new JTextArea();
		itemBuy8.setText("10,000 Fo");
		itemBuy8.setBounds(10, 238, 87, 22);
		generalPanel.add(itemBuy8);

		itemBuy9 = new JTextArea();
		itemBuy9.setText("Dragon arrow");
		itemBuy9.setBounds(10, 268, 87, 22);
		generalPanel.add(itemBuy9);

		itemBuy10 = new JTextArea();
		itemBuy10.setText("Crystal key");
		itemBuy10.setBounds(10, 298, 87, 22);
		generalPanel.add(itemBuy10);

		itemPriceBuy4 = new JSpinner();
		itemPriceBuy4.setModel(new SpinnerNumberModel(35000000, 0, 2147000000, 1));
		itemPriceBuy4.setBounds(106, 118, 71, 20);
		generalPanel.add(itemPriceBuy4);

		itemPriceBuy5 = new JSpinner();
		itemPriceBuy5.setModel(new SpinnerNumberModel(4500000, 0, 2147000000, 1));
		itemPriceBuy5.setBounds(106, 148, 71, 20);
		generalPanel.add(itemPriceBuy5);

		itemPriceBuy6 = new JSpinner();
		itemPriceBuy6.setModel(new SpinnerNumberModel(4000000, 0, 2147000000, 1));
		itemPriceBuy6.setBounds(106, 177, 71, 20);
		generalPanel.add(itemPriceBuy6);

		itemPriceBuy7 = new JSpinner();
		itemPriceBuy7.setModel(new SpinnerNumberModel(75000000, 0, 2147000000, 1));
		itemPriceBuy7.setBounds(106, 208, 71, 20);
		generalPanel.add(itemPriceBuy7);

		itemPriceBuy8 = new JSpinner();
		itemPriceBuy8.setModel(new SpinnerNumberModel(30000000, 0, 2147000000, 1));
		itemPriceBuy8.setBounds(106, 238, 71, 20);
		generalPanel.add(itemPriceBuy8);

		itemPriceBuy9 = new JSpinner();
		itemPriceBuy9.setModel(new SpinnerNumberModel(25000, 0, 2147000000, 1));
		itemPriceBuy9.setBounds(106, 268, 71, 20);
		generalPanel.add(itemPriceBuy9);

		itemPriceBuy10 = new JSpinner();
		itemPriceBuy10.setModel(new SpinnerNumberModel(1500000, 0, 2147000000, 1));
		itemPriceBuy10.setBounds(106, 298, 71, 20);
		generalPanel.add(itemPriceBuy10);

		itemPriceSell2 = new JSpinner();
		itemPriceSell2.setModel(new SpinnerNumberModel(12000000, 0, 2147000000, 1));
		itemPriceSell2.setBounds(187, 58, 71, 20);
		generalPanel.add(itemPriceSell2);

		itemPriceSell3 = new JSpinner();
		itemPriceSell3.setModel(new SpinnerNumberModel(4500000, 0, 2147000000, 1));
		itemPriceSell3.setBounds(187, 88, 71, 20);
		generalPanel.add(itemPriceSell3);

		itemPriceSell4 = new JSpinner();
		itemPriceSell4.setModel(new SpinnerNumberModel(35000000, 0, 2147000000, 1));
		itemPriceSell4.setBounds(187, 118, 71, 20);
		generalPanel.add(itemPriceSell4);

		itemPriceSell5 = new JSpinner();
		itemPriceSell5.setModel(new SpinnerNumberModel(5500000, 0, 2147000000, 1));
		itemPriceSell5.setBounds(187, 148, 71, 20);
		generalPanel.add(itemPriceSell5);

		itemPriceSell6 = new JSpinner();
		itemPriceSell6.setModel(new SpinnerNumberModel(750000, 0, 2147000000, 1));
		itemPriceSell6.setBounds(187, 177, 71, 20);
		generalPanel.add(itemPriceSell6);

		itemPriceSell7 = new JSpinner();
		itemPriceSell7.setModel(new SpinnerNumberModel(82000000, 0, 2147000000, 1));
		itemPriceSell7.setBounds(187, 208, 71, 20);
		generalPanel.add(itemPriceSell7);

		itemPriceSell8 = new JSpinner();
		itemPriceSell8.setModel(new SpinnerNumberModel(32000000, 0, 2147000000, 1));
		itemPriceSell8.setBounds(187, 238, 71, 20);
		generalPanel.add(itemPriceSell8);

		itemPriceSell9 = new JSpinner();
		itemPriceSell9.setModel(new SpinnerNumberModel(25000, 0, 2147000000, 1));
		itemPriceSell9.setBounds(187, 268, 71, 20);
		generalPanel.add(itemPriceSell9);

		itemPriceSell10 = new JSpinner();
		itemPriceSell10.setModel(new SpinnerNumberModel(2000000, 0, 2147000000, 1));
		itemPriceSell10.setBounds(187, 298, 71, 20);
		generalPanel.add(itemPriceSell10);

		itemPriceSell20 = new JSpinner();
		itemPriceSell20.setBounds(445, 298, 71, 20);
		generalPanel.add(itemPriceSell20);

		itemPriceBuy20 = new JSpinner();
		itemPriceBuy20.setModel(new SpinnerNumberModel(150000000, 0, 2147000000, 1));
		itemPriceBuy20.setBounds(364, 298, 71, 20);
		generalPanel.add(itemPriceBuy20);

		itemBuy20 = new JTextArea();
		itemBuy20.setText("50,000 Fo");
		itemBuy20.setBounds(268, 298, 87, 22);
		generalPanel.add(itemBuy20);

		itemBuy19 = new JTextArea();
		itemBuy19.setText("Trident of the sea");
		itemBuy19.setBounds(268, 268, 87, 22);
		generalPanel.add(itemBuy19);

		itemPriceBuy19 = new JSpinner();
		itemPriceBuy19.setModel(new SpinnerNumberModel(10000000, 0, 2147000000, 1));
		itemPriceBuy19.setBounds(364, 268, 71, 20);
		generalPanel.add(itemPriceBuy19);

		itemPriceSell19 = new JSpinner();
		itemPriceSell19.setBounds(445, 268, 71, 20);
		generalPanel.add(itemPriceSell19);

		itemPriceSell18 = new JSpinner();
		itemPriceSell18.setBounds(445, 238, 71, 20);
		generalPanel.add(itemPriceSell18);

		itemPriceBuy18 = new JSpinner();
		itemPriceBuy18.setModel(new SpinnerNumberModel(16000000, 0, 2147000000, 1));
		itemPriceBuy18.setBounds(364, 238, 71, 20);
		generalPanel.add(itemPriceBuy18);

		itemBuy18 = new JTextArea();
		itemBuy18.setText("Crystal legs");
		itemBuy18.setBounds(268, 238, 87, 22);
		generalPanel.add(itemBuy18);

		itemBuy17 = new JTextArea();
		itemBuy17.setText("Crystal helm");
		itemBuy17.setBounds(268, 208, 87, 22);
		generalPanel.add(itemBuy17);

		itemPriceBuy17 = new JSpinner();
		itemPriceBuy17.setModel(new SpinnerNumberModel(16000000, 0, 2147000000, 1));
		itemPriceBuy17.setBounds(364, 208, 71, 20);
		generalPanel.add(itemPriceBuy17);

		itemPriceSell17 = new JSpinner();
		itemPriceSell17.setBounds(445, 208, 71, 20);
		generalPanel.add(itemPriceSell17);

		itemPriceSell16 = new JSpinner();
		itemPriceSell16.setBounds(445, 177, 71, 20);
		generalPanel.add(itemPriceSell16);

		itemPriceBuy16 = new JSpinner();
		itemPriceBuy16.setModel(new SpinnerNumberModel(16000000, 0, 2147000000, 1));
		itemPriceBuy16.setBounds(364, 177, 71, 20);
		generalPanel.add(itemPriceBuy16);

		itemBuy16 = new JTextArea();
		itemBuy16.setText("Crystal body");
		itemBuy16.setBounds(268, 178, 87, 22);
		generalPanel.add(itemBuy16);

		itemBuy15 = new JTextArea();
		itemBuy15.setText("Serpentine");
		itemBuy15.setBounds(268, 148, 87, 22);
		generalPanel.add(itemBuy15);

		itemPriceBuy15 = new JSpinner();
		itemPriceBuy15.setModel(new SpinnerNumberModel(15000000, 0, 2147000000, 1));
		itemPriceBuy15.setBounds(364, 148, 71, 20);
		generalPanel.add(itemPriceBuy15);

		itemPriceSell15 = new JSpinner();
		itemPriceSell15.setBounds(445, 148, 71, 20);
		generalPanel.add(itemPriceSell15);

		itemPriceSell14 = new JSpinner();
		itemPriceSell14.setBounds(445, 118, 71, 20);
		generalPanel.add(itemPriceSell14);

		itemPriceBuy14 = new JSpinner();
		itemPriceBuy14.setModel(new SpinnerNumberModel(8000000, 0, 2147000000, 1));
		itemPriceBuy14.setBounds(364, 118, 71, 20);
		generalPanel.add(itemPriceBuy14);

		itemBuy14 = new JTextArea();
		itemBuy14.setText("The unbearable's key");
		itemBuy14.setBounds(268, 118, 87, 22);
		generalPanel.add(itemBuy14);

		itemBuy13 = new JTextArea();
		itemBuy13.setText("Corrupted ork's key");
		itemBuy13.setBounds(268, 88, 87, 22);
		generalPanel.add(itemBuy13);

		itemPriceBuy13 = new JSpinner();
		itemPriceBuy13.setModel(new SpinnerNumberModel(8000000, 0, 2147000000, 1));
		itemPriceBuy13.setBounds(365, 88, 71, 20);
		generalPanel.add(itemPriceBuy13);

		itemPriceSell13 = new JSpinner();
		itemPriceSell13.setBounds(445, 88, 71, 20);
		generalPanel.add(itemPriceSell13);

		itemPriceSell12 = new JSpinner();
		itemPriceSell12.setBounds(445, 58, 71, 20);
		generalPanel.add(itemPriceSell12);

		itemPriceBuy12 = new JSpinner();
		itemPriceBuy12.setModel(new SpinnerNumberModel(3750000, 0, 2147000000, 1));
		itemPriceBuy12.setBounds(365, 58, 71, 20);
		generalPanel.add(itemPriceBuy12);

		itemBuy12 = new JTextArea();
		itemBuy12.setToolTipText("");
		itemBuy12.setText("Common raids key");
		itemBuy12.setBounds(268, 58, 87, 22);
		generalPanel.add(itemBuy12);

		itemBuy11 = new JTextArea();
		itemBuy11.setText("Raids rare key");
		itemBuy11.setBounds(268, 28, 87, 22);
		generalPanel.add(itemBuy11);

		itemPriceBuy11 = new JSpinner();
		itemPriceBuy11.setModel(new SpinnerNumberModel(240000000, 0, 2147000000, 1));
		itemPriceBuy11.setBounds(364, 28, 71, 20);
		generalPanel.add(itemPriceBuy11);

		itemPriceSell11 = new JSpinner();
		itemPriceSell11.setBounds(445, 28, 71, 20);
		generalPanel.add(itemPriceSell11);

		btnApplyNewSettings = new JButton("Start");
		btnApplyNewSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onApplyNewSettings();
			}
		});

		btnApplyNewSettings.setBounds(27, 402, 124, 32);
		contentPane.add(btnApplyNewSettings);

		btnCloseGui = new JButton("Close GUI");
		btnCloseGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseGUI();
			}
		});

		btnCloseGui.setBounds(436, 403, 124, 32);
		contentPane.add(btnCloseGui);
	}

	public void onApplyNewSettings() {
		Mercher.started = true;
		Mercher.itemBuy1 = itemBuy1.getText();
		Mercher.itemPriceBuy1 = (Integer) itemPriceBuy1.getValue();
		Mercher.itemPriceSell1 = (Integer) itemPriceSell1.getValue();
		Mercher.itemBuy2 = itemBuy2.getText();
		Mercher.itemPriceBuy2 = (Integer) itemPriceBuy2.getValue();
		Mercher.itemPriceSell2 = (Integer) itemPriceSell2.getValue();
		Mercher.itemBuy3 = itemBuy3.getText();
		Mercher.itemPriceBuy3 = (Integer) itemPriceBuy3.getValue();
		Mercher.itemPriceSell3 = (Integer) itemPriceSell3.getValue();
		Mercher.itemBuy4 = itemBuy4.getText();
		Mercher.itemPriceBuy4 = (Integer) itemPriceBuy4.getValue();
		Mercher.itemPriceSell4 = (Integer) itemPriceSell4.getValue();
		Mercher.itemBuy5 = itemBuy5.getText();
		Mercher.itemPriceBuy5 = (Integer) itemPriceBuy5.getValue();
		Mercher.itemPriceSell5 = (Integer) itemPriceSell5.getValue();
		Mercher.itemBuy6 = itemBuy6.getText();
		Mercher.itemPriceBuy6 = (Integer) itemPriceBuy6.getValue();
		Mercher.itemPriceSell6 = (Integer) itemPriceSell6.getValue();
		Mercher.itemBuy7 = itemBuy7.getText();
		Mercher.itemPriceBuy7 = (Integer) itemPriceBuy7.getValue();
		Mercher.itemPriceSell7 = (Integer) itemPriceSell7.getValue();
		Mercher.itemBuy8 = itemBuy8.getText();
		Mercher.itemPriceBuy8 = (Integer) itemPriceBuy8.getValue();
		Mercher.itemPriceSell8 = (Integer) itemPriceSell8.getValue();
		Mercher.itemBuy9 = itemBuy9.getText();
		Mercher.itemPriceBuy9 = (Integer) itemPriceBuy9.getValue();
		Mercher.itemPriceSell9 = (Integer) itemPriceSell9.getValue();
		Mercher.itemBuy10 = itemBuy10.getText();
		Mercher.itemPriceBuy10 = (Integer) itemPriceBuy10.getValue();
		Mercher.itemPriceSell10 = (Integer) itemPriceSell10.getValue();
		Mercher.itemBuy11 = itemBuy11.getText();
		Mercher.itemPriceBuy11 = (Integer) itemPriceBuy11.getValue();
		Mercher.itemPriceSell11 = (Integer) itemPriceSell11.getValue();
		Mercher.itemBuy12 = itemBuy12.getText();
		Mercher.itemPriceBuy12 = (Integer) itemPriceBuy12.getValue();
		Mercher.itemPriceSell12 = (Integer) itemPriceSell12.getValue();
		Mercher.itemBuy13 = itemBuy13.getText();
		Mercher.itemPriceBuy13 = (Integer) itemPriceBuy13.getValue();
		Mercher.itemPriceSell13 = (Integer) itemPriceSell13.getValue();
		Mercher.itemBuy14 = itemBuy14.getText();
		Mercher.itemPriceBuy14 = (Integer) itemPriceBuy14.getValue();
		Mercher.itemPriceSell14 = (Integer) itemPriceSell14.getValue();
		Mercher.itemBuy15 = itemBuy15.getText();
		Mercher.itemPriceBuy15 = (Integer) itemPriceBuy15.getValue();
		Mercher.itemPriceSell15 = (Integer) itemPriceSell15.getValue();
		Mercher.itemBuy16 = itemBuy16.getText();
		Mercher.itemPriceBuy16 = (Integer) itemPriceBuy16.getValue();
		Mercher.itemPriceSell16 = (Integer) itemPriceSell16.getValue();
		Mercher.itemBuy17 = itemBuy17.getText();
		Mercher.itemPriceBuy17 = (Integer) itemPriceBuy17.getValue();
		Mercher.itemPriceSell17 = (Integer) itemPriceSell17.getValue();
		Mercher.itemBuy18 = itemBuy18.getText();
		Mercher.itemPriceBuy18 = (Integer) itemPriceBuy18.getValue();
		Mercher.itemPriceSell18 = (Integer) itemPriceSell18.getValue();
		Mercher.itemBuy19 = itemBuy19.getText();
		Mercher.itemPriceBuy19 = (Integer) itemPriceBuy19.getValue();
		Mercher.itemPriceSell19 = (Integer) itemPriceSell19.getValue();
		Mercher.itemBuy20 = itemBuy20.getText();
		Mercher.itemPriceBuy20 = (Integer) itemPriceBuy20.getValue();
		Mercher.itemPriceSell20 = (Integer) itemPriceSell20.getValue();
		Mercher.stage = 1;
	}

	public void onCloseGUI() {
		frame.setVisible(false);
	}
}
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

//*************************************************************************
// EnigmaGUI.java       Author: Huff                     5/6/2016
//
// The main panel for displaying the instructions, settings, and message center.
// Contains private class to listen for keyboard events, button events and item events
//*************************************************************************

public class EnigmaGUI extends JPanel{
	private JPanel instructionsPanel, settingsPanel, messagePanel, keyPanel;
	private JTextField plugField;
	private JComboBox fastBox, midBox, slowBox, reflectorBox, plugBox;
	private JSpinner fastPosSpinner, midPosSpinner, slowPosSpinner, fastRingSpinner, midRingSpinner, slowRingSpinner;
	private JRadioButton presetRadio, chooseRadio;
	private JButton chooseSettingsButton, showSettingsButton, showInstructionsButton, showKeyButton, continueButton, 
	closeButton, encodeButton, resetButton;
	private JTextArea inputArea, outputArea;
	private JLabel rotorsKeyLabel, positionKeyLabel, ringKeyLabel, plugKeyLabel;
	private GridBagLayout gridBag;
	private CardLayout cl = new CardLayout();
	private JPanel parent = this;
	private ButtonListener lfb;
	private RadioListener lfr;
	private KeyBoardListener lfk;
	private EnigmaMachine enigma;
	private JScrollPane inputPane, outputPane;

	//********************************************************************
	//  Constructor: Sets up the GUI.
	//********************************************************************
	public EnigmaGUI(){
		this.setPreferredSize(new Dimension(600, 500));
		cl = new CardLayout();
		this.setLayout(cl);
		gridBag = new GridBagLayout();
		lfr = new RadioListener();
		lfb = new ButtonListener();
		lfk = new KeyBoardListener();
		this.add(makeInstructionsPanel(), "instructions");
		this.add(makeSettingsPanel(), "settings");
		this.add(makeMessagePanel(), "message");
		this.add(makeKeyPanel(), "key");
		cl.show(parent, "instructions");
	}

	//********************************************************************
	// Sets up and returns a panel for showing instructions
	//********************************************************************
	private JPanel makeInstructionsPanel(){
		instructionsPanel = new JPanel();
		String instructions = "- Select your settings for the Enigma Machine." +
				"\n\n-Choose your rotors, inital position, and ring position" +
				"\n\n-Either use one of 5 default plugboard settings or choose your own, a letter may only be used once" +
				"\n\n-The reset button restores the rotor positions to the initial positions before you began typing" +
				"\n\n-To encode a message, type or paste your message in the input area" +
				"\n\n-To decode a message, use the key to figure out the settings the message was encoded with. Enter the correct settings " +
				"and type or paste the encoded message in the input area. The decoded message will appear in the output area.";

		instructionsPanel.setLayout(gridBag);

		JLabel titleLabel = new JLabel("Instructions");
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 0;
		instructionsPanel.add(titleLabel, gbc_titleLabel);

		JTextArea instructionsArea = new JTextArea(18,40);
		instructionsArea.setEditable(false);
		instructionsArea.setLineWrap(true);
		instructionsArea.setWrapStyleWord(true);
		JScrollPane instructionsPane = new JScrollPane(instructionsArea);
		GridBagConstraints gbc_instructionsPane = new GridBagConstraints();
		gbc_instructionsPane.insets = new Insets(0, 0, 5, 5);
		gbc_instructionsPane.gridx = 0;
		gbc_instructionsPane.gridy = 1;
		instructionsArea.setBackground(SystemColor.window);
		instructionsArea.setText(instructions);
		instructionsPanel.add(instructionsPane, gbc_instructionsPane);

		continueButton = new JButton("Continue");
		continueButton.addActionListener(lfb);
		GridBagConstraints gbc_continueButton = new GridBagConstraints();
		gbc_continueButton.insets = new Insets(0, 0, 5, 5);
		gbc_continueButton.gridx = 0;
		gbc_continueButton.gridy = 2;
		instructionsPanel.add(continueButton, gbc_continueButton);

		return instructionsPanel;
	}

	//********************************************************************
	// Sets up and returns a panel for the use to customize the settings
	//********************************************************************
	private JPanel makeSettingsPanel(){
		settingsPanel = new JPanel();
		settingsPanel.setLayout(gridBag);

		JLabel settingsTitleLabel = new JLabel("Settings");
		settingsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_settingsTitleLabel = new GridBagConstraints();
		gbc_settingsTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_settingsTitleLabel.gridx = 0;
		gbc_settingsTitleLabel.gridy = 0;
		settingsPanel.add(settingsTitleLabel, gbc_settingsTitleLabel);

		JPanel rotorPanel = new JPanel();
		GridBagConstraints gbc_rotorPanel = new GridBagConstraints();
		gbc_rotorPanel.insets = new Insets(0, 0, 5, 5);
		gbc_rotorPanel.gridx = 0;
		gbc_rotorPanel.gridy = 1;
		FlowLayout flowLayout = (FlowLayout) rotorPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		String[] rotorStrings = {"I", "II", "III", "IV", "V"};
		String[] reflectorStrings = {"B", "C"};
		String[] plugStrings = {"1", "2", "3", "4", "5"};

		JLabel rotorLabel1 = new JLabel("Fast rotor");
		rotorLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		rotorPanel.add(rotorLabel1);

		fastBox = new JComboBox(rotorStrings);
		rotorPanel.add(fastBox);

		JLabel rotorLabel2 = new JLabel("Mid rotor");
		rotorPanel.add(rotorLabel2);

		midBox = new JComboBox(rotorStrings);
		rotorPanel.add(midBox);

		JLabel rotorLabel3 = new JLabel("Slow rotor");
		rotorPanel.add(rotorLabel3);

		slowBox = new JComboBox(rotorStrings);
		rotorPanel.add(slowBox);

		JLabel reflectorLabel = new JLabel("Reflector");
		rotorPanel.add(reflectorLabel);

		reflectorBox = new JComboBox(reflectorStrings);
		rotorPanel.add(reflectorBox);

		settingsPanel.add(rotorPanel, gbc_rotorPanel);

		JPanel rotorPanel2 = new JPanel();

		GridBagConstraints gbc_rotorPanel2 = new GridBagConstraints();
		gbc_rotorPanel2.insets = new Insets(0, 0, 5, 5);
		gbc_rotorPanel2.gridx = 0;
		gbc_rotorPanel2.gridy = 2;

		JLabel rotorLabel4 = new JLabel("Rotor positions");
		rotorPanel2.add(rotorLabel4);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rotorPanel2.add(rigidArea);

		JLabel rotorLabel5 = new JLabel("Fast");
		rotorPanel2.add(rotorLabel5);

		SpinnerNumberModel numModel1 = new SpinnerNumberModel(0, 0, 25, 1);
		SpinnerNumberModel numModel2 = new SpinnerNumberModel(0, 0, 25, 1);
		SpinnerNumberModel numModel3 = new SpinnerNumberModel(0, 0, 25, 1);
		SpinnerNumberModel numModel4 = new SpinnerNumberModel(0, 0, 25, 1);
		SpinnerNumberModel numModel5 = new SpinnerNumberModel(0, 0, 25, 1);
		SpinnerNumberModel numModel6 = new SpinnerNumberModel(0, 0, 25, 1);
		fastPosSpinner = new JSpinner(numModel1);
		rotorPanel2.add(fastPosSpinner);

		JLabel rotorLabel6 = new JLabel("Mid");
		rotorPanel2.add(rotorLabel6);

		midPosSpinner = new JSpinner(numModel2);
		rotorPanel2.add(midPosSpinner);

		JLabel rotorLabel7 = new JLabel("Slow");
		rotorPanel2.add(rotorLabel7);

		slowPosSpinner = new JSpinner(numModel3);
		rotorPanel2.add(slowPosSpinner);

		settingsPanel.add(rotorPanel2, gbc_rotorPanel2);

		JPanel rotorPanel3 = new JPanel();
		GridBagConstraints gbc_rotorPanel3 = new GridBagConstraints();
		gbc_rotorPanel3.insets = new Insets(0, 0, 5, 5);
		gbc_rotorPanel3.gridx = 0;
		gbc_rotorPanel3.gridy = 3;

		JLabel lblRotorRingSettings = new JLabel("Rotor ring settings");
		rotorPanel3.add(lblRotorRingSettings);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rotorPanel3.add(rigidArea_1);

		JLabel lblFast_1 = new JLabel("Fast");
		rotorPanel3.add(lblFast_1);

		fastRingSpinner = new JSpinner(numModel4);
		rotorPanel3.add(fastRingSpinner);

		JLabel lblMid = new JLabel("Mid");
		rotorPanel3.add(lblMid);

		midRingSpinner = new JSpinner(numModel5);
		rotorPanel3.add(midRingSpinner);

		JLabel lblSlow = new JLabel("Slow");
		rotorPanel3.add(lblSlow);

		slowRingSpinner = new JSpinner(numModel6);
		rotorPanel3.add(slowRingSpinner);

		settingsPanel.add(rotorPanel3, gbc_rotorPanel3);

		JPanel plugPanel = new JPanel();
		GridBagConstraints gbc_plugPanel = new GridBagConstraints();
		gbc_plugPanel.insets = new Insets(0, 0, 5, 5);
		gbc_plugPanel.gridx = 0;
		gbc_plugPanel.gridy = 4;

		presetRadio = new JRadioButton("Choose a plugboard preset");
		presetRadio.addItemListener(lfr);
		plugPanel.add(presetRadio);

		plugBox = new JComboBox(plugStrings);
		plugBox.setVisible(false);
		plugPanel.add(plugBox);

		settingsPanel.add(plugPanel, gbc_plugPanel);

		JPanel plugPanel2 = new JPanel();
		GridBagConstraints gbc_plugPanel2 = new GridBagConstraints();
		gbc_plugPanel2.insets = new Insets(0, 0, 5, 5);
		gbc_plugPanel2.gridx = 0;
		gbc_plugPanel2.gridy = 5;

		chooseRadio = new JRadioButton("Enter your own plugboard settings");
		chooseRadio.addItemListener(lfr);
		plugPanel2.add(chooseRadio);

		plugField = new JTextField();
		plugPanel2.add(plugField);
		plugField.setColumns(15);
		plugField.setVisible(false);

		settingsPanel.add(plugPanel2, gbc_plugPanel2);

		chooseSettingsButton = new JButton("Choose Settings");
		chooseSettingsButton.addActionListener(lfb);
		GridBagConstraints gbc_chooseSettingsButton = new GridBagConstraints();
		gbc_chooseSettingsButton.insets = new Insets(0, 0, 5, 5);
		gbc_chooseSettingsButton.gridx = 0;
		gbc_chooseSettingsButton.gridy = 6;
		settingsPanel.add(chooseSettingsButton, gbc_chooseSettingsButton);
		return settingsPanel;
	}

	//********************************************************************
	// Sets up and returns a panel for the user to encode/decode messages
	//********************************************************************
	private JPanel makeMessagePanel(){
		messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(500, 320));
		messagePanel.setLayout(gridBag);
		JPanel titlePanel = new JPanel();
		GridBagConstraints gbc_titlePanel = new GridBagConstraints();
		gbc_titlePanel.insets = new Insets(0, 0, 5, 5);
		gbc_titlePanel.gridx = 0;
		gbc_titlePanel.gridy = 0;

		JLabel messageTitleLabel = new JLabel("Encode/Decode a Message");
		titlePanel.add(messageTitleLabel);

		showInstructionsButton = new JButton("Instructions");
		showInstructionsButton.addActionListener(lfb);
		titlePanel.add(showInstructionsButton);

		showSettingsButton = new JButton("Settings");
		showSettingsButton.addActionListener(lfb);
		titlePanel.add(showSettingsButton);

		messagePanel.add(titlePanel, gbc_titlePanel);

		resetButton = new JButton("Reset Settings");
		resetButton.addActionListener(lfb);
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.insets = new Insets(0, 0, 5, 5);
		gbc_resetButton.gridx = 0;
		gbc_resetButton.gridy = 1;
		messagePanel.add(resetButton, gbc_resetButton);

		JPanel inputPanel = new JPanel();
		GridBagConstraints gbc_inputPanel = new GridBagConstraints();
		gbc_inputPanel.insets = new Insets(0, 0, 5, 5);
		gbc_inputPanel.gridx = 0;
		gbc_inputPanel.gridy = 2;

		JLabel inputLabel = new JLabel("Input");
		inputPanel.add(inputLabel);

		inputArea = new JTextArea(8, 30);
		inputArea.addKeyListener(lfk);
		inputArea.setLineWrap(true);
		inputPane = new JScrollPane(inputArea);
		inputPanel.add(inputPane);

		messagePanel.add(inputPanel, gbc_inputPanel);

		encodeButton = new JButton("Encode");
		encodeButton.addActionListener(lfb);
		GridBagConstraints gbc_encodeButton = new GridBagConstraints();
		gbc_encodeButton.insets = new Insets(0, 0, 5, 5);
		gbc_encodeButton.gridx = 0;
		gbc_encodeButton.gridy = 3;
		messagePanel.add(encodeButton, gbc_encodeButton);


		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new FlowLayout());
		GridBagConstraints gbc_outputPanel = new GridBagConstraints();
		gbc_outputPanel.insets = new Insets(0, 0, 5, 5);
		gbc_outputPanel.gridx = 0;
		gbc_outputPanel.gridy = 4;

		JLabel outputLabel = new JLabel("Output");
		outputPanel.add(outputLabel);

		outputArea = new JTextArea(8, 30);
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputPane = new JScrollPane(outputArea);
		outputPanel.add(outputPane);

		messagePanel.add(outputPanel, gbc_outputPanel);

		showKeyButton = new JButton("Key");
		showKeyButton.addActionListener(lfb);
		GridBagConstraints gbc_showKeyButton = new GridBagConstraints();
		gbc_showKeyButton.insets = new Insets(0, 0, 5, 5);
		gbc_showKeyButton.gridx = 0;
		gbc_showKeyButton.gridy = 5;
		messagePanel.add(showKeyButton, gbc_showKeyButton);

		return messagePanel;
	}

	//*******************************************************************************
	// Sets up and returns a panel that shows the settings a message was encoded with
	//*******************************************************************************
	private JPanel makeKeyPanel(){
		keyPanel = new JPanel();
		keyPanel.setLayout(gridBag);

		JLabel keyLabel = new JLabel("This message was encoded with the following settings:");
		GridBagConstraints gbc_keyLabel = new GridBagConstraints();
		gbc_keyLabel.insets = new Insets(0, 0, 8, 8);
		gbc_keyLabel.gridx = 0;
		gbc_keyLabel.gridy = 0;
		keyPanel.add(keyLabel, gbc_keyLabel);

		rotorsKeyLabel = new JLabel("Rotors:\t\tFast: 0 Mid: 0 Slow: 0 Reflector: B");
		GridBagConstraints gbc_rotorsKeyLabel = new GridBagConstraints();
		gbc_rotorsKeyLabel.insets = new Insets(0, 0, 8, 8);
		gbc_rotorsKeyLabel.gridx = 0;
		gbc_rotorsKeyLabel.gridy = 1;
		keyPanel.add(rotorsKeyLabel, gbc_rotorsKeyLabel);

		positionKeyLabel = new JLabel("Rotor starting positions:\t\tFast: 0 Mid: 0 Slow: 0");
		GridBagConstraints gbc_positionKeyLabel = new GridBagConstraints();
		gbc_positionKeyLabel.insets = new Insets(0, 0, 8, 8);
		gbc_positionKeyLabel.gridx = 0;
		gbc_positionKeyLabel.gridy = 2;
		keyPanel.add(positionKeyLabel, gbc_positionKeyLabel);

		ringKeyLabel = new JLabel("Rotor ring settings:\t\tFast: 0 Mid: 0 Slow: 0");
		GridBagConstraints gbc_ringKeyLabel = new GridBagConstraints();
		gbc_ringKeyLabel.insets = new Insets(0, 0, 8, 8);
		gbc_ringKeyLabel.gridx = 0;
		gbc_ringKeyLabel.gridy = 3;
		keyPanel.add(ringKeyLabel, gbc_ringKeyLabel);

		plugKeyLabel = new JLabel("Plugboard settings:\t\tABCDEFGHIJKLMNOPQRSTUVWXYZ");
		GridBagConstraints gbc_plugKeyLabel = new GridBagConstraints();
		gbc_plugKeyLabel.insets = new Insets(0, 0, 8, 8);
		gbc_plugKeyLabel.gridx = 0;
		gbc_plugKeyLabel.gridy = 4;
		keyPanel.add(plugKeyLabel, gbc_plugKeyLabel);

		closeButton = new JButton("Close");
		closeButton.addActionListener(lfb);
		GridBagConstraints gbc_closeButton = new GridBagConstraints();
		gbc_closeButton.insets = new Insets(0, 0, 8, 8);
		gbc_closeButton.gridx = 0;
		gbc_closeButton.gridy = 5;
		keyPanel.add(closeButton, gbc_closeButton);

		return keyPanel;
	}

	//********************************************************************
	// Private inner class responsible for handling button events
	//********************************************************************
	private class ButtonListener implements ActionListener{

		//********************************************************************
		// Listens for buttons being pressed and responds accordingly
		//********************************************************************
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == continueButton){
				cl.show(parent, "settings");
			}

			if(e.getSource() == chooseSettingsButton){
				int r1 = fastBox.getSelectedIndex() + 1;
				int r2 = midBox.getSelectedIndex() + 1;
				int r3 = slowBox.getSelectedIndex() + 1;
				char reflector;
				if(reflectorBox.getSelectedIndex() == 0)
					reflector = 'B';
				else
					reflector = 'C';

				enigma = new EnigmaMachine(r1, r2, r3, reflector);
				enigma.getFastRotor().setRotorPosition((int)fastPosSpinner.getValue());
				enigma.getMidRotor().setRotorPosition((int)midPosSpinner.getValue());
				enigma.getSlowRotor().setRotorPosition((int)slowPosSpinner.getValue());

				enigma.getFastRotor().setRingSetting((int)fastRingSpinner.getValue());
				enigma.getMidRotor().setRingSetting((int)midRingSpinner.getValue());
				enigma.getSlowRotor().setRingSetting((int)slowRingSpinner.getValue());

				if(presetRadio.isSelected())
					enigma.getPlugBoard().setConfig(plugBox.getSelectedIndex() + 1);
				if(chooseRadio.isSelected())
					enigma.getPlugBoard().setConfig(plugField.getText());

				rotorsKeyLabel.setText("Rotors:\t\tFast: " + fastBox.getSelectedItem() + "\t\tMid: " + midBox.getSelectedItem() + 
						"\t\tSlow: " + slowBox.getSelectedItem() +  "\t\tReflector: "+ reflectorBox.getSelectedItem());

				positionKeyLabel.setText("Rotor starting positions:\t\tFast: " + (int)fastPosSpinner.getValue() +
						"\t\tMid: " + (int)midPosSpinner.getValue() + "\t\tSlow: " + (int)slowPosSpinner.getValue());
				ringKeyLabel.setText("Rotor ring settings:\t\tFast: " + (int)fastRingSpinner.getValue() +
						"\t\tMid: " + (int)midRingSpinner.getValue() + "\t\tSlow: " + (int)slowRingSpinner.getValue());
				plugKeyLabel.setText("Plugboard settings:\t\t" + enigma.getPlugBoard().getConfig());

				cl.show(parent, "message");
			}

			if(e.getSource() == closeButton){
				cl.show(parent, "message");
			}

			if(e.getSource() == showKeyButton){
				cl.show(parent, "key");
			}

			if(e.getSource() == showSettingsButton){
				cl.show(parent, "settings");
			}

			if(e.getSource() == showInstructionsButton){
				cl.show(parent, "instructions");
			}

			if(e.getSource() == encodeButton){
				enigma.getFastRotor().setRotorPosition((int)fastPosSpinner.getValue());
				enigma.getMidRotor().setRotorPosition((int)midPosSpinner.getValue());
				enigma.getSlowRotor().setRotorPosition((int)slowPosSpinner.getValue());
				outputArea.setText(enigma.encodeString(inputArea.getText()));
			}

			if(e.getSource() == resetButton){
				enigma.getFastRotor().setRotorPosition((int)fastPosSpinner.getValue());
				enigma.getMidRotor().setRotorPosition((int)midPosSpinner.getValue());
				enigma.getSlowRotor().setRotorPosition((int)slowPosSpinner.getValue());
			}

		}

	}

	//*************************************************************************************
	// Private inner class responsible for handling actions performed on the radio buttons
	//*************************************************************************************
	private class RadioListener implements ItemListener{

		//********************************************************************
		// Listens for the radio buttons being toggled and responds accordingly
		//********************************************************************
		public void itemStateChanged(ItemEvent e) {
			if(e.getItemSelectable() == presetRadio){
				if(presetRadio.isSelected()){
					chooseRadio.setVisible(false);
					plugBox.setVisible(true);
				} 
				else {
					chooseRadio.setVisible(true);
					plugBox.setVisible(false);
				}
			}

			if(e.getItemSelectable() == chooseRadio){
				if(chooseRadio.isSelected()){
					presetRadio.setVisible(false);
					plugField.setVisible(true);
					plugField.setEditable(true);
				}
				else {
					presetRadio.setVisible(true);
					plugField.setVisible(false);
					plugField.setEditable(false);
				}

			}





		}
	}

	//*******************************************************************************
	// Private inner class responsible for handling actions performed by the keyboard
	//*******************************************************************************
	private class KeyBoardListener implements KeyListener{

		//********************************************************************
		// Listens for a key being pressed and responds accordingly
		//********************************************************************
		public void keyTyped(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				enigma.previousSettings();
				if(outputArea.getText().length() > 0)
					outputArea.setText(outputArea.getText().substring(0, outputArea.getText().length() - 2));
			} 
			else {
				outputArea.setText(outputArea.getText() + Character.toString(enigma.encodeChar(e.getKeyChar())));
			}
		}

		//********************************************************************
		// Method needed to implement interface
		//********************************************************************
		public void keyPressed(KeyEvent e) {	

		}

		//********************************************************************
		// Method needed to implement interface
		//********************************************************************
		public void keyReleased(KeyEvent e) {

		}

	}

}



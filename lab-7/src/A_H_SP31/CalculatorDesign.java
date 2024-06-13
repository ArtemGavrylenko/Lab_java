package A_H_SP31;
import A_H_SP31.ErrorHandler.InterpreterException;
import java.awt.*;
import java.awt.event.*;

public class CalculatorDesign extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TextField display;
    private Button[] numButtons;
    private Button addButton, subButton, mulButton, divButton, equButton, clrButton, powButton;
    private Button sinButton, cosButton, tanButton, ctgButton, openParenButton, closeParenButton;
    private Button memStoreButton, memRecallButton, memClearButton, memAddButton, delButton;

    private Parser parser;
    private double memory = 0; // To store the memory value

    public CalculatorDesign() {
        // Frame settings
        setLayout(new BorderLayout());
        setSize(400, 600);
        setTitle("Calculator");
        setResizable(true);

        // Initialize display
        display = new TextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24)); // Increase font size
        display.setPreferredSize(new Dimension(400, 100)); // Increase display height
        add(display, BorderLayout.NORTH);

        // Initialize number buttons
        numButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            numButtons[i] = new Button(String.valueOf(i));
            numButtons[i].setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size
            numButtons[i].addActionListener(this); // Add ActionListener to each number button
        }

        // Initialize operator buttons
        addButton = new Button("+");
        subButton = new Button("-");
        mulButton = new Button("*");
        divButton = new Button("/");
        equButton = new Button("=");
        clrButton = new Button("C");
        powButton = new Button("^");
        delButton = new Button("Del");

        Button[] operatorButtons = {addButton, subButton, mulButton, divButton, equButton, clrButton, powButton, delButton};
        for (Button button : operatorButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size
            button.addActionListener(this);
        }

        // Initialize trigonometric function buttons
        sinButton = new Button("Sin");
        cosButton = new Button("Cos");
        tanButton = new Button("Tan");
        ctgButton = new Button("Ctg");

        Button[] trigButtons = {sinButton, cosButton, tanButton, ctgButton};
        for (Button button : trigButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size
            button.addActionListener(this);
        }

        // Initialize parentheses buttons
        openParenButton = new Button("(");
        closeParenButton = new Button(")");

        Button[] parenButtons = {openParenButton, closeParenButton};
        for (Button button : parenButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size
            button.addActionListener(this);
        }

        // Initialize memory buttons
        memStoreButton = new Button("M");
        memRecallButton = new Button("MR");
        memClearButton = new Button("MC");
        memAddButton = new Button("M+");

        Button[] memoryButtons = {memStoreButton, memRecallButton, memClearButton, memAddButton};
        for (Button button : memoryButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size
            button.addActionListener(this);
        }

        // Layout for buttons
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(7, 4, 5, 5)); // Adjusted grid layout and spacing

        // Add buttons to the panel
        panel.add(numButtons[1]);
        panel.add(numButtons[2]);
        panel.add(numButtons[3]);
        panel.add(addButton);
        panel.add(numButtons[4]);
        panel.add(numButtons[5]);
        panel.add(numButtons[6]);
        panel.add(subButton);
        panel.add(numButtons[7]);
        panel.add(numButtons[8]);
        panel.add(numButtons[9]);
        panel.add(mulButton);
        panel.add(clrButton);
        panel.add(numButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        // Add trigonometric function buttons
        panel.add(sinButton);
        panel.add(cosButton);
        panel.add(tanButton);
        panel.add(ctgButton);

        // Add parentheses buttons
        panel.add(openParenButton);
        panel.add(closeParenButton);

        // Add power button
        panel.add(powButton);

        // Add memory buttons
        panel.add(memStoreButton);
        panel.add(memRecallButton);
        panel.add(memClearButton);
        panel.add(memAddButton);

        // Add delete button
        panel.add(delButton);

        add(panel, BorderLayout.CENTER);

        // Window closing event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();  // Close the window
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        if (command.equals("C")) {
            // Clear the display
            display.setText("");
        } else if (command.equals("Del")) {
            // Delete the last character
            String text = display.getText();
            if (!text.isEmpty()) {
                display.setText(text.substring(0, text.length() - 1));
            }
        } else if (command.equals("=")) {
            parser = new Parser();
            // Perform calculation
            try {
                double result = parser.evaluate(display.getText().toCharArray());
                display.setText(Double.toString(result));
            } catch (InterpreterException e) {
                display.setText("Error");
            }
        } else if (command.equals("M")) {
            // Store the current value in memory
            try {
                memory = Double.parseDouble(display.getText());
            } catch (NumberFormatException e) {
                display.setText("Error");
            }
        } else if (command.equals("MR")) {
            // Recall the value from memory
            display.setText(Double.toString(memory));
        } else if (command.equals("MC")) {
            // Clear the memory
            memory = 0;
        } else if (command.equals("M+")) {
            // Add the current value to memory
            try {
                memory += Double.parseDouble(display.getText());
            } catch (NumberFormatException e) {
                display.setText("Error");
            }
        } else {
            // Append the command to the display
            display.setText(display.getText() + command);
        }
    }
}

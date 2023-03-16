import javax.swing.*;

public class View {
    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JButton button;

    public View() {
        frame = new JFrame("MVC Example");
        label = new JLabel("Enter Data:");
        textField = new JTextField(20);
        button = new JButton("Submit");

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(button);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public String getData() {
        return textField.getText();
    }

    public void setData(String data) {
        JOptionPane.showMessageDialog(frame, data);
    }

    public JButton getButton() {
        return button;
    }
}
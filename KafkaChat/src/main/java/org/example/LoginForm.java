package org.example;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginForm extends JFrame {

    private static Set<String> topics = new HashSet<>(List.of("chat1", "chat2", "chat3"));
    private static Set<String> usersName = new HashSet<>();
    private JTextField textField1;
    private JTextField loginField;
    private JLabel loginLabe;
    private JPanel mainPanel;
    private JButton loginButton;
    private JLabel topicLabel;
    private JLabel LoginLabel;
    private JComboBox<String> topicsBox;

    public LoginForm() {
        setTitle("Zaloguj się do systemu");
        setSize(400, 200);

        setLocationRelativeTo(null);
        topics.forEach(topicsBox::addItem);

        loginButton.addActionListener(e -> {
            String login = loginField.getText();
            String topic = (String) topicsBox.getSelectedItem();

//            if (Broker.addToCurrentlyLoggedUsers(login)) {
                JOptionPane.showMessageDialog(this, "Zalogowano pomyślnie!");
                new ChatUI(topic, login);
//                Broker.getCurrentlyLoggedUsers();
                dispose();
//            } else {
//                JOptionPane.showMessageDialog(this, "Użytkownik o takim loginie jest już zalogowany", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
//            }
        });
        setContentPane(mainPanel);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}
package org.example;

import org.apache.kafka.clients.producer.ProducerRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;

public class ChatUI extends JFrame {
    private JList<String> loginList;
    private JTextField messageField;
    private JButton sendButton;
    private JPanel mainPanel;
    private JList<String> chatMessages;
    private ChatMessageRenderer chatMessageRenderer;


    private final MessageConsumer messageConsumer;


    public ChatUI(String topic, String login) throws HeadlessException {

        setContentPane(mainPanel);
        setTitle("Użytkownik - " + login);
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        messageConsumer = new MessageConsumer(topic, login);

        chatMessageRenderer = new ChatMessageRenderer();
        chatMessages.setCellRenderer(chatMessageRenderer);

        DefaultListModel<String> chatMessagesModel = new DefaultListModel<>();
        chatMessages.setModel(chatMessagesModel);

        Executors.newSingleThreadExecutor().submit(() -> {
            while(true) {
                messageConsumer.consumer.poll(Duration.of(1, ChronoUnit.SECONDS)).forEach(
                        m -> {
                            System.out.println("Odebrano wiadomość: " + m.value());
                            chatMessagesModel.addElement(m.value());
                        });
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    MessageProducer.send(new ProducerRecord<>(topic, login + ": " + message));
                }
            }

        });


    }

}

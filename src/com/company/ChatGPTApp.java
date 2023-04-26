package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatGPTApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ChatGPT App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextField textField = new JTextField();
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        ChatGPTApiClient chatGPTApiClient = new ChatGPTApiClient();

        textField.addActionListener(e -> {
            String question = textField.getText();
            try {
                String answer = chatGPTApiClient.askQuestion(question);
                textArea.append("Q: " + question + "\nA: " + answer + "\n\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            textField.setText("");
        });

        frame.setVisible(true);
    }
}

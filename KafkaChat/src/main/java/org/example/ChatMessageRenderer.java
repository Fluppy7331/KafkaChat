package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChatMessageRenderer extends JLabel implements ListCellRenderer<String> {

    private static Map<String, MyColor> userColorMap = new HashMap<>();

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        String[] parts = value.split(":", 2);
        String user = parts[0];
        String message = parts.length > 1 ? parts[1] : "";

        if (!userColorMap.containsKey(user)) {
            userColorMap.put(user, MyColor.generateColor());
        }
        MyColor color = userColorMap.get(user);
       setText("<html><font face='Roboto' size='6' color='" + color + "'>" + user + ":</font> <font size='6'>" + message + "</font></html>");
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setOpaque(true);

        return this;
    }
}
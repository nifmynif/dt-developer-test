package com.vizor.test;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public void run() {
        JFrame frame = new JFrame("DT Developer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }
}

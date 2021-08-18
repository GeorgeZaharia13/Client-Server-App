/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.server;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author George
 */
public class Client extends Thread{
    static Frame frame;
    static Label label1;
    static Label label2;
    static TextField textField;
    static TextField textField1;
    static TextField textField2;
    static Button button;
    static String rezultat;

    @Override
    public void run() {

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        frame = new Frame("Client - Server");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        frame.setLocation(x - 250, y - 200);
        frame.setSize(500, 400);
        frame.setBackground(Color.darkGray);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setLayout(layout);
        frame.setVisible(true);

        label1 = new Label("First number:");
        label1.setForeground(Color.white);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        frame.add(label1, gridBagConstraints);

        textField = new TextField();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        frame.add(textField, gridBagConstraints);

        label2 = new Label("Second Number:");
        label2.setForeground(Color.white);
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        frame.add(label2, gridBagConstraints);

        textField1 = new TextField();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        frame.add(textField1, gridBagConstraints);

        textField2 = new TextField();
        textField2.setBackground(Color.darkGray);
        textField2.setForeground(Color.yellow);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new Insets(5, 0, 0, 0);
        frame.add(textField2, gridBagConstraints);

        button = new Button("Result");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try (Socket soc = new Socket("localhost", 1050);
                     BufferedOutputStream bos = new BufferedOutputStream(soc.getOutputStream());) {

                    bos.write((textField.getText()).getBytes());
                    bos.write(("o").getBytes());
                    bos.write((textField1.getText()).getBytes());
                    bos.flush();
                } catch (IOException e) {
                }
            }

        });
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(20, 0, 0, 0);
        frame.add(button, gridBagConstraints);

        while (true) {
            try (ServerSocket sS = new ServerSocket(1051);
                 Socket soc2 = sS.accept();
                 BufferedReader bis = new BufferedReader(new InputStreamReader(soc2.getInputStream()));) {

                textField2.setText(bis.readLine());
            } catch (IOException ex) {
            }
        }
    }
}



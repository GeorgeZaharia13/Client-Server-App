/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.server;

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
public class Server extends Thread{
    static String rezultat;

    @Override
    public void run() {
        while (true) {
            try (ServerSocket ser = new ServerSocket(1050);
                 Socket soc = ser.accept();
                 BufferedReader bis = new BufferedReader(new InputStreamReader(soc.getInputStream()));) {

                String read = bis.readLine();

                double value1 = Double.parseDouble(read.split("o")[0]);
                double value2 = Double.parseDouble(read.split("o")[1]);
                rezultat = String.valueOf(value1 + value2);
            } catch (IOException e) {
            }

            try (Socket soc1 = new Socket("localhost", 1051);
                 BufferedOutputStream bos = new BufferedOutputStream(soc1.getOutputStream());) {
                bos.write(rezultat.getBytes());
            } catch (IOException e) {
            }
        }
    }
}



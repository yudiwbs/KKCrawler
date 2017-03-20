package edu.upi.cs.yudiwbs.kkcrawler;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yudiwbs on 15/03/2017.
 * untuk testing2
 */


public class Coba {

    public void passwordExample() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        console.printf("Testing password%n");
        char passwordArray[] = console.readPassword("Enter your secret password: ");
        console.printf("Password entered was: %s%n", new String(passwordArray));

    }

    public static void main(String[] args) {
        final String passwd;
        final String message = "Enter password";
        if( System.console() == null ) {
            final JPasswordField pf = new JPasswordField();
            passwd = JOptionPane.showConfirmDialog( null, pf, message,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION
                    ? new String( pf.getPassword() ) : "";
        } else
            passwd = new String( System.console().readPassword( "%s> ", message ) );
    }
}

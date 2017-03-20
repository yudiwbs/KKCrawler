package edu.upi.cs.yudiwbs.kkcrawler; /**
 * Created by dell on 20/03/2017.
 */
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *   hati2 nama dan sandi jangan terlihat untuk repo publik!
 */


public class ProxyAuth extends Authenticator {
    private String user, password;

    public ProxyAuth(String user, String password) {
        this.user = user;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}
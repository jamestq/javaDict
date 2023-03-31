package app;

import app.components.ClientWindow;

public class App {
    public static void main(String[] args) {
        ClientWindow newClient = new ClientWindow();
        newClient.initiate();
    }
}

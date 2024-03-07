package org.example;

import org.example.repositories.UserRepository;

public class Main {
    public static void main(String[] args) {

        App app = new App();
        app.run();

        UserRepository userRepository = new UserRepository();
    }
}
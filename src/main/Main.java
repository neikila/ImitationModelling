package main;

import model.Model;
import model.Settings;

/**
 * Created by neikila on 31.10.15.
 */
public class Main {
    public static void main(String[] args) {
        Settings settings = new Settings();
        Model model = new Model(settings);
        model.run();
    }
}

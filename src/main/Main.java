package main;

import analytics.Analytics;
import model.Model;
import model.Settings;

/**
 * Created by neikila on 31.10.15.
 */
public class Main {
    public static void main(String[] args) {
        Settings settings = new Settings();
        Analytics analytics = new Analytics();
        Model model = new Model(settings, analytics);
        model.run();
    }
}

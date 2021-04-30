package domain;

import java.util.ArrayList;

public class NotificationService {
    private ArrayList<SuperUser> receivers;

    public NotificationService(ArrayList<SuperUser> receivers) {
        this.receivers = receivers;
    }
}

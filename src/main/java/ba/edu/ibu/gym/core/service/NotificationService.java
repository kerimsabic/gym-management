package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.rest.websocket.MainSocketHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationService {

    private final MainSocketHandler mainSocketHandler;

    public NotificationService(MainSocketHandler mainSocketHandler) {
        this.mainSocketHandler = mainSocketHandler;
    }

    public void broadcastMessageToAll(String message) throws IOException {
        mainSocketHandler.broadcastMessageToAll(message);
    }


    public void sendMessage(String userId, String message) {
        mainSocketHandler.sendMessage(userId, message);
    }

}

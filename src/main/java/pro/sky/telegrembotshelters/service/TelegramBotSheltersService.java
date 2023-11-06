package pro.sky.telegrembotshelters.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrembotshelters.repository.ClientRepository;

@Service
public class TelegramBotSheltersService {

    private final ClientRepository clientRepository;

    public TelegramBotSheltersService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }



}

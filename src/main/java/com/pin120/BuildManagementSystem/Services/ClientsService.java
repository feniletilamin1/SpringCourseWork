package com.pin120.BuildManagementSystem.Services;

import com.pin120.BuildManagementSystem.Models.Client;
import com.pin120.BuildManagementSystem.Repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired
    private ClientsRepository clientsRepository;

    public void save(Client client){
        clientsRepository.save(client);
    }

    public List<Client> getAll(){
        return (List<Client>) clientsRepository.findAll();
    }

    public Optional<Client> getOneById(Long id) {
        return clientsRepository.findById(id);
    }

    public void deleteById(Long id) {
        clientsRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return clientsRepository.existsById(id);
    }

}

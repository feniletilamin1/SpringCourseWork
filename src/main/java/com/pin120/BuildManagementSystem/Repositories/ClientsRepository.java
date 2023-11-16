package com.pin120.BuildManagementSystem.Repositories;

import com.pin120.BuildManagementSystem.Models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends CrudRepository<Client,Long> {
}

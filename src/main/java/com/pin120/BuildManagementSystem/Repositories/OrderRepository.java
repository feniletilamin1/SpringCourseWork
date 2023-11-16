package com.pin120.BuildManagementSystem.Repositories;

import com.pin120.BuildManagementSystem.Models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}

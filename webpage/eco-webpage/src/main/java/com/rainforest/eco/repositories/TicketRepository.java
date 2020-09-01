package com.rainforest.eco.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> 
{
	List<Ticket> findByOwner(ObjectId owner);
}

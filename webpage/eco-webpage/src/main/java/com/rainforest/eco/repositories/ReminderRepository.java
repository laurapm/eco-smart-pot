package com.rainforest.eco.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rainforest.eco.models.Reminder;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder, String> 
{
	List<Reminder> findByTitle(String title);
}

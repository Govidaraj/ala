package com.GroDel.App.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.GroDel.App.Model.User1;


public interface StaffRepository extends MongoRepository<User1, String> {

	Optional<User1> findByName(String name);


}

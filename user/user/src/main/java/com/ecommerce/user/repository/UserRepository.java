package com.ecommerce.user.repository;

import com.ecommerce.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
//User -> Giving access to methods of User entity type
//Long -> Mention the type of the Primary key -> Since we are using MongoDB, this is String

    //We are using default mongodb functions


}

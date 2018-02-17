package microblog.mongo.repository;


import microblog.mongo.dal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);
    User findById(String id);
}


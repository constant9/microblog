package microblog.mongo.repository;


import microblog.mongo.dal.model.User;
import microblog.mongo.dal.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface PostRepository extends MongoRepository<Post, String> {
	Post findById(String id);
	List<Post> findByCreator(String id);
}


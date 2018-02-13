package microblog.mongo.repository;


import microblog.mongo.dal.model.User;
import microblog.mongo.dal.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface PostRepository extends MongoRepository<Post, Integer> {

}


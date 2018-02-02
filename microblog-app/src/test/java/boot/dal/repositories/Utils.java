package boot.dal.repositories;

import boot.dal.model.Post;
import boot.dal.model.User;

import java.util.Date;
import java.util.Random;

public class Utils {
    private static Random random = new Random();
    public static User createUser(UserRepository userRepository){
        return userRepository.save(new User().setName("user_" + random.nextInt()));
    }

    public static Post createPost(User user, PostRepository postRepository){
        int i = random.nextInt();
        Post post = new Post()
                .setText("text" + i)
                .setSubject("subject_" + i)
                .setCreator(user);
        return postRepository.save(post);
    }

}

package boot.dal.repositories;

import boot.MicroblogBoot;
import boot.dal.model.Post;
import boot.dal.model.User;
import boot.dal.model.Vote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@SpringBootTest(classes = MicroblogBoot.class)
@TestPropertySource(locations="classpath:test.app.properties")
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreate(){
        User userCreator = Utils.createUser(userRepository);
        Post post = Utils.createPost(userCreator, postRepository);
        User userVoter = Utils.createUser(userRepository);

        Vote vote = new Vote(1, userVoter, post);
        Vote save = voteRepository.save(vote);
        assertNotNull(save);
        assertNotNull(save.getUser());
        assertNotNull(save.getPost());
        assertEquals(userVoter, save.getUser());
        assertEquals(post, save.getPost());
    }

    @Test
    public void testScoresCount(){
        User userCreator = Utils.createUser(userRepository);
        Post post = Utils.createPost(userCreator, postRepository);
        int upVotes = 11;
        int downVotes = 3;

        User userVoter = null;
        Vote save = null;
        for (int i=0; i< upVotes;i++) {
            userVoter = Utils.createUser(userRepository);
            Vote vote = new Vote(1, userVoter, post);
            save = voteRepository.save(vote);
        }
        for (int i=0; i< downVotes;i++) {
            userVoter = Utils.createUser(userRepository);
            Vote vote = new Vote(-1, userVoter, post);
            save = voteRepository.save(vote);
        }
        int sumScore = voteRepository.sumScore(post.getId());
        assertEquals(upVotes - downVotes, sumScore);

    }
}
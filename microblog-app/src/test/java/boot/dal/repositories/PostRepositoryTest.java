package boot.dal.repositories;

import boot.MicroblogBoot;
import boot.dal.model.Post;
import boot.dal.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@SpringBootTest(classes = MicroblogBoot.class)
@TestPropertySource(locations="classpath:test.app.properties")
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreate(){
        Post post = new Post()
                .setText("sdf")
                .setSubject("ddd")
                .setCreator(createUser());
        Post save = postRepository.save(post);
        assertNotNull(save);
    }

    private User createUser(){
        return userRepository.save(new User().setName("user_" + new Date().getTime()/1000));
    }



}
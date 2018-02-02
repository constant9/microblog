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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@SpringBootTest(classes = MicroblogBoot.class)
@TestPropertySource(locations="classpath:test.app.properties")
public class UserRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreate(){
        User saved = userRepository.save(new User().setName("k"));
        assertNotNull(saved);
    }

    @Test
    public void testFindByName(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add(userRepository.save(new User().setName("user_" + i)));
        }
        userRepository.save(users);

        for (User user : users) {
            List<User> byName = userRepository.findByName(user.getName());
            assertNotNull(byName);
            assertTrue(byName.size() == 1);
            assertEquals(user.getName(), byName.get(0).getName());
        }
    }


}
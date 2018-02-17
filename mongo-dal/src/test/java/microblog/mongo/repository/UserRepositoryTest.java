package microblog.mongo.repository;

import microblog.mongo.dal.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

public class UserRepositoryTest {
	static final Random random = new Random();
	@Autowired
	UserRepository userRepository;

	@Test
	public void create(){
		User user = new User();
		user.setName("user_name_" + random.nextInt()).setCreationDate(new Date());
		User save = userRepository.save(user);

		User one = userRepository.findOne( Example.of(new User().setId(save.getId())));
		assertNotNull(one);
	}

	@Test
	public void findByName() {
		User user = new User();
		user.setName("user_name_" + random.nextInt());
		User save = userRepository.save(user);

		User one = userRepository.findByName(user.getName());
		assertNotNull(one);
	}

	@Test(expected = org.springframework.dao.DuplicateKeyException.class)
	public void testUniqueNameUser() {
		User user = new User();
		String name = "user_name_" + random.nextInt();
		user.setName(name);
		User save = userRepository.save(user);

		User one = userRepository.findByName(user.getName());
		assertNotNull(one);

		//create another user with same name
		User user2 = new User();
		user2.setName(name);
		User save2 = userRepository.save(user2);
	}
}
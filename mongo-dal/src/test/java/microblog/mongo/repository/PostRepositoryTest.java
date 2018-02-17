package microblog.mongo.repository;

import microblog.mongo.MongoBoot;
import microblog.mongo.dal.model.Post;
import microblog.mongo.dal.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoBoot.class)
public class PostRepositoryTest {

	static final Random random = new Random();
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	private User user;

	@Before
	public void createUser(){
		String postfix = "" + random.nextInt();
		User user1 = getUser(postfix);
		user = user1;
	}

	@Test
	public void create(){
		String postfix = "" + random.nextInt();
		Date creationDate = new Date();
		Post post  = new Post().setCreationDate(creationDate)
				.setCreator(user)
				.setSubject("subject_" + postfix)
				.setText("text_" + postfix)
				.setUpdateDate(creationDate);

		Post save = postRepository.save(post);
		Post one = postRepository.findOne(Example.of(new Post()
														.setId(save.getId()).setVoteNegativeScore(0).setVotePositiveScore(0).setCreationDate(null).setUpdateDate(null)));
		assertNotNull(one);

		user.getPosts().add(post);
		userRepository.save(user);
		User one1 = userRepository.findByName(user.getName());
		assertNotNull(one1);
		List<Post> posts = one1.getPosts();
		assertTrue(!posts.isEmpty());
		assertEquals(posts.get(0).getId(), one.getId());

		List<Post> byCreator = postRepository.findByCreator(user.getId());
		assertTrue(!byCreator.isEmpty());


	}

	@Test
	public void getBySubjectUsingQueryByExample() {
		String postfix = "" + random.nextInt();
		String subject = "subject_" + postfix;
		Post post  = new Post().setCreator(user)
				.setSubject(subject)
				.setText("text_" + postfix);

		Post save = postRepository.save(post);

		Post examplePost = new Post().setSubject(subject);
		ExampleMatcher matcher = ExampleMatcher.matching()
						.withMatcher("subject", ExampleMatcher.GenericPropertyMatcher::exact)
						.withIgnorePaths("creationDate","updateDate","votePositiveScore","voteNegativeScore")
						.withIgnoreNullValues();
		Example<Post> postExample = Example.of(examplePost, matcher);
		List<Post> posts = postRepository.findAll(postExample);
		assertTrue(!posts.isEmpty());
		assertEquals(save.getId(), posts.get(0).getId());
	}

	private User getUser(String postfix) {
		User user = new User();
		user.setName("user_name_" + postfix).setCreationDate(new Date());
		return userRepository.save(user);
	}


}
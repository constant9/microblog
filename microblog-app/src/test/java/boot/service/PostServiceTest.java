package boot.service;

import boot.MicroblogBoot;
import boot.api.rest.dto.PostDto;
import boot.dal.model.Post;
import boot.dal.model.User;
import boot.dal.repositories.PostRepository;
import boot.dal.repositories.UserRepository;
import boot.dal.repositories.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@SpringBootTest(classes = MicroblogBoot.class)
@TestPropertySource(locations="classpath:test.app.properties")
public class PostServiceTest {

	@Autowired
	PostService postService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;

	@Test
	public void getTopList() {
		List<PostDto> topList = postService.getTopList();
		assertNotNull(topList);
	}

	@Test
	public void create() {
		User user = Utils.createUser(userRepository);
		PostDto postDto = postService.create("subj1", "text1", user.getName());
		Post post = postRepository.findOne(postDto.getId());
		assertNotNull(post);
		assertEquals(post.getId(),postDto.getId());
		assertEquals(post.getSubject(),postDto.getSubject());
		assertEquals(post.getText(),postDto.getText());
		assertEquals(post.getVotePositiveScore(),postDto.getPositiveScore());
	}

	@Test
	public void update() {
		User user = Utils.createUser(userRepository);
		PostDto postDto = postService.create("subj2", "text2", user.getName());
		Post post = postRepository.findOne(postDto.getId());
		PostDto update = postService.update("aaa", "bbb", post.getId());

		assertNotEquals(postDto, update);
		assertEquals(postDto.getId(),postDto.getId());
		assertNotEquals(postDto.getSubject(),update.getSubject());
		assertNotEquals(postDto.getText(), update.getText());
		assertEquals(postDto.getPositiveScore(),postDto.getPositiveScore());
	}

	@Test
	public void get() {
		User user = Utils.createUser(userRepository);
		PostDto postDto = postService.create("subj2", "text2", user.getName());
		PostDto post2 = postService.get(postDto.getId());
		assertEquals(postDto.getId(),post2.getId());
	}

	@Test
	public void refreshTopList() {
	}

	@Test
	public void onApplicationEvent() {
	}
}
package boot.api.rest;

import boot.MicroblogBoot;
import boot.api.rest.dto.CreatePostDto;
import boot.api.rest.dto.PostDto;
import boot.api.rest.dto.UpdatePostDto;
import boot.api.rest.dto.UserDto;
import boot.dal.model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@SpringBootTest(classes = MicroblogBoot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.app.properties")
public class PostControllerTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers;

	@Before
	public void init(){
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	public final String POSTS = "/posts/";
	public final String USERS = "/users/";
	public static Random random = new Random();


	private String createURLWithPort(String uri) {
		return "http://localhost:" + port +"/microblog"+ uri;
	}

	private String upvoteQuery(String user, int postId) {
		return createURLWithPort(USERS + user + "/upvote/" + postId);
	}
	private String downvoteQuery(String user, int postId) {
		return createURLWithPort(USERS + user + "/downvote/" + postId);
	}

	@Test
	public void createUserTest() {
		String user1 = "userTest";
		ResponseEntity<UserDto> userResp = restTemplate.postForEntity(
				createURLWithPort(USERS + user1), null, UserDto.class);

		assertEquals(HttpStatus.OK, userResp.getStatusCode());
		assertNotNull(userResp.getBody());
		assertEquals(user1, userResp.getBody().getName());
	}

	@Test
	public void createPost() {
		UserDto user = createUser("testPost");

		ResponseEntity<PostDto> postResp = restTemplate.postForEntity(
				createURLWithPort(POSTS), new CreatePostDto("subj1","text1",user.getName()), PostDto.class);
		assertEquals(HttpStatus.OK, postResp.getStatusCode());
		assertNotNull(postResp.getBody());
		assertEquals(user.getName(), postResp.getBody().getUserName());
		assertEquals("subj1", postResp.getBody().getSubject());
		assertEquals("text1", postResp.getBody().getText());
		assertEquals(0, postResp.getBody().getPositiveScore());
	}

	@Test
	public void updatePost() {
		UserDto user = createUser("testUpdatePost");

		ResponseEntity<PostDto> postResp = restTemplate.postForEntity(
				createURLWithPort(POSTS), new CreatePostDto("subj1","text1",user.getName()), PostDto.class);
		assertEquals(HttpStatus.OK, postResp.getStatusCode());

		HttpEntity<UpdatePostDto> entity = new HttpEntity<>(new UpdatePostDto("newSubj1","newText1"), headers);
		ResponseEntity<PostDto> putResp = restTemplate.exchange(
				createURLWithPort(POSTS + postResp.getBody().getId()),
				HttpMethod.PUT, entity, PostDto.class);

		assertEquals(HttpStatus.OK, putResp.getStatusCode());
		assertNotNull(putResp.getBody());
		assertEquals(user.getName(), putResp.getBody().getUserName());
		assertEquals("newSubj1", putResp.getBody().getSubject());
		assertEquals("newText1", putResp.getBody().getText());
		assertEquals(0, putResp.getBody().getPositiveScore());
	}

	@Test
	public void getPost() {
		UserDto user = createUser("testGetPost");
		ResponseEntity<PostDto> postResp = restTemplate.postForEntity(
				createURLWithPort(POSTS), new CreatePostDto("subj1","text1",user.getName()), PostDto.class);
		assertEquals(HttpStatus.OK, postResp.getStatusCode());

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<PostDto> response = restTemplate.exchange(
				createURLWithPort(POSTS + postResp.getBody().getId() ),
				HttpMethod.GET, entity, PostDto.class);
		assertEquals(HttpStatus.OK, postResp.getStatusCode());
		assertNotNull(postResp.getBody());
		assertEquals(user.getName(), postResp.getBody().getUserName());
		assertEquals("subj1", postResp.getBody().getSubject());
		assertEquals("text1", postResp.getBody().getText());
		assertEquals(0, postResp.getBody().getPositiveScore());
	}

	@Test
	public void getNotFound() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort(POSTS + "12344" ),
				HttpMethod.GET, entity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void getTop() {
		UserDto user = createUser("testVote1_" + random.nextInt(30));
		UserDto user2 = createUser("testVote2_" + random.nextInt(30));
		ResponseEntity<PostDto> postResp = restTemplate.postForEntity(
				createURLWithPort(POSTS), new CreatePostDto("subj " + random.nextInt(),"text " + random.nextInt() ,user.getName()), PostDto.class);
		assertEquals(HttpStatus.OK, postResp.getStatusCode());
		PostDto postDto = postResp.getBody();
		ResponseEntity<ExceptionHadler.Details> voteResp = restTemplate.postForEntity(
				upvoteQuery(user.getName(),postDto.getId()), null, ExceptionHadler.Details.class);
		assertEquals(HttpStatus.ACCEPTED, voteResp.getStatusCode());
		ResponseEntity<PostDto> post = restTemplate.getForEntity(createURLWithPort(POSTS + postDto.getId() ), PostDto.class);
		assertEquals(1, post.getBody().getPositiveScore());

		//try second time and fail
		ResponseEntity<ExceptionHadler.Details> voteRespTry2 = restTemplate.postForEntity(
				upvoteQuery(user.getName(),postDto.getId()), null, ExceptionHadler.Details.class);
		assertEquals(HttpStatus.CONFLICT, voteRespTry2.getStatusCode());

		//downvote by user2
		ResponseEntity<PostDto> voteRespUser2 = restTemplate.postForEntity(
				downvoteQuery(user2.getName(),postDto.getId()), null, PostDto.class);
		assertEquals(HttpStatus.ACCEPTED, voteRespUser2.getStatusCode());
		post = restTemplate.getForEntity(createURLWithPort(POSTS + postDto.getId() ), PostDto.class);
		assertEquals(1, post.getBody().getPositiveScore());

		//create new post and upvote it by both
		ResponseEntity<PostDto> postResp2 = restTemplate.postForEntity(
				createURLWithPort(POSTS), new CreatePostDto("subj " + random.nextInt(),"text " + random.nextInt() ,user.getName()), PostDto.class);
		ResponseEntity<PostDto> voteRespUser1post2 = restTemplate.postForEntity(
				upvoteQuery(user.getName(),postResp2.getBody().getId()), null, PostDto.class);
		assertEquals(HttpStatus.ACCEPTED, voteRespUser1post2.getStatusCode());
		ResponseEntity<PostDto> voteRespUser2post2 = restTemplate.postForEntity(
				upvoteQuery(user2.getName(),postResp2.getBody().getId()), null, PostDto.class);
		assertEquals(HttpStatus.ACCEPTED, voteRespUser2post2.getStatusCode());
		post = restTemplate.getForEntity(createURLWithPort(POSTS + postResp2.getBody().getId() ), PostDto.class);
		assertEquals(2, post.getBody().getPositiveScore());

		//calc top posts
		ResponseEntity<PostDto[]> response = restTemplate.getForEntity(createURLWithPort(POSTS + "top" ), PostDto[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().length>0);
	}

	private UserDto createUser(String name) {
		ResponseEntity<UserDto> userResp = restTemplate.postForEntity(
				createURLWithPort(USERS + name + "_" + random.nextInt(999) ), null, UserDto.class);
		return userResp.getBody();
	}
}
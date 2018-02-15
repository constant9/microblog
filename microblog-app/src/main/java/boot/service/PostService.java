package boot.service;

import boot.api.rest.VoteEvent;
import boot.api.rest.dto.NotFoundErrorResponse;
import boot.api.rest.dto.PostDto;
import boot.dal.model.Post;
import boot.dal.model.User;
import boot.dal.repositories.PostRepository;
import boot.dal.repositories.UserRepository;
import boot.dal.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class PostService implements ApplicationListener<VoteEvent> {

	@Value("${posts.top.list.size}")
	private	int topPostsListSize;
	@Value("${posts.top.list.refresh.millis}")
	private	long topPostsListRefresh;

	AtomicInteger atomicInteger = new AtomicInteger();

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VoteRepository votesRepository;

	private List<PostDto> topList = new ArrayList<>();
	private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

	@PostConstruct
	private void init(){
		//scheduledThreadPoolExecutor.scheduleWithFixedDelay(()->refreshTopList(), 0L, topPostsListRefresh, TimeUnit.MILLISECONDS);
	}

	public List<PostDto> getTopList(){
		return topList;
	}


	public PostDto create(String subject, String text, String userName){
		List<User> users = userRepository.findByName(userName);
		if(users.isEmpty())
			throw new NotFoundErrorResponse("user " + userName + " does not exist.");

		Post post = new Post();
		post.setCreator(users.get(0))
				.setSubject(subject)
				.setText(text);

		Post saveResult = postRepository.save(post);
		return toDto(saveResult)
				.setUserName(userName);
	}


	public PostDto update(String subject, String text, int postId){
		Post post = postRepository.findOne(postId);
		if(post == null)
			throw new NotFoundErrorResponse("post #" + postId + " does not exist.");

		if(subject!= null && subject.length() > 0)
			post.setSubject(subject);
		if(text != null && text.length() > 0)
			post.setText(text);

		Post saveResult = postRepository.save(post.setUpdateDate(new Date()));
		return toDto(saveResult).setUserName(saveResult.getCreator().getName());
	}

	public PostDto get(int postId){
		Post post = postRepository.findOne(postId);
		if(post == null)
			throw new NotFoundErrorResponse("post #" + postId + " does not exist.");
		//Integer score = Optional.ofNullable(votesRepository.sumScore(postId)).orElse(0);
		return toDto(post)
				.setUserName(post.getCreator().getName());
	}


	public void refreshTopList(){
		try {
			List<Post> topPosts = postRepository.selectTopPosts(topPostsListSize);
			this.topList = topPosts.stream().map(PostService::toDto).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onApplicationEvent(VoteEvent voteEvent) {
		int addAndGet = atomicInteger.addAndGet(1);
		if(addAndGet%topPostsListSize==0)
			refreshTopList();
	}

	private static PostDto toDto(Post post){
		return new PostDto().setCreationDate(post.getCreationDate())
				.setId(post.getId()).setSubject(post.getSubject())
				.setText(post.getText()).setCreationDate(post.getCreationDate())
				.setUpdateDate(post.getUpdateDate())
				.setPositiveScore(post.getVotePositiveScore());
	}

}

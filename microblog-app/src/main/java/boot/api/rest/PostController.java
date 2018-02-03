package boot.api.rest;

import boot.api.rest.dto.CreatePostDto;
import boot.api.rest.dto.NotFoundErrorResponse;
import boot.api.rest.dto.PostDto;
import boot.api.rest.dto.UpdatePostDto;
import boot.dal.model.Post;
import boot.dal.model.User;
import boot.dal.repositories.PostRepository;
import boot.dal.repositories.UserRepository;
import boot.dal.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path="/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository votesRepository;

    @RequestMapping(method = RequestMethod.POST)
    public PostDto create(@RequestBody CreatePostDto createPostDto){
        List<User> users = userRepository.findByName(createPostDto.getUserName());
        if(users.isEmpty())
            throw new NotFoundErrorResponse("user " + createPostDto.getUserName() + " does not exist.");

        Post post = new Post();
        post.setCreator(users.get(0))
                .setSubject(createPostDto.getSubject())
                .setText(createPostDto.getText());

        Post saveResult = postRepository.save(post);
        return toDto(saveResult)
                .setUserName(createPostDto.getUserName());
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{postId}")
    public PostDto update(@RequestBody UpdatePostDto updatePostDto, @PathVariable int postId){
        Post post = postRepository.findOne(postId);
        if(post == null)
            throw new NotFoundErrorResponse("post #" + postId + " does not exist.");

        if(updatePostDto.getSubject() != null && updatePostDto.getSubject().length() > 0)
            post.setSubject(updatePostDto.getSubject());
        if(updatePostDto.getText() != null && updatePostDto.getText().length() > 0)
            post.setText(updatePostDto.getText());

        Post saveResult = postRepository.save(post.setUpdateDate(new Date()));
        return toDto(saveResult).setUserName(saveResult.getCreator().getName());
    }

    @RequestMapping(method = RequestMethod.GET, value="/{postId}")
    public PostDto get(@PathVariable int postId){
        Post post = postRepository.findOne(postId);
        if(post == null)
            throw new NotFoundErrorResponse("post #" + postId + " does not exist.");
        //Integer score = Optional.ofNullable(votesRepository.sumScore(postId)).orElse(0);
        return toDto(post)
                .setUserName(post.getCreator().getName());
    }

    private static PostDto toDto(Post post){
        return new PostDto().setCreationDate(post.getCreationDate())
                .setId(post.getId()).setSubject(post.getSubject())
                .setText(post.getText()).setCreationDate(post.getCreationDate())
                .setUpdateDate(post.getUpdateDate())
				.setPositiveScore(post.getVotePositiveScore());
    }
}

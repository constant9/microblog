package boot.api.rest;

import boot.api.rest.dto.CreatePostDto;
import boot.api.rest.dto.PostDto;
import boot.api.rest.dto.UpdatePostDto;
import boot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping(path="/posts")
public class PostController {

    @Autowired
	private PostService postService;

    @RequestMapping(method = RequestMethod.POST)
    public PostDto create(@RequestBody CreatePostDto createPostDto){
    	return postService.create(createPostDto.getSubject(), createPostDto.getText(), createPostDto.getUserName());
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{postId}")
    public PostDto update(@RequestBody UpdatePostDto updatePostDto, @PathVariable int postId){
     	return postService.update(updatePostDto.getSubject(), updatePostDto.getText(), postId);
     }

    @RequestMapping(method = RequestMethod.GET, value="/{postId}")
    public PostDto get(@PathVariable int postId){
    	return postService.get(postId);
    }

    @RequestMapping(method = RequestMethod.GET, value="/top")
    public List<PostDto> getTop(){
		List<PostDto> topList = postService.getTopList();
        return topList==null ? new ArrayList<>() : topList;
    }
}

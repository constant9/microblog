package boot.api.rest;

import boot.api.rest.dto.AlreadyExitsErrorResponse;
import boot.api.rest.dto.NotFoundErrorResponse;
import boot.api.rest.dto.UserDto;
import boot.dal.model.User;
import boot.dal.repositories.UserRepository;
import boot.dal.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private EntityManagerFactory emFactory;

    @RequestMapping(method = RequestMethod.POST, value="/{userName}")
    public UserDto update(@PathVariable String userName){
        try{
			User user = userRepository.save(new User().setName(userName));
			return toDto(user);
		}catch (DataIntegrityViolationException e)
		{
			throw new AlreadyExitsErrorResponse("User " + userName + "already exists");
		}
    }

	@RequestMapping(method = RequestMethod.POST, value="/{userName}/upvote/{postId}")
	public ResponseEntity<ExceptionHadler.Details> upvote(@PathVariable @NotNull String userName, @PathVariable int postId){
		return vote(userName, postId, 1);
	}
	@RequestMapping(method = RequestMethod.POST, value="/{userName}/downvote/{postId}")
	public ResponseEntity<ExceptionHadler.Details> downvote(@PathVariable @NotNull String userName, @PathVariable int postId){
		return vote(userName, postId, -1);
	}

    @RequestMapping(method = RequestMethod.GET, value="/{userName}")
    public UserDto get(@PathVariable String userName){
        List<User> users = userRepository.findByName(userName);
		User user = validateFindResults(userName, users);
		return toDto(user);
    }

    //this method is ugly because it uses a native sql in order to try to insert vote and let the DB fail in case of violation
	private ResponseEntity<ExceptionHadler.Details> vote(String userName, int postId, int voteScore){
		EntityManager entityManager = emFactory.createEntityManager();
    	try{
			//Post post = emFactory.createEntityManager().getReference(Post.class, postId);
			//User byName = validateFindResults(userName , userRepository.findByName(userName));
			//Vote vote = new Vote(voteScore, byName, post);

			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			int i = entityManager.createNativeQuery("insert into votes (post_id, user_id, score) values (:pid, (select id from users where name=:uname) ,:scr);")
					.setParameter("pid", postId)
					.setParameter("uname", userName)
					.setParameter("scr", voteScore)
					.executeUpdate();
			//voteRepository.save(vote);
			transaction.commit();
			return new ResponseEntity<>(new ExceptionHadler.Details("Vote saved"), HttpStatus.ACCEPTED);
		}catch (/*DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException*/Exception e)
		{
			throw new AlreadyExitsErrorResponse("User`s " + userName+ " vote wasn`t saved for post " + postId);
		}finally {
    		entityManager.close();
		}
	}

	private User validateFindResults(String userName, List<User> users) {
		if(users == null || users.size() == 0)
			throw new NotFoundErrorResponse("user " + userName + " does not exist.");
		return users.get(0);
	}

	private static UserDto toDto(User user){
        return new UserDto().setId(user.getId()).setName(user.getName());
    }
}

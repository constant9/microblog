package boot.dal.repositories;

import boot.dal.model.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
	@Query(value = "select\n" +
			"\tp.*,\n" +
			"    vote_positive_score/(time_to_sec(timediff(now(), creation_date))/60) + \n" +
			"    (\n" +
			"\t\t(select sum(score) from votes v " +
			"		where " +
			"		v.post_id = p.id and score > 0 " +
			"		and v.creation_date > p.update_date)/(time_to_sec(timediff(now(), update_date))/60)\n" +
			"\t	) rating \n" +
			"from posts p\n" +
			"where vote_positive_score > 0 \n" +
			"order by rating desc \n" +
			"limit ?1", nativeQuery = true)
	List<Post> selectTopPosts(int topPostsListSize);
}

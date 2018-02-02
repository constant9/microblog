package boot.dal.repositories;

import boot.dal.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Integer> {
    @Query(value = "select sum(score) from votes where post_id = ?1", nativeQuery = true)
    Integer sumScore(int postId);
}

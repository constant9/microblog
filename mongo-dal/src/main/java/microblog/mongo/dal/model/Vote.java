package microblog.mongo.dal.model;


import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.util.Date;


public class Vote  implements Serializable {

    private int score;
	@CreationTimestamp
    private Date creationDate = new Date();

    private int postId;

    protected Vote(){ }

    public Vote(int score, int postId) {
        this.score = score;
        this.postId = postId;

    }

    //region ... setters/getters

    public int getScore() {
        return score;
    }

    public Vote setScore(int score) {
        this.score = score;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Vote setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

	public int getPostId() {
		return postId;
	}

	public Vote setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	//endregion

    //region ...hash-equals-tostring
    @Override
    public String toString() {
        return "Vote{" +
                " score=" + score +
                ", creationDate=" + creationDate +
                ", postId=" + postId +
                '}';
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Vote))
			return false;
		Vote vote = (Vote) o;
		return getScore() == vote.getScore() &&
				getPostId() == vote.getPostId() &&
				com.google.common.base.Objects.equal(getCreationDate(), vote.getCreationDate());
	}

	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(getScore(), getCreationDate(), getPostId());
	}
	//endregion


}

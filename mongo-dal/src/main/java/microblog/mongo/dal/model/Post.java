package microblog.mongo.dal.model;


import com.google.common.base.MoreObjects;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.Id;
import java.util.Date;


@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private int votePositiveScore;
    private int voteNegativeScore;
    private String subject;
    private String text;
	@CreationTimestamp
    private Date creationDate = new Date();
	@UpdateTimestamp
    private Date updateDate = new Date();

    @DBRef
    private User creator;

    //region ... setters/getters

	public String getId() {
		return id;
	}

	public Post setId(String id) {
		this.id = id;
		return this;
	}

	public int getVotePositiveScore() {
		return votePositiveScore;
	}

	public Post setVotePositiveScore(int votePositiveScore) {
		this.votePositiveScore = votePositiveScore;
		return this;
	}

	public int getVoteNegativeScore() {
		return voteNegativeScore;
	}

	public Post setVoteNegativeScore(int voteNegativeScore) {
		this.voteNegativeScore = voteNegativeScore;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public Post setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getText() {
		return text;
	}

	public Post setText(String text) {
		this.text = text;
		return this;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Post setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Post setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
		return this;
	}

	public User getCreator() {
		return creator;
	}

	public Post setCreator(User creator) {
		this.creator = creator;
		return this;
	}


	//endregion

	//region ...equals-tostring-hash
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("votePositiveScore", votePositiveScore)
				.add("voteNegativeScore", voteNegativeScore)
				.add("subject", subject)
				.add("text", text)
				.add("creationDate", creationDate)
				.add("updateDate", updateDate)
				.add("creator", creator)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Post))
			return false;
		Post post = (Post) o;
		return getId() == post.getId() &&
				getVotePositiveScore() == post.getVotePositiveScore() &&
				getVoteNegativeScore() == post.getVoteNegativeScore() &&
				com.google.common.base.Objects.equal(getSubject(), post.getSubject()) &&
				com.google.common.base.Objects.equal(getText(), post.getText()) &&
				com.google.common.base.Objects.equal(getCreationDate(), post.getCreationDate()) &&
				com.google.common.base.Objects.equal(getUpdateDate(), post.getUpdateDate()) &&
				com.google.common.base.Objects.equal(getCreator(), post.getCreator());
	}

	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(getId(), getVotePositiveScore(), getVoteNegativeScore(), getSubject(), getText(), getCreationDate(), getUpdateDate(), getCreator());
	}
	//endregion
}

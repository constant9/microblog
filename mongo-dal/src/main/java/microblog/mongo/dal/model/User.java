package microblog.mongo.dal.model;


//import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @CreationTimestamp
	@Field("creation_date")
    private Date creationDate;

    @DBRef
    private List<Post> posts = new ArrayList<>();

    private List<Vote> votes = new ArrayList<>();

    //region ... setters/getters

	public String getId() {
		return id;
	}

	public User setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public User setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public User setPosts(List<Post> posts) {
		this.posts = posts;
		return this;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public User setVotes(List<Vote> votes) {
		this.votes = votes;
		return this;
	}

	//endregion
}

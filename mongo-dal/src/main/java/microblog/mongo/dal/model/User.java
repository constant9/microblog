package microblog.mongo.dal.model;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Document(collection = "users")
public class User {
    @Id
    private Integer id;

    private String name;

    @CreationTimestamp
    private Date creationDate;

    @DBRef
    private List<Post> posts;

    private List<Vote> votes = new ArrayList<>();

    //region ... setters/getters

	public Integer getId() {
		return id;
	}

	public User setId(Integer id) {
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

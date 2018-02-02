package boot.dal.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;

    private String subject;
    private String text;

    private Date creationDate = new Date();
    private Date updateDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Vote> votes = new ArrayList<>();

    //region ... setters/getters

    public int getId() {
        return id;
    }

    public Post setId(int id) {
        this.id = id;
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

    public List<Vote> getVotes() {
        return votes;
    }

    public Post setVotes(List<Vote> votes) {
        this.votes = votes;
        return this;
    }

    //endregion


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", creator=" + creator +
                '}';
    }
}

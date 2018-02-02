package boot.dal.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "votes")
public class Vote  implements Serializable {

    @EmbeddedId
    private VoteId voteId;
    private int score;
    private Date creationDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    private Post post;

    protected Vote(){ /*this.voteId = new VoteId(); */}

    public Vote(int score, User user, Post post) {
        this.score = score;
        this.user = user;
        this.post = post;
        this.voteId = new VoteId(user.getId(),post.getId());
    }

    //region ... setters/getters

    public VoteId getVoteId() {
        return voteId;
    }

    public Vote setVoteId(VoteId voteId) {
        this.voteId = voteId;
        return this;
    }

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

    public User getUser() {
        return user;
    }

    public Vote setUser(User user) {
        this.user = user;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public Vote setPost(Post post) {
        this.post = post;
        return this;
    }
    //endregion

    //region ...hash-equals-tostring
    @Override
    public String toString() {
        return "Vote{" +
                "voteId=" + voteId +
                ", score=" + score +
                ", creationDate=" + creationDate +
                ", user=" + user +
                ", post=" + post +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return score == vote.score &&
                Objects.equals(voteId, vote.voteId) &&
                Objects.equals(creationDate, vote.creationDate) &&
                Objects.equals(user, vote.user) &&
                Objects.equals(post, vote.post);
    }

    @Override
    public int hashCode() {

        return Objects.hash(voteId, score, creationDate, user, post);
    }
    //endregion

    @Embeddable
    public static class VoteId implements Serializable {
        private int postId;
        private int userId;

        //fails without default constr.
        private VoteId() {
        }

        public VoteId(int userId, int postId) {
            this.userId = userId;
            this.postId = postId;
        }

        @Override
        public String toString() {
            return "VoteId{" +
                    "postId=" + postId +
                    ", userId=" + userId +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VoteId voteId = (VoteId) o;
            return postId == voteId.postId &&
                    userId == voteId.userId;
        }

        @Override
        public int hashCode() {

            return Objects.hash(postId, userId);
        }
    }

}

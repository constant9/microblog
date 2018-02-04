package boot.api.rest;

import org.springframework.context.ApplicationEvent;

public class VoteEvent extends ApplicationEvent {
	private int postId, voteScore;

	public VoteEvent(Object source, int postId, int voteScore) {
		super(source);
		this.postId = postId;
		this.voteScore = voteScore;
	}

	public int getPostId() {
		return postId;
	}

	public int getVoteScore() {
		return voteScore;
	}
}
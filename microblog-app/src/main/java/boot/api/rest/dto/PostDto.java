package boot.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PostDto {
    private String subject;
    private String text;
	@JsonProperty("user_name")
    private String userName;
    private int id;
    private int score;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@JsonProperty("creation_date")
    private Date creationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@JsonProperty("update_date")
    private Date updateDate;

    public String getSubject() {
        return subject;
    }

    public PostDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public PostDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public PostDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public int getId() {
        return id;
    }

    public PostDto setId(int id) {
        this.id = id;
        return this;
    }

    public int getScore() {
        return score;
    }

    public PostDto setScore(int score) {
        this.score = score;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public PostDto setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public PostDto setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }
}

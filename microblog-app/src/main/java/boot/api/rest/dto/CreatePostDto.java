package boot.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePostDto {
    private String subject;
    private String text;

	@JsonProperty("user_name")
    private String userName;

    public String getSubject() {
        return subject;
    }

    public CreatePostDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public CreatePostDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CreatePostDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}

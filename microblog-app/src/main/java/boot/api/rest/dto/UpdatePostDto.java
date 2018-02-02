package boot.api.rest.dto;

public class UpdatePostDto {
    private String subject;
    private String text;

    public String getSubject() {
        return subject;
    }

    public UpdatePostDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public UpdatePostDto setText(String text) {
        this.text = text;
        return this;
    }
}

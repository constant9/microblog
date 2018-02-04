package boot.api.rest.dto;

public class UpdatePostDto {
    private String subject;
    private String text;

    public UpdatePostDto(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public UpdatePostDto() {
    }

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

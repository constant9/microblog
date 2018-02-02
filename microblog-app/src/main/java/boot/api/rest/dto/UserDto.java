package boot.api.rest.dto;

public class UserDto {
    String name;
    int id;

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public int getId() {
        return id;
    }

    public UserDto setId(int id) {
        this.id = id;
        return this;
    }
}

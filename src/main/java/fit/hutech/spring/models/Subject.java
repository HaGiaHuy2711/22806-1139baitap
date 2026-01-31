package fit.hutech.spring.models;

public class Subject {
    private String code;
    private String name;
    private String image;

    public Subject(String code, String name, String image) {
        this.code = code;
        this.name = name;
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}


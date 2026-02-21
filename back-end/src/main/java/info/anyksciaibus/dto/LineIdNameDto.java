package info.anyksciaibus.dto;

public class LineIdNameDto {
    private Long id;
    private String name;


    //=========================================


    public LineIdNameDto() {
    }

    //need this constructor for Jpa Repo custom method
    public LineIdNameDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


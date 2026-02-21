package info.anyksciaibus.dto;

public class KeyValueDto {
    String key;
    String value;









    //=============================
    public KeyValueDto() {
    }

    public KeyValueDto(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}



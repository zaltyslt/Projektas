package trash;

public class TestDto {
    private String key;

    public TestDto() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ScheduleCreateDto{" +
                "key='" + key + '\'' +
                '}';
    }
}

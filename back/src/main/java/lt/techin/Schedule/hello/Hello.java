package lt.techin.Schedule.hello;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lt.techin.Schedule.tools.TextValid;

import java.util.Objects;


//@Entity
public class Hello {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String text;

    public Hello() {
    }

    public Hello(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hello that = (Hello) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "Hello{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

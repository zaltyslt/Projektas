package lt.techin.schedule.hello;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloDTO {

    @NotBlank
    @Email
    private String answer;

    public HelloDTO() {
        this.answer =  "Hello from database !!!";
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "HelloDTO{" +
                "answer='" + answer + '\'' +
                '}';
    }
}

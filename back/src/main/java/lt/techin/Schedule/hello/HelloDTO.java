package lt.techin.Schedule.hello;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lt.techin.Schedule.tools.TextValid;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloDTO {
    @TextValid(textMaximumLength = 40)
    private String answer;

    public HelloDTO() {
        this.answer =  "Hello from the server !!!";
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

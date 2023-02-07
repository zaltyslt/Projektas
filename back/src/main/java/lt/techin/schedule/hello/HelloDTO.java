package lt.techin.schedule.hello;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloDTO {
   private String answer;

    public HelloDTO() {
        this.answer =  "Hello from server !!!";
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

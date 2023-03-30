package lt.techin.schedule.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class ErrorDto {
    private String url;
    private String message;
    private Integer status;
    private String error;
    private String path;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timestamp;

    public ErrorDto(String url, String message, Integer status, String error, String path, LocalDateTime timestamp) {
        this.url = url;
        this.message = message;
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
    }

    public ErrorDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return Objects.equals(url, errorDto.url)
                && Objects.equals(message, errorDto.message)
                && Objects.equals(status, errorDto.status)
                && Objects.equals(error, errorDto.error)
                && Objects.equals(path, errorDto.path)
                && Objects.equals(timestamp, errorDto.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, message, status, error, path, timestamp);
    }

    @Override
    public String toString() {
        return "ErrorDto{" + "url='" + url + '\'' + ", message='" + message + '\'' + ", status=" + status + ", " +
                "error='" + error + '\'' + ", path='" + path + '\'' + ", timestamp=" + timestamp + '}';
    }
}

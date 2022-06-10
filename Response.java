package engine;

public class Response {
    private boolean success;
    private String feedback;
    public Response (boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public boolean isSuccess() {
        return success;
    }
}

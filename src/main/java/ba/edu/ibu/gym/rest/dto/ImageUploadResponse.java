package ba.edu.ibu.gym.rest.dto;

public class ImageUploadResponse {
    private boolean success;
    private String message;
    private String imageUrl;

    public ImageUploadResponse(boolean success, String message, String imageUrl) {
        this.success = success;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    // Getters and setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

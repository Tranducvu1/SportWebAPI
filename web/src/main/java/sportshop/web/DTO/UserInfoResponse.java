package sportshop.web.DTO;

public class UserInfoResponse {
    private String username;
    private String hinhDaiDien;

    // Constructor
    public UserInfoResponse(String username, String hinhDaiDien) {
        this.username = username;
        this.hinhDaiDien = hinhDaiDien;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(String hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }
}


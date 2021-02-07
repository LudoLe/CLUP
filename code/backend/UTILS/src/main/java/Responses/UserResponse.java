package Responses;


import polimi.it.DL.entities.User;

public class UserResponse {

    private User user;
    private String token;

    public UserResponse(User user, String token){
        this.user=user;
        this.token=token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

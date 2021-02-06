package Responses;


import polimi.it.DL.entities.User;

public class UserResponse {

    User user;
    String token;

    public UserResponse(User user, String token){
        this.user=user;
        this.token=token;
    }
}

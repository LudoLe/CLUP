package polimi.it.AMB;


import javax.ejb.Stateless;

@Stateless(name = "AccountManagerService")
public class LoginHandler {
    public String hello() {
        return "Hello, World!";
    }
}

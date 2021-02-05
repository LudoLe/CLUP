package polimi.it.DL.services;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.User;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless(name= "services/UserService")
public class UserService {

    // I set this parameters to low, pretty unsafe values so login is faster
    private static final int ARGON2_ITERATIONS = 2;
    private static final int ARGON2_MEMORY = 16384;
    private static final int ARGON2_PARALLELISM = 1;
    private static final int ARGON2_SALT_LENGTH = 64;
    private static final int ARGON2_HASH_LENGTH = 128;

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public UserService(){}

    /*public User checkCredentials(String username, String password) throws Exception {
        User user;
        try {
            user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        if(user == null) throw new Exception();
        boolean passed = false;
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
        try {
            passed = encoder.matches(password, user.getPassword());
        } catch(Exception e) {
            passed = false;
        }
        return passed ? user : null;


    }

    public User find(int id) throws Exception{
        return em.find(User.class, id);
    }*/

    public User createUser(String usrn, String pwd, String email) throws Exception{
        try{
            //checks that username and email aren't already in use
            if(em.createNamedQuery("User.exists", Long.class).setParameter(1, usrn).setParameter(2, email).getSingleResult() >= 1) return null;
            User user = new User();
            user.setUsername(usrn);
            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
            String hash = encoder.encode(pwd);
            user.setPassword(hash);
            user.setEmail(email);
            em.persist(user);
            em.flush();
            return user;
        } catch (PersistenceException e) {
            throw new Exception("Could not insert user");
        }


    }
}

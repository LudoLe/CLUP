package polimi.it.DL.services;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.User;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Base64;


@Stateless(name= "services/UserService")
public class UserService {

    boolean test = false;

    // I set this parameters to low, pretty unsafe values so login is faster
    private static final int ARGON2_ITERATIONS = 2;
    private static final int ARGON2_MEMORY = 16384;
    private static final int ARGON2_PARALLELISM = 1;
    private static final int ARGON2_SALT_LENGTH = 64;
    private static final int ARGON2_HASH_LENGTH = 128;

    @PersistenceContext(unitName = "clup")
    private EntityManager em;

    public UserService(){}

    public User checkCredentials(String username, String password) throws Exception {
        User user;
        System.out.println("till now fine before query");
        user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);
        if(!(user == null)){
            boolean passed;
            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
            passed = encoder.matches(password, user.getPassword());
            if(passed) {
                user.setSessionToken(generateSessionToken());
                em.persist(user);
                em.flush();
                em.clear();

            }
        }
        return user;
    }

    public void invalidateSessionToken(String username){
        User user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);
        if(!(user == null)){
            user.setSessionToken(null);
            em.persist(user);
            em.flush();
            em.clear();
        }
    }


    public String newSessionToken(String username){
        User user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);
        if(!(user == null)){
                String newSessionToken = generateSessionToken();
                user.setSessionToken(newSessionToken);
                em.persist(user);
                em.flush();
                em.clear();
            return newSessionToken;
        }
        System.out.println("user not found in check session token");

        return null;
        }

    public void logOut(String username) throws Exception{
           User user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);
           if(user!=null) {
               user.setSessionToken(null);
               em.persist(user);
               em.flush();
               em.clear();

           }
    }

    public Boolean isAuthorized(String username, String token) throws Exception{
        User user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);;
        if(!(user == null)){
            if(user.getSessionToken()!=null){

                if(user.getSessionToken().equals(token)) {
                    return true;
                }
            }else
            {            System.out.println("user not found in is authorized session token");
                         user.setSessionToken(null);
                em.persist(user);
                em.flush();
                em.clear();



                return false;
            }
        }
        return false;

    }

    public Boolean isAuthorizedAndManager(String username, String token) throws Exception{
        User user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);;
        assert user != null;
        if(user.getSessionToken()!=null){
            System.out.println(user.getSessionToken());
           if(user.getSessionToken().equals(token)&&(user.getIsManager()))return true;
           else{
               user.setSessionToken(null);
               em.persist(user);
               em.flush();
               em.clear();
               return false;
           }
        }else return false;
    }



    public User findByUsername(String username) throws Exception{
        User user = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username).getResultList().stream().findFirst().orElse(null);;
        return user;
    }

    public User find(int id) throws Exception{
        return em.find(User.class, id);
    }

    public User createUser(String usrn, String pwd, String email, boolean isManager, String phoneNumber) throws Exception{
        try{
            //checks that username and email aren't already in use
            if(em.createNamedQuery("User.exists", Long.class).setParameter(1, usrn).setParameter(2, email).getSingleResult() >= 1) return null;
            User user = new User();
            user.setUsername(usrn);
            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
            String hash = encoder.encode(pwd);
            user.setPassword(hash);
            user.setEmail(email);
            user.setSessionToken(generateSessionToken());
            user.setIsManager(isManager);
            user.setPhoneNumber(phoneNumber);
            em.persist(user);
            em.flush();
            em.clear();

            return user;
        } catch (PersistenceException e) {
            throw new Exception("Could not insert user");
        }
    }

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateSessionToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public UserService(EntityManager em, boolean test){
        this.em = em;
        this.test = test;
    }
}

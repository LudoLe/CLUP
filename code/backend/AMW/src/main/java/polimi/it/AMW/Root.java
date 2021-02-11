package polimi.it.AMW;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.BeanConfig;
import polimi.it.AMW.Gateway;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class Root extends Application {

    public Root()
    {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8082");
        beanConfig.setBasePath("AMW-1.0-SNAPSHOT/api");
        beanConfig.setResourcePackage(Gateway.class.getPackage().getName());
        beanConfig.setTitle("Account Management Service");
        beanConfig.setDescription("account management service");
        beanConfig.setScan(true);
    }

}

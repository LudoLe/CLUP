package polimi.it.QSW;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class Root extends Application {

    public Root()
    {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/QSW_war_exploded/api");
        beanConfig.setResourcePackage(Gateway.class.getPackage().getName());
        beanConfig.setTitle("Information Service");
        beanConfig.setDescription("A simple information service");
        beanConfig.setScan(true);
    }

}

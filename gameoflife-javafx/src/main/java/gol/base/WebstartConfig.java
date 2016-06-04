package gol.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tino on 14.05.2016.
 */
@Configuration
public class WebstartConfig {

//    @Bean
//    public BasicService basicService() {
//        try {
//            BasicService service =  (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
//            service.showDocument(new URL("http://www.google.de"));
//            return service;
//        }
//        catch (UnavailableServiceException ex) {
//            throw new RuntimeException(ex);
//        }
//        catch (MalformedURLException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
}

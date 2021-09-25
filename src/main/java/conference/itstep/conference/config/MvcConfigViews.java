package conference.itstep.conference.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigViews implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry){
       // registry.addViewController("first.html").setViewName("directives/first");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registry").setViewName("registry");
        registry.addViewController("/themes").setViewName("themes");

      /*  registry.addViewController("/breadcrumb").setViewName("directives/breadcrumb");
        registry.addViewController("/flipcard").setViewName("directives/flipcard");*/

      /* registry.addViewController("/header_fr").setViewName("fragments/header_fr");
        registry.addViewController("/footer_fr").setViewName("fragments/footer_fr");*/
        registry.addViewController("/").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
      /* registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:views/");*/
    }


    }

package conference.itstep.conference.config;

import conference.itstep.conference.components.CsrfTokenResponseHeaderBindingFilter;
import conference.itstep.conference.components.StatelessAuthFilter;
import conference.itstep.conference.components.TokenAuthService;
import conference.itstep.conference.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
  /*@Bean
  UserDetailsService getUserDetailsService(){
      return new UserService();
  }*/

  @Autowired
    TokenAuthService tokenAuthService;

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder());
    }
    @Override
    protected void configure( AuthenticationManagerBuilder auth)throws Exception {
        auth.eraseCredentials(false).userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();

        http
                .addFilterBefore(new StatelessAuthFilter(tokenAuthService ),UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/views/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("**/favicon").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/directives/**").permitAll()
                .antMatchers("/factories/**").permitAll()
                .antMatchers("/one_user/**").permitAll()
                .antMatchers("/all_directive/**").permitAll()
                .antMatchers("/fragments/**").permitAll()
                .antMatchers("/footer_fr").permitAll()
                .antMatchers("/header_fr").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/registry").permitAll()
                .antMatchers("/themes").permitAll()
                .antMatchers("/api/v1/one_user").permitAll()
                .antMatchers("/api/v1/check_one").permitAll()
                .antMatchers("/api/v1/user_file").hasRole("USER")
                .antMatchers("/api/v1/update_user").hasRole("USER")
                .antMatchers("/api/v1/delete_user").hasRole("USER")
                .antMatchers("/api/v1/peoples").hasRole("ADMIN")
                .and()
                .addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class)
                .csrf();

    }

    @Bean
    PasswordEncoder bcryptPasswordEncoder(){

        return new BCryptPasswordEncoder(){
            @Override
            public String encode(CharSequence rawPassword) {
                return BCrypt.hashpw(rawPassword.toString(),BCrypt.gensalt());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }




}
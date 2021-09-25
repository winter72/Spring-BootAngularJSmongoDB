package conference.itstep.conference.components;

import conference.itstep.conference.models_entitys.Users;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class StatelessAuthFilter extends GenericFilterBean {

    private final TokenAuthService tokenAuthService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenHandler tokenHandler;

    public StatelessAuthFilter(@NonNull TokenAuthService tokenAuthService) {
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    /*  try {
           String s=SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
         //  System.out.println(s);
            if(s.equals("[USER]")){
                    Users user= (Users) SecurityContextHolder.getContext().getAuthentication().getDetails();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }catch (NullPointerException ex){

        }*/
    String token;
    try {
        token = tokenAuthService.resolveToken((HttpServletRequest) request);
      //  System.out.println(token);
        if (token!=null&&tokenHandler.validateToken(token)) {

            Authentication authentication=tokenAuthService.getAuthentication((HttpServletRequest) request).orElse(null);

            if(authentication!=null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
              //System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        }
    }catch (NullPointerException ex){
       // System.out.println(ex);
    }

        chain.doFilter(request, response);
    }
}

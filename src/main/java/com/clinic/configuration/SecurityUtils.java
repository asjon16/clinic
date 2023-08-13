package com.clinic.configuration;


import com.clinic.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {


    public static Integer getLoggedUserId(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null && authentication.getPrincipal() instanceof Jwt){
            // handle jwt profile
            return getRestLoggedUser();
        }else {
            //handle null authentication
            return null;
        }
    }

    public static Integer getRestLoggedUser(){
        var authentication = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Integer.valueOf(authentication.getClaim("sub"));
    }
}

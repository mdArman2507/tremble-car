package com.trimble.trimbleCar.config;

import com.arbaz.book.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationAuditAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ){
            return Optional.empty();
        }
        User userPrinciple=(User) authentication.getPrincipal();
        return Optional.ofNullable(userPrinciple.getId());
    }
//@Override
//public Optional<Integer> getCurrentAuditor() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
//        return Optional.empty();  // No user is authenticated
//    }
//    User userPrinciple = (User) authentication.getPrincipal();  // Assumes you have a custom User class implementing UserDetails
//    return Optional.ofNullable(userPrinciple.getId());  // Return the user ID
//}
}

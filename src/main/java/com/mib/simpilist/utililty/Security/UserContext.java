package com.mib.simpilist.utililty.Security;

import com.mib.simpilist.dto.Security.CurrentUserContext;
import com.mib.simpilist.utililty.Utilities;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserContext {
    public static CurrentUserContext getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Utilities.isNull(authentication) || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CurrentUserContext) {
            return (CurrentUserContext) principal;
        }

        throw new AuthenticationCredentialsNotFoundException("Invalid System usage");
    }
}

package org.ardian.librarymanagementsystem.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom security annotation that grants access to anonymous users
 * and administrators only.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAnonymous() or hasRole('ADMIN')")
public @interface IsAnonymousOrAdmin {
}

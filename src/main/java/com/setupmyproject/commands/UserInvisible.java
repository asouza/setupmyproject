package com.setupmyproject.commands;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Use para criar comandos que não devem ser listados para os usuários
 * @author alberto
 *
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface UserInvisible {

}

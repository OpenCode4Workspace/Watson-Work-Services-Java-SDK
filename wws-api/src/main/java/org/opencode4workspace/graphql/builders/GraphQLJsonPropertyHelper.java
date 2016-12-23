package org.opencode4workspace.graphql.builders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for "jsonProperty"
 * 
 * @author Christian Guedemann
 * 
 * @since 0.5.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GraphQLJsonPropertyHelper {

	String jsonProperty();
}

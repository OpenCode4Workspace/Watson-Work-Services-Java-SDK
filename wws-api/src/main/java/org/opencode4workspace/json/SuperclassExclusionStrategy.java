package org.opencode4workspace.json;

import java.lang.reflect.Field;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * @author Christian Guedemann
 * @since 0.5.0
 * @see ExclusionStrategy
 * 
 *      An exclusion strategy that ignores fields in the superclass
 */
public class SuperclassExclusionStrategy implements ExclusionStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
	 */
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
	 */
	public boolean shouldSkipField(FieldAttributes fieldAttributes) {
		String fieldName = fieldAttributes.getName();
		Class<?> theClass = fieldAttributes.getDeclaringClass();

		return isFieldInSuperclass(theClass, fieldName);
	}

	/**
	 * Checks whether the field is in the superclass
	 * 
	 * @param subclass
	 *            Class, subclass extending the superclass to check
	 * @param fieldName
	 *            String, property in the super class to check for
	 * @return boolean, whether the field being tested is in the superclass
	 * 
	 * @since 0.5.0
	 */
	private boolean isFieldInSuperclass(Class<?> subclass, String fieldName) {
		Class<?> superclass = subclass.getSuperclass();
		Field field;

		while (superclass != null) {
			field = getField(superclass, fieldName);

			if (field != null)
				return true;

			superclass = superclass.getSuperclass();
		}

		return false;
	}

	/**
	 * Gets a field from a class
	 * 
	 * @param theClass
	 *            Class, class to get the property from
	 * @param fieldName
	 *            String, the property to retrieve
	 * @return Field, corresponding to the property being retrieved
	 * 
	 * @since 0.5.0
	 */
	private Field getField(Class<?> theClass, String fieldName) {
		try {
			return theClass.getDeclaredField(fieldName);
		} catch (Exception e) {
			return null;
		}
	}
}
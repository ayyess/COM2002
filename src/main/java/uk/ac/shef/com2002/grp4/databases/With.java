package uk.ac.shef.com2002.grp4.databases;

/**
 * Functional interface for Connection Manager
 * Created on 11/11/2016.
 */
@FunctionalInterface
public interface With<T, R, EXCEPTION_TYPE extends Exception> {
	R with(T t) throws EXCEPTION_TYPE;
}

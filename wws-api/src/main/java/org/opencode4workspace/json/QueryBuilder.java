package org.opencode4workspace.json;

import java.io.Serializable;

import org.opencode4workspace.WWException;
import org.opencode4workspace.graphql.builders.AbstractGraphQLQuery;

/**
 * @author Paul Withers
 * @since 0.5.0
 * 
 *        QueryBuilder for building queries. Pass in a GraphQLQuery object to the {@linkplain #buildFromGraphQLQuery(String, AbstractGraphQLQuery)} method. <b>NOTE:</b> This cannot be empty or null!
 *
 */
public class QueryBuilder implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String extractedQuery;
	private AbstractGraphQLQuery attributes;

	/**
	 * Private constructor, called from {@linkplain #buildFromGraphQLQuery(String, AbstractGraphQLQuery)}
	 * 
	 * @param name
	 *            String, name for query, to be passed to the GraphQLRequest
	 * @param attributes
	 *            AbstractGraphQLQuery map of attributes
	 */
	private QueryBuilder(String name, AbstractGraphQLQuery attributes) {
		this.name = name;
		this.attributes = attributes;
	}

	/**
	 * Initialises the QueryBuilder. This method throws an error if query param is empty or null
	 * 
	 * @param name
	 *            String, name for the query, e.g. "getSpaces"
	 * @param query
	 *            AbstractGraphQLQuery, effectively a Map
	 * @return QueryBuilder object initialised
	 * @throws WWException
	 *             error
	 */
	public static QueryBuilder buildFromGraphQLQuery(String name, AbstractGraphQLQuery query) throws WWException {
		if (null == query) {
			throw new WWException("This method does not accept an empty GraphQLQuery object");
		} else if (query.isEmpty()) {
			throw new WWException("This method does not accept an empty GraphQLQuery object");
		} else {
			return new QueryBuilder(name, query);
		}
	}

	/**
	 * @return String, name of query
	 */
	private String getName() {
		return name;
	}

	/**
	 * @return AbstractGraphQLQuery, attributes for the query
	 */
	private AbstractGraphQLQuery getAttributes() {
		return attributes;
	}

	/**
	 * @return String, query extracted from AbstractGraphQLQuery object
	 */
	private String getExtractedQuery() {
		return extractedQuery;
	}

	public void buildExtractedQuery() {

	}

}

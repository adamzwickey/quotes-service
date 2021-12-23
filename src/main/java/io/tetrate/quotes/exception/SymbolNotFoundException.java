package io.tetrate.quotes.exception;

/**
 * Exception representing that a quote symbol cannot be found.
 * @author Adam Zwickey
 *
 */
public class SymbolNotFoundException extends Exception {

	public SymbolNotFoundException(String message ) {
		super(message);
	}
}

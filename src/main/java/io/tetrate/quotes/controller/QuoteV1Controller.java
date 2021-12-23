package io.tetrate.quotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.tetrate.quotes.domain.CompanyInfo;
import io.tetrate.quotes.domain.Quote;
import io.tetrate.quotes.exception.SymbolNotFoundException;
import io.tetrate.quotes.service.QuoteService;

/**
 * Rest Controller providing the REST API for the Quote Service. Provides two
 * calls (both HTTP GET methods): - /quote/{symbol} - Retrieves the current
 * quote for a given symbol. - /company/{name} - Retrieves a list of company
 * information for companies that match the {name}.
 * 
 * @author Adam Zwickey
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class QuoteV1Controller {
	private static final Logger logger = LoggerFactory.getLogger(QuoteV1Controller.class);

	/**
	 * The service to delegate calls to.
	 */
	@Autowired
	private QuoteService service;

	/**
	 * Retrieves the current quotes for the given symbols.
	 * 
	 * @param query request parameter with q=symbol,symbol
	 * @return The Quote
	 * @throws SymbolNotFoundException if the symbol is not valid.
	 */
	@GetMapping(value = "/quotes")
	public ResponseEntity<List<Quote>> getQuotes(@RequestParam(value="q", required=false) String query) throws SymbolNotFoundException{
		logger.debug("received Quote query for: {}", query);
		if (query == null) {
			//return empty list.
			return new ResponseEntity<List<Quote>>(new ArrayList<Quote>(), getNoCacheHeaders(), HttpStatus.OK);
		}
		List<Quote> quotes = new ArrayList<>();
		String[] splitQuery = query.split(",");
		if (splitQuery.length > 1) {
			for (String i : splitQuery) {
				//TODO investigate batch API
				quotes.add(service.getQuote(i));	
			}
		} else {
			quotes.add(service.getQuote(splitQuery[0]));
		}
		logger.info(String.format("Retrieved symbols: [%s] with quotes {}", query, quotes));
		return new ResponseEntity<List<Quote>>(quotes, getNoCacheHeaders(), HttpStatus.OK);
	}

	/**
	 * Searches for companies that have a name or symbol matching the parameter.
	 * 
	 * @param name The name or symbol to search for.
	 * @return The list of companies that match the search parameter.
	 */
	@GetMapping(value = "/company/{name}")
	public ResponseEntity<CompanyInfo> getCompany(@PathVariable("name") final String name) {
		logger.debug("QuoteController.getCompanies: retrieving companies for: {}", name);
		CompanyInfo company = service.getCompanyInfo(name);
		logger.info(String.format("Retrieved companies with search parameter: %s - list: {}", name), company);
		return new ResponseEntity<CompanyInfo>(company, HttpStatus.OK);
	}

	/**
	 * Generates HttpHeaders that have the no-cache set.
	 * 
	 * @return HttpHeaders.
	 */
	private HttpHeaders getNoCacheHeaders() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Cache-Control", "no-cache");
		return responseHeaders;
	}

	/**
	 * Handles the response to the client if there is any exception during the
	 * processing of HTTP requests.
	 * 
	 * @param e The exception thrown during the processing of the request.
	 * @param response The HttpResponse object.
	 * @throws IOException
	 */
	@ExceptionHandler({ Exception.class })
	public void handleException(Exception e, HttpServletResponse response) throws IOException {
		logger.warn("Handle Error: {}", e.getMessage());
		logger.warn("Exception:", e);
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR: " + e.getMessage());
	}
}

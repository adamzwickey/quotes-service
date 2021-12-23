package io.tetrate.quotes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.tetrate.quotes.domain.CompanyInfo;
import io.tetrate.quotes.domain.Quote;
import io.tetrate.quotes.exception.SymbolNotFoundException;

import java.math.BigDecimal;
import java.util.*;

import com.google.common.collect.ImmutableMap;

/**
 * A service to retrieve Company and Quote information.
 * 
 * @author Adam Zwickey
 *
 */
@Service
public class QuoteService {

	private final Logger log = LoggerFactory.getLogger(QuoteService.class);

	@Value("${quotes.quoteUrl}")
	protected String _quoteUrl;
	@Value("${quotes.quotesUrl}")
	protected String _quotesUrl;
	@Value("${quotes.companiesUrl}")
	protected String _companyUrl;

	public static final String FMT = "json";	

	@Bean
    RestTemplate restTemplate(){
      return new RestTemplate();
    }

	/**
	 * Retrieves an up to date quote for the given symbol.
	 * 
	 * @param symbol The symbol to retrieve the quote for.
	 * @return The quote object or null if not found.
	 * @throws SymbolNotFoundException
	 */
	// @CircuitBreaker(name = "QUOTE", fallbackMethod = "getQuoteFallback")
	public Quote getQuote(String symbol) throws SymbolNotFoundException {
		log.debug("QuoteService.getQuote: retrieving quote for: {}", symbol);
		ResponseEntity<Quote> response = restTemplate().getForEntity(_quoteUrl, Quote.class, Map.of("symbol", symbol));
		Quote quote = response.getBody();

		if (quote == null || quote.getSymbol() == null) {
			throw new SymbolNotFoundException("Symbol not found: " + symbol);
		}
		log.debug("QuoteService.getQuote: retrieved quote: {}", quote);
		return quote;
	}

	@SuppressWarnings("unused")
	private Quote getQuoteFallback(String symbol) throws SymbolNotFoundException {
		log.debug("QuoteService.getQuoteFallback: circuit opened for symbol: {}", symbol);
		Quote quote = new Quote();
		quote.setSymbol(symbol);
		quote.setLatestPrice(new BigDecimal(0));
		return quote;
	}

	/**
	 * Retrieves a list of CompanyInfor objects. Given the name parameters, the
	 * return list will contain objects that match the search both on company
	 * name as well as symbol.
	 * 
	 * @param name The search parameter for company name or symbol.
	 * @return The company information.
	 */
	// @HystrixCommand(fallbackMethod = "getCompanyInfoFallback",
	// 	    commandProperties = {
	// 	      @HystrixProperty(name="execution.timeout.enabled", value="false")
	// 	    })
	// @CircuitBreaker(name = "COMPANY", fallbackMethod = "getCompanyInfoFallback")
	public CompanyInfo getCompanyInfo(String name) {
		log.debug("QuoteService.getCompanyInfo: retrieving info for: {}", name);	
		CompanyInfo company = restTemplate().getForObject(_companyUrl,CompanyInfo.class, Map.of("name", name));
		log.debug("QuoteService.getCompanyInfo: retrieved info: {}", company);
		return company;
	}

	
	@SuppressWarnings("unused")
	private List<CompanyInfo> getCompanyInfoFallback(String symbol) {
		log.debug("QuoteService.getCompanyInfoFallback: circuit opened for symbol: {}", symbol);
		List<CompanyInfo> companies = new ArrayList<>();
		return companies;
	}
}

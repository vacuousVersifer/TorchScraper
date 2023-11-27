package cmonster.browsers;

import cmonster.cookies.Cookie;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class Browser {

	/**
	 * A file that should be used to make a temporary copy of the browser's cookie store
	 */
	final File cookieStoreCopy = new File(".cookies.db");

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Returns cookies for a given domain
	 */
	public Set<Cookie> getCookiesForDomain(String domain) {
		HashSet<Cookie> cookies = new HashSet<>();
		for (File cookieStore : getCookieStores()) {
			cookies.addAll(processCookies(cookieStore, domain));
		}
		return cookies;
	}

	/**
	 * Returns a set of cookie store locations
	 *
	 * @return Cookie Store
	 */
	public abstract Set<File> getCookieStores();

	/**
	 * Processes all cookies in the cookie store for a given domain or all
	 * domains if domainFilter is null
	 *
	 * @param cookieStore Cookie's cookie store
	 * @param domainFilter Domain filter for processing
	 * @return Cookie store
	 */
	protected abstract Set<Cookie> processCookies(File cookieStore, String domainFilter);

	/**
	 * Returns the browser proper name
	 *
	 * @return The browser's name
	 */
	public abstract String getName();

}

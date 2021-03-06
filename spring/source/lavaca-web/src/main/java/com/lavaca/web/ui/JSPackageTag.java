package com.lavaca.web.ui;

import javax.servlet.jsp.JspException;

import com.lavaca.web.compression.CodePackage;
import com.lavaca.web.compression.JSPackage;
import com.lavaca.web.config.LavacaConfig;

/**
 * Tag for including Lavaca script in a page
 */
public class JSPackageTag extends CodePackageTag {

	private static final long serialVersionUID = 1641459714851233770L;

	private static final String REFERENCE_ELEMENT = "<script type=\"text/javascript\" src=\"%s\"></script>";
	private static final String INLINE_ELEMENT = "<script type=\"text/javascript\">%s</script>";
	private static final String REFERENCE_URL = "/min.js?k=%s&d=%s";

	/**
	 * Determines the URL needed to reference a code package
	 * 
	 * @param cp
	 *            The code package tor eference
	 * @return The URL used to access the code package
	 * @throws JspException
	 */
	@Override
	protected String getReferenceURL(CodePackage cp) throws JspException {
		return String.format(REFERENCE_URL, cp.getKey(), cp.getCreated()
				.getTime());
	}

	/**
	 * Determines the markup to use when the code is to be referenced externally
	 * 
	 * @param cp
	 *            The code package to reference
	 * @param url
	 *            The URL of the packaged code
	 * @return The HTML code required to reference the package
	 */
	@Override
	protected String getReferenceElement(CodePackage cp, String url) {
		return String.format(REFERENCE_ELEMENT, url);
	}

	/**
	 * Determines the markup to use when the code is to be written inline
	 * 
	 * @param cp
	 *            The code package to inline
	 * @param code
	 *            The code to inline
	 * @return The HTML code required to include the code directly in the host
	 *         page
	 */
	@Override
	protected String getInlineElement(CodePackage cp, Object code) {
		return String.format(INLINE_ELEMENT, code);
	}

	/**
	 * Retrieves a code package, given its key
	 * 
	 * @param key
	 *            The identifier of the code package
	 * @return The code package
	 * @throws JspException
	 */
	@Override
	protected CodePackage getCodePackage(String key) throws JspException {
		try {
			return JSPackage.get(this.pageContext.getServletContext(), key);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	/**
	 * Retrieves all keys associated with a code package
	 * 
	 * @return The keys included within that package
	 */
	@Override
	protected String[] getCollectedKeys() {
		return LavacaConfig.instance(this.pageContext.getServletContext())
				.getScriptKeys();
	}

}

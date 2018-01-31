package ru.shaldnikita.starter.app;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author n.shaldenkov on 23.11.2017
 */

public class IconBootstrapListener implements BootstrapListener {

	protected String baseUri = "theme://icon-";
	protected String extension = ".png";
	protected String[] rels = { "icon", "apple-touch-icon" };
	protected int[] sizes = { 192, 96 };

	@Override
	public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
		// do nothing
	}

	@Override
	public void modifyBootstrapPage(BootstrapPageResponse response) {

		final Document document = response.getDocument();
		final Element head = document.getElementsByTag("head").get(0);
		for (String rel : rels) {
			for (int size : sizes) {
				String iconUri = baseUri + size + extension;
				String href = response.getUriResolver().resolveVaadinUri(iconUri);
				String s = size + "x" + size;
				Element element = document.createElement("link");
				element.attr("rel", rel);
				element.attr("sizes", s);
				element.attr("href", href);
				head.appendChild(element);
			}
		}

		/*- Enable these to hide browser controls when app is started from homescreen:
		Element element = document.createElement("meta");
		element.attr("name", "mobile-web-app-capable");
		element.attr("content", "yes");
		head.appendChild(element);

		element = document.createElement("meta");
		element.attr("name", "apple-mobile-web-app-capable");
		element.attr("content", "yes");
		head.appendChild(element);
		*/
	}

}
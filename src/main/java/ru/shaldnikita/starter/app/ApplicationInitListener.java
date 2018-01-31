package ru.shaldnikita.starter.app;

import com.vaadin.server.ServiceInitEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

/**
 * @author n.shaldenkov on 23.11.2017
 */

@Component
public class ApplicationInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent serviceInitEvent) {
		VaadinService service = serviceInitEvent.getSource();

		service.addSessionInitListener(event -> event.getSession().addBootstrapListener(new IconBootstrapListener()));
	}
}

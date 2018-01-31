package ru.shaldnikita.starter.web.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ConfirmDialogWindow {

	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;


	public void showLeaveViewConfirmDialog(View view, Runnable runOnConfirm) {
		showLeaveViewConfirmDialog(view, runOnConfirm, () -> {

		});
	}

	public void showLeaveViewConfirmDialog(View view, Runnable runOnConfirm, Runnable runOnCancel) {
		ConfirmDialog dialog = confirmDialogFactory.create("Please confirm",
				"You have unsaved changes that will be discarded if you navigate away.", "Discard Changes", "Cancel",
				null);
		dialog.show(view.getViewComponent().getUI(), event -> {
			if (event.isConfirmed()) {
				runOnConfirm.run();
			} else {
				runOnCancel.run();
			}
		}, true);
	}

}

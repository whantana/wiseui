package eu.wisebed.wiseui.client.reservation.view;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(ReservationViewImpl.class)
public interface ReservationView extends IsWidget {

    void setPresenter(Presenter presenter);
    AcceptsOneWidget getParametersPanel();

	
	public interface Presenter{
		void makeReservation();
	}
}
/**
 * Copyright (C) 2011 Universität zu Lübeck, Institut für Telematik (ITM),
 *                             Research Academic Computer Technology Institute (RACTI)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.wisebed.wiseui.client.reservation.view;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.ImplementedBy;

import eu.wisebed.wiseui.shared.dto.ConfidentialReservationData;
import eu.wisebed.wiseui.shared.dto.Node;
import eu.wisebed.wiseui.shared.dto.PublicReservationData;
import eu.wisebed.wiseui.widgets.loading.HasLoadingIndicator;

/**
 * @author John I. Gakos
 * @author Soenke Nommensen
 */
@ImplementedBy(PublicReservationsViewImpl.class)
public interface PublicReservationsView extends IsWidget {

    void setPresenter(Presenter presenter);

    /**
     * Renders a list of public reservations received.
     *
     * @param publicReservations Public reservation data from the backend.
     */
    void renderPublicReservations(List<PublicReservationData> publicReservations);
    
    void renderConfidentialReservations(List<ConfidentialReservationData> confidentialReservations);

    Appointment addReservation(PublicReservationData reservationData, boolean confidential);

    void removeAllAppointments();

    Calendar getCalendar();

    DateBox getDatePicker();

    HasLoadingIndicator getLoadingIndicator();

    public interface Presenter {

        static final int ONE_DAY = 1;

        static final int WEEK = 7;

        void loadReservations(Date date);

        void registerPublicReservation(String appointmentId, PublicReservationData publicReservationData);

        void registerConfidentialReservation(String appointmentId,
                                             ConfidentialReservationData confidentialReservationData);

        void handleBackClicked();

        void handleForwardClicked();

        void handleTodayClicked();
    	
    	boolean isAuthenticated();
    }
}
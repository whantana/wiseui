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
package eu.wisebed.wiseui.client.reservation.event;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import eu.wisebed.wiseui.shared.dto.Node;

import java.util.Set;

/**
 * @author Philipp Abraham
 */
public class CreateReservationEvent extends GwtEvent<CreateReservationEvent.Handler> {

    public interface Handler extends EventHandler {

        void onCreateReservation(CreateReservationEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final Appointment reservation;
    private final Set<Node> nodes;

    public CreateReservationEvent(final Appointment reservation, final Set<Node> nodes) {
        this.reservation = reservation;
        this.nodes = nodes;
    }

    public Appointment getAppointment() {
        return this.reservation;
    }

    public Set<Node> getNodes() {
        return this.nodes;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final Handler handler) {
        handler.onCreateReservation(this);
    }
}

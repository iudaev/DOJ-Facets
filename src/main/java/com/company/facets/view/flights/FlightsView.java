package com.company.facets.view.flights;


import com.company.facets.app.FlightsService;
import com.company.facets.entity.Flight;
import com.company.facets.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "flights-view", layout = MainView.class)
@ViewController("FlightsView")
@ViewDescriptor("flights-view.xml")
public class FlightsView extends StandardView {
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private Notifications notifications;


    @ViewComponent
    protected Span onInitItemCount;
    @ViewComponent
    protected Span onBeforeShowItemCount;
    @ViewComponent
    protected Span onReadyItemCount;
    @ViewComponent
    private CollectionLoader<Flight> flightsDl;
    @ViewComponent
    private CollectionContainer<Flight> flightsDc;

    @Subscribe
    public void onInit(InitEvent event) {
        onInitItemCount.setText(String.valueOf(flightsDc.getItems().size()));
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        onBeforeShowItemCount.setText(String.valueOf(flightsDc.getItems().size()));
    }

    @Subscribe
    public void onReady(ReadyEvent event) {
        onReadyItemCount.setText(String.valueOf(flightsDc.getItems().size()));
    }

    @Subscribe(id = "addFlightsButton", subject = "clickListener")
    public void onAddFlightsButtonClick(final ClickEvent<JmixButton> event) {
        int flightsImported = flightsService.generateFlights();

        //Load flights explicitly to the container
//        flightsDl.load();

        notifications.create("Generated " + flightsImported + " new flights")
                .withThemeVariant(NotificationVariant.LUMO_SUCCESS)
                .show();
    }

    @Subscribe("timer")
    public void onTimerTimerAction(final Timer.TimerActionEvent event) {
        flightsDl.load();
        notifications.create("Timer tics", "Flights refreshed").show();
    }
}
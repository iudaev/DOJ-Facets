package com.company.facets.view.flights;


import com.company.facets.app.FlightsService;
import com.company.facets.entity.Destinations;
import com.company.facets.entity.Flight;
import com.company.facets.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.combobox.JmixComboBox;
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
    @ViewComponent
    private JmixComboBox<Destinations> destinationComboBox;

    @Subscribe
    public void onInit(InitEvent event) {

        destinationComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                flightsDl.setParameter("destination", e.getValue());
                flightsDl.load();
            }
        });

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
}
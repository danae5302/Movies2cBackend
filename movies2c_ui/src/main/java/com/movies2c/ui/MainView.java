package com.movies2c.ui;

import com.movies2c.ui.components.MovieCards;
import com.movies2c.ui.components.SearchBar;
import com.movies2c.ui.models.Movie;
import com.movies2c.ui.services.MovieSearchBarService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    private MovieSearchBarService movieSearchBarService;

    private VerticalLayout resultsContainer = new VerticalLayout();

    public MainView() {

        // Layout ρυθμίσεις
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Background style
        getStyle()
                .set("background", "linear-gradient(90deg, #000000 0%, #0b0b0b 55%, #3a0e0e 75%, #7a1e1e 100%)")
                .set("color", "#f5f5ff")
                .set("font-family", "system-ui");


        Button loginButton = new Button("Login / Sign Up");
        loginButton.getStyle()
                .set("background-color", "transparent")
                .set("border", "2px solid #f5f5ff")
                .set("color", "#f5f5ff")
                .set("border-radius", "10px")
                .set("padding", "5px 15px")
                .set("cursor", "pointer");

        loginButton.addClickListener(e -> openLoginDialog());

        HorizontalLayout header = new HorizontalLayout(loginButton);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.END);
        header.getStyle().set("padding", "15px 30px");

        add(header);

        H1 title = new H1("Movies2c");
        title.getStyle()
                .set("color", "#ffc857")
                .set("margin-top", "20px")
                .set("font-size", "40px")
                .set("letter-spacing", "0.12em")
                .set("text-transform", "uppercase");

        add(title);

        SearchBar searchBar = new SearchBar();
        searchBar.getStyle().set("margin-top", "40px");
        add(searchBar);

        // Results container
        resultsContainer.setWidth("80%");
        resultsContainer.setPadding(false);
        resultsContainer.setSpacing(true);
        resultsContainer.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(resultsContainer);

        // Search logic
        searchBar.getSearchField().addValueChangeListener(event -> {
            String text = event.getValue();

            if (text == null || text.isEmpty()) {
                resultsContainer.removeAll();
                return;
            }

            resultsContainer.removeAll();
            List<Movie> movies = movieSearchBarService.searchMovies(text);

            for (Movie movie : movies) {
                resultsContainer.add(new MovieCards(movie));
            }
        });
    }

    private void openLoginDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeight("350px");

        VerticalLayout form = new VerticalLayout();
        form.setSpacing(true);
        form.setPadding(true);
        form.getStyle().set("background-color", "#f5f5ff");

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        Button submit = new Button("Login");
        submit.getStyle()
                .set("background-color", "#ffc857")
                .set("color", "black");

        form.add(username, password, submit);
        dialog.add(form);

        dialog.open();
    }
}

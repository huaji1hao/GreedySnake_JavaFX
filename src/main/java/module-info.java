module com {
    requires javafx.graphics;
    requires java.desktop;
    requires jlayer;

    // other modules
    opens com.megasnake to javafx.fxml;
    exports com.megasnake to javafx.graphics;
}

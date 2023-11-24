module com.megasnake {
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.media;

    // other modules
    opens com.megasnake to javafx.fxml;
    exports com.megasnake to javafx.graphics;
}

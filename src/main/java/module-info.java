module com.megasnake {
    requires javafx.graphics;
    requires java.desktop;
    requires jlayer;
    requires javafx.controls;

    // other modules
    opens com.megasnake to javafx.fxml;
    exports com.megasnake to javafx.graphics;
}

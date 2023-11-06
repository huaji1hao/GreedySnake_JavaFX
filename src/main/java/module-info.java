module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires jlayer;
    // 其他模块依赖

    opens com.megasnake to javafx.fxml;
//    exports snake.controller to javafx.fxml;
    exports com.megasnake to javafx.graphics;
//    exports snake.ui to javafx.graphics;
//    opens snake.ui to javafx.fxml;
//    exports snake.controller to javafx.graphics;
//    opens snake.controller to javafx.fxml;
}

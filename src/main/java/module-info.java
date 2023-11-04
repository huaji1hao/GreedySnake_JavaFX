module snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires jlayer;
    // 其他模块依赖

    opens snake to javafx.fxml;
//    exports snake.controller to javafx.fxml;
    exports snake to javafx.graphics;
}

module se233.project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires javafx.media;


    opens se233.project2 to javafx.fxml;
    exports se233.project2;
}
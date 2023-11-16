module software.engineering.contraptioncrack {
    requires javafx.controls;
    requires javafx.fxml;


    opens software.engineering.contraptioncrack to javafx.fxml;
    exports software.engineering.contraptioncrack;
    exports software.engineering.contraptioncrack.gameobjects;
    opens software.engineering.contraptioncrack.gameobjects to javafx.fxml;
    exports software.engineering.contraptioncrack.board;
    opens software.engineering.contraptioncrack.board to javafx.fxml;
    exports software.engineering.contraptioncrack.game;
    opens software.engineering.contraptioncrack.game to javafx.fxml;
}
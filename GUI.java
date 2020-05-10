import javafx.application.Application;

import javafx.stage.FileChooser;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class GUI extends Application {
  Text statusinfo;
  File valgtFil;
  Labyrint labyrint;
  Button rute;
  GridPane rutenett;
  Button stoppknapp;
  Button filKnapp;
  Button velgAnnenFil;
  Button utvei;

  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage stage){
    rutenett = new GridPane();
    stage.setTitle("Labyrintprogram AdrianEdit");

    // AVSLUTT PROGRAM
    stoppknapp = new Button("Avlutt");
    Stopp stopp = new Stopp();
    stoppknapp.setOnAction(stopp);

    //VELG FIL FUNKSJONALITET
    FileChooser velgFil = new FileChooser();
    velgFil.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.in"));
    filKnapp = new Button("Velg fil");

    //LESER AV FIL OG LAGER LABYRINTEN
    Finnutveien nyUtvei = new Finnutveien();
    filKnapp.setOnAction(nyttValg -> {
      valgtFil = velgFil.showOpenDialog(stage);
      try {
        labyrint = Labyrint.lesFraFil(valgtFil);
      } catch (FileNotFoundException f) {
        System.out.printf("FEIL: Kunne ikke lese fra '%s'\n" , valgtFil.getName());
        System.exit(1);
      }
      for (int r = 0; r<labyrint.rad; r++){
        for (int k=0; k<labyrint.kolonne; k++){

          if (labyrint.ruteArray[r][k].status == "#"){
            rute = new Button();
            rute.setStyle("-fx-background-color: black");
            rute.setDisable(true);
          }
          else{
            rute = new Button(k + " " + r); //"(" + k + "," + r + ")"
            rute.setStyle("-fx-background-color: white");
            rute.setStyle("-fx-border-color: grey");
          }
          rute.setMinSize(2, 2);
          rute.setMaxSize(70,50);
          rutenett.add(rute, k+10, r+10, 1 , 1);
          rute.setOnAction(nyUtvei);
        }
      }
    });
    // VELG NY FIL-KNAPP
    velgAnnenFil = new Button("Resett");
    Resett nyStart = new Resett();
    velgAnnenFil.setOnAction(nyStart);

    // LAG SCENEN
    rutenett.add(filKnapp, 1, 1, 1, 1);
    rutenett.add(stoppknapp, 3, 1, 1, 1);
    rutenett.add(velgAnnenFil, 2, 1, 1, 1);
    Scene scene = new Scene(rutenett);
    stage.setScene(scene);
    stage.show();
  }

  class Resett implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e){
      rutenett.getChildren().clear();
      rutenett.add(filKnapp, 1, 1, 1, 1);
      rutenett.add(stoppknapp, 3, 1, 1, 1);
      rutenett.add(velgAnnenFil, 2, 1, 1, 1);
    }
  }

  class Utveivelger implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e){
      /**
      * tanken her er å legge til en funksjonalitet som gjør at jeg kan velge mellom de forskjellige
      * utveien for vi finner. Jeg har klart å opprette en knapp for hver utvei som lages, nå er det bare
      * å gjøre slik at knappene viser hver enkelt utvei som er mulig.
      * dette krever nok flere forandringer til koden. Ikke minst at jeg begrenser hva som vises med en gang.
      * kanskje må jeg lagre hver utvei i en egen liste som hver knapp tar vare på.. hem hem
      */
      return;
    }
  }

  class Finnutveien implements EventHandler<ActionEvent>{
    @Override
    public void handle(ActionEvent e){
      String text = ((Button)e.getSource()).getText();
      String[] ord = text.split(" ");
      int startKol = Integer.parseInt(ord[0]);
      int startRad = Integer.parseInt(ord[1]);

      Liste<String> utveier = labyrint.finnUtveiFra(startKol, startRad);

      if (utveier.stoerrelse() != 0) {
        int teller = 0;
        for (String s : utveier) {
          teller++;
          utvei = new Button("Utvei: " + teller);
          Utveivelger nyUtvei = new Utveivelger();
          rutenett.add(utvei, 1, 1+teller, 1, 1);
          utvei.setOnAction(nyUtvei);

          String[] inndelt = s.split("@");
          for (int i =0; i<inndelt.length; i++){
            for(int knapp = 0; knapp < rutenett.getChildren().size(); knapp++){
              Button knappen = (Button)rutenett.getChildren().get(knapp);
              if(inndelt[i].equals(knappen.getText())){
                knappen.setStyle("-fx-background-color: #90ee90");
                knappen.setDisable(true);
              }
              else{
                System.out.print("");
              }
            }
          }
        }
      }
      else {
        System.out.print("");
      }
    }
  }

  class Stopp implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e){
      Platform.exit();
    }
  }
}

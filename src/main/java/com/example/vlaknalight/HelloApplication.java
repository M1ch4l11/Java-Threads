package com.example.vlaknalight;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.LinkedList;

public class HelloApplication extends Application {
    Thread vlakno,vlakno1,vlakno2;
    HBox hbo = new HBox(5);
    VBox vbo = new VBox(5);
    Button btnStart = new Button("HĽADAJ");
    TextField inputUserForDeveloper = new TextField();
    ProgressBar progressBar = new ProgressBar(0);
    TextArea infoForUser = new TextArea();
    Label time = new Label();
    Group root = new Group();
    VBox forBoxs = new VBox(5);
    int riadky,stlpce;
    Zemegula zem = new Zemegula();
    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        // zaciatok prveho boxu
        spustiCas();
        inputUserForDeveloper.setPromptText("Zadaj hodnotu ");
        btnStart.setOnAction(e-> {
            if(!inputUserForDeveloper.getText().equals("")) {
                spustiVlakno();
                btnStart.setDisable(true);
            }
        });
        time.setStyle("-fx-font-size: 18");
        hbo.getChildren().addAll(btnStart, inputUserForDeveloper,time);
        // zaciatok druheho boxu
        infoForUser.setPrefWidth(380);
        infoForUser.setEditable(false);
        infoForUser.setPromptText("Zatial sa nič nedeje.");
        progressBar.setPrefWidth(380);
        vbo.getChildren().addAll(infoForUser,progressBar);
        // the end
        forBoxs.setLayoutX(10);
        forBoxs.getChildren().addAll(hbo,vbo,zem);
        root.getChildren().add(forBoxs);
        stage.setTitle("VLÁKNA LIGHT");
        stage.setScene(new Scene(root, 400, 600));
        stage.show();
    }

    @Override
    public void stop(){
        if(vlakno1.isAlive())vlakno1.stop();
    }

    public void spustiCas(){
        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                startTime();
            }
        };
        vlakno1 = new Thread(run2);
        vlakno1.start();
    }

    public void spustiVlakno(){
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                citanieSuboru();
            }
        };
        vlakno = new Thread(run1);
        vlakno.start();
        Runnable run3 = new Runnable() {
            @Override
            public void run() {
                pustiZemegulu();
            }
        };
        vlakno2 = new Thread(run3);
        vlakno2.start();
    }

    public void pustiZemegulu(){
        while (true){
            try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    zem.vykresli();
                }
            });
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startTime() {
        for (int i = 1; i > 0; i++) {
            try {
                int pomI = i;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        time.setText("Time: "+pomI);
                    }
                });
                Thread.sleep(1000);
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }



    public void citanieSuboru(){
        try {
            BufferedReader Br = new BufferedReader(new FileReader("Data.txt"));
            String pom = "";
            String retazecNaPrehladavanie = inputUserForDeveloper.getText();
            int riadok = 1;
            while ((pom = Br.readLine()) != null){
                progressBar.setProgress(progressBar.getProgress()+0.12);
                for (int i = 0; i < pom.length(); i++) {
                    if(pom.substring(i,i+1).equals(retazecNaPrehladavanie.substring(0,1))){
                        if(i+retazecNaPrehladavanie.length()>pom.length());
                        else if (pom.substring(i,i+retazecNaPrehladavanie.length()).equals(retazecNaPrehladavanie)){
                            if(retazecNaPrehladavanie.length()>1) infoForUser.setText(infoForUser.getText()+" Retazec \"" + retazecNaPrehladavanie + "\" sa nachádza na " + riadok+ " riadku, stlpcoch " + i + " - " + (i+retazecNaPrehladavanie.length()) + "\n");
                            else infoForUser.setText(infoForUser.getText()+" Retazec \"" + retazecNaPrehladavanie + "\" sa nachádza na " + riadok+ " riadku, stlpci " + i +"\n");
                        }
                    }
                }
                riadok++;
                Thread.sleep(1000);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        infoForUser.setText(infoForUser.getText()+"\n " + " This is the END.");
        if(vlakno2.isAlive()){
            zem.vymaz();
            vlakno2.stop();
        }
        if(vlakno.isAlive())vlakno.stop();

    }

    public static void main(String[] args) {
        launch();
    }
}
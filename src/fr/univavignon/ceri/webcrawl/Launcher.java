package fr.univavignon.ceri.webcrawl;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class is used to launch the game.
 * 
 * @author Youssef ABIDAR
 * @author Imane HACEN
 * @author Mohamed KHARCHOUF
 * @author Abdelhakim RASFI
 * 
 */
public class Launcher extends Application{	
	/**
	 * Launches the game.
	 * 
	 * @param args
	 * 		Not used here.
	 */
	
	Button demarrer_button3;
	
	Spinner<Integer> temps_heure, temps_minute, temps_seconde;
	
	@Override
	public void start(Stage primaryStage){
		Pane cibles = new Pane();
		Pane param = new Pane();
		Pane demar = new Pane();
		
		Font f1 = new Font("Arial", 15);
		Font f2 = new Font("Arial", 20);
		
		// PARTIE 1
		
		DropShadow enfonce = new DropShadow();
		
		Button cibles_button1 = new Button("Cibles");
		cibles_button1.setLayoutX(50);
		cibles_button1.setLayoutY(500);
		
		cibles_button1.setEffect(enfonce);
		
		Button param_button1 = new Button("Cibles");
		param_button1.setLayoutX(50);
		param_button1.setLayoutY(500);
		
		Button demarrer_button1 = new Button("Cibles");
		demarrer_button1.setLayoutX(50);
		demarrer_button1.setLayoutY(500);
		
		// PARTIE 2
		
		Button cibles_button2 = new Button("Paramètres généraux");
		cibles_button2.setLayoutX(150);
		cibles_button2.setLayoutY(500);
		
		Button param_button2 = new Button("Paramètres généraux");
		param_button2.setLayoutX(150);
		param_button2.setLayoutY(500);
		
		param_button2.setEffect(enfonce);
		
		Button demarrer_button2 = new Button("Paramètres généraux");
		demarrer_button2.setLayoutX(150);
		demarrer_button2.setLayoutY(500);
		
		// PARTIE 3
		
		Button cibles_button3 = new Button("Démarrer");
		cibles_button3.setLayoutX(350);
		cibles_button3.setLayoutY(500);
		
		Button param_button3 = new Button("Démarrer");
		param_button3.setLayoutX(350);
		param_button3.setLayoutY(500);
		
		demarrer_button3 = new Button("Arreter");
		demarrer_button3.setLayoutX(350);
		demarrer_button3.setLayoutY(500);
		
		demarrer_button3.setEffect(enfonce);
		
		Button demarrer_button4 = new Button("Démarrer");
		demarrer_button4.setLayoutX(350);
		demarrer_button4.setLayoutY(500);
		demarrer_button4.setVisible(false);
		
		demarrer_button4.setEffect(enfonce);
		
		/////////////////// CIBLES ///////////////////
			
		// PARTIE 4
		
		ListView<String> liste = new ListView<String>();
		liste.getItems().add("Cible n°1");
		liste.getItems().add("Cible n°2");
		liste.getItems().add("Cible n°3");
		liste.getItems().add("Cible n°4");
		liste.getItems().add("Cible n°5");
		liste.setLayoutX(50);
		liste.setLayoutY(20);
		
		// PARTIE 5
		
		Button ajouter_cible = new Button("Ajouter cible");
		ajouter_cible.setLayoutX(80);
		ajouter_cible.setLayoutY(450);
		
		ajouter_cible.setOnAction(e -> {
			TextInputDialog input = new TextInputDialog();
			input.setTitle("Ajouter cible");
			input.setHeaderText(null);
			input.setContentText("Quel est l'URL de la cible ?");
			Optional<String> url = input.showAndWait();
			if (url.isPresent()){ // ne rien ajouter si l'utilisateur a cliqué sur "Annuler"
				liste.getItems().add(url.get());
			}
		});
		
		// PARTIE 6
		
		Button supprimer_cible = new Button("Supprimer cible");
		supprimer_cible.setLayoutX(250);
		supprimer_cible.setLayoutY(450);
		
		supprimer_cible.setOnAction(e -> {
			int n;
			n = liste.getSelectionModel().getSelectedIndex();
			if(n == -1){
				Alert erreur = new Alert(AlertType.WARNING);
				erreur.setTitle("Erreur suppression cible");
				erreur.setHeaderText(null);
				erreur.setContentText("Vous n'avez pas choisi la cible à supprimer !");
				erreur.showAndWait();
			} else {
				liste.getItems().remove(n);
			}
		});
		
		// Ajout cibles
		
		cibles.getChildren().add(liste);
		
		cibles.getChildren().add(ajouter_cible);
		cibles.getChildren().add(supprimer_cible);
		
		cibles.getChildren().add(cibles_button1);
		cibles.getChildren().add(cibles_button2);
		cibles.getChildren().add(cibles_button3);
		
		/////////////////// PARAMETRES AVANCES ///////////////////
			
		// PARTIE 7 
		
		Label label_noeud = new Label("Type de noeud :");
		label_noeud.setFont(f1);
		label_noeud.setLayoutX(50);
		label_noeud.setLayoutY(20);
		
		ComboBox<String> noeud = new ComboBox<String>();
		noeud.getItems().add("Page");
		noeud.getItems().add("Domaine");
		noeud.setLayoutX(230);
		noeud.setLayoutY(20);
		
		// LIMITES
		
		Label label_limites = new Label("Limites");
		label_limites.setFont(f2);
		label_limites.setLayoutX(100);
		label_limites.setLayoutY(60);
		
		Line line1_limites = new Line();
		line1_limites.setStartX(30);
		line1_limites.setStartY(70); 
		line1_limites.setEndX(30);
		line1_limites.setEndY(240);
		
		Line line2_limites = new Line();
		line2_limites.setStartX(30);
		line2_limites.setStartY(240); 
		line2_limites.setEndX(420);
		line2_limites.setEndY(240);
		
		Line line3_limites = new Line();
		line3_limites.setStartX(420);
		line3_limites.setStartY(240); 
		line3_limites.setEndX(420);
		line3_limites.setEndY(70);
		
		Line line4_limites = new Line();
		line4_limites.setStartX(420);
		line4_limites.setStartY(70); 
		line4_limites.setEndX(180);
		line4_limites.setEndY(70);
		
		Line line5_limites = new Line();
		line5_limites.setStartX(80);
		line5_limites.setStartY(70); 
		line5_limites.setEndX(30);
		line5_limites.setEndY(70);
		
		// PARTIE 8
		
		
		Label label_rayon = new Label("Rayon :");
		label_rayon.setFont(f1);
		label_rayon.setLayoutX(50);
		label_rayon.setLayoutY(100);
		
		SpinnerValueFactory<Integer> modelRayon = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 3); // min, max, nombre initiale
		
		Spinner<Integer> rayon = new Spinner<Integer>();
		rayon.setValueFactory(modelRayon);
		rayon.setLayoutX(230);
		rayon.setLayoutY(100);
		
		// PARTIE 9
		
		Label label_temps = new Label("Temps :");
		label_temps.setFont(f1);
		label_temps.setLayoutX(50);
		label_temps.setLayoutY(150);
		
		SpinnerValueFactory<Integer> modelHeure = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
		SpinnerValueFactory<Integer> modelMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
		SpinnerValueFactory<Integer> modelSeconde = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
		
		temps_heure = new Spinner<Integer>();
		temps_heure.setValueFactory(modelHeure);
		temps_heure.setMaxSize(55, 55);
		temps_heure.setLayoutX(180);
		temps_heure.setLayoutY(150);
		
		Label label_temps_h = new Label("h");
		label_temps_h.setFont(f1);
		label_temps_h.setLayoutX(240);
		label_temps_h.setLayoutY(155);
		
		temps_minute = new Spinner<Integer>();
		temps_minute.setValueFactory(modelMinute);
		temps_minute.setMaxSize(55, 55);
		temps_minute.setLayoutX(250);
		temps_minute.setLayoutY(150);
		
		Label label_temps_m = new Label("m");
		label_temps_m.setFont(f1);
		label_temps_m.setLayoutX(307);
		label_temps_m.setLayoutY(155);
		
		temps_seconde = new Spinner<Integer>();
		temps_seconde.setValueFactory(modelSeconde);
		temps_seconde.setMaxSize(55, 55);
		temps_seconde.setLayoutX(320);
		temps_seconde.setLayoutY(150);
				
		Label label_temps_s = new Label("s");
		label_temps_s.setFont(f1);
		label_temps_s.setLayoutX(380);
		label_temps_s.setLayoutY(155);
		
		// PARTIE 10
		
		Label label_domaine = new Label("Rester dans le domaine :");
		label_domaine.setFont(f1);
		label_domaine.setLayoutX(50);
		label_domaine.setLayoutY(200);
		
		CheckBox domaine = new CheckBox();
		domaine.setLayoutX(250);
		domaine.setLayoutY(200);
		
		noeud.valueProperty().addListener(e -> {
			if(noeud.getSelectionModel().getSelectedIndex() == 1){
				domaine.setSelected(false);
				domaine.setDisable(true);
			} else {
				domaine.setDisable(false);
			}
		});
		
		// PARTIE 11
		
		Label label_processus = new Label("Nombre de processus :");
		label_processus.setFont(f1);
		label_processus.setLayoutX(50);
		label_processus.setLayoutY(300);
		
		SpinnerValueFactory<Integer> modelProcessus = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		Spinner<Integer> processus = new Spinner<Integer>();
		processus.setValueFactory(modelProcessus);
		processus.setLayoutX(230);
		processus.setLayoutY(300);
		
		// PARTIE 12
		
		Label label_robot = new Label("Respect de robot.txt :");
		label_robot.setFont(f1);
		label_robot.setLayoutX(50);
		label_robot.setLayoutY(350);
		
		CheckBox robot = new CheckBox();
		robot.setLayoutX(250);
		robot.setLayoutY(350);
		
		// PARTIE 13
		
		Label label_sitemap = new Label("Respect de sitemap.xml :");
		label_sitemap.setFont(f1);
		label_sitemap.setLayoutX(50);
		label_sitemap.setLayoutY(400);
		
		CheckBox sitemap = new CheckBox();
		sitemap.setLayoutX(250);
		sitemap.setLayoutY(400);
		
		// PARTIE 14
		
		Label label_remontee = new Label("Remontée :");
		label_remontee.setFont(f1);
		label_remontee.setLayoutX(50);
		label_remontee.setLayoutY(450);
		
		CheckBox remontee = new CheckBox();
		remontee.setLayoutX(250);
		remontee.setLayoutY(450);
		
		noeud.valueProperty().addListener(e -> {
			if(noeud.getSelectionModel().getSelectedIndex() == 1){
				remontee.setSelected(false);
				remontee.setDisable(true);
			} else {
				remontee.setDisable(false);
			}
		});
		
		////////////////////////////////////////////////////////////
				
		Scene scene1 = new Scene(cibles,450,550);
		Scene scene2 = new Scene(param,450,550);
		Scene scene3 = new Scene(demar,450,550);
		
		primaryStage.setTitle("Crawler");
		primaryStage.setScene(scene1);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		//TODO to be completed
		launch(args);
	}
}


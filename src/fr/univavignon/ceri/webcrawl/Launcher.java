package fr.univavignon.ceri.webcrawl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Timer;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Modality;
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
	
	Timer timer = new Timer();
	int hE = 0, mE = 0, sE = 0;
	int hR = 0, mR = 0, sR = 0;
	boolean control_temps;
	
	ProgressBar prog1;
	ProgressIndicator prog2;
	int prog_num = 0, prog_den;
	
	Label temps_ecoule;
	Label temps_restant;
	Button demarrer_button3;
	
	Label nb_threads;
	Label nb_URL;
	Label nb_noeuds;
	Label nb_liens;
	
	Spinner<Integer> temps_heure, temps_minute, temps_seconde;
	
	LineChart chart;
	XYChart.Series<Integer, Integer> g_noeud;
	XYChart.Series<Integer, Integer> g_liens;
	int g_n = 2, g_l = 2, nb_n = 0, nb_l = 0;
	int nb_t = 0, nb_u = 1;
	
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
		
		// Ajout paramètres avancés
		
		param.getChildren().add(label_noeud);
		param.getChildren().add(noeud);
		
		param.getChildren().add(label_limites);
		param.getChildren().add(line1_limites);
		param.getChildren().add(line2_limites);
		param.getChildren().add(line3_limites);
		param.getChildren().add(line4_limites);
		param.getChildren().add(line5_limites);
		
		param.getChildren().add(label_rayon);
		param.getChildren().add(rayon);
		
		param.getChildren().add(label_temps);
		param.getChildren().add(label_temps_h);
		param.getChildren().add(temps_heure);
		param.getChildren().add(label_temps_m);
		param.getChildren().add(temps_minute);
		param.getChildren().add(label_temps_s);
		param.getChildren().add(temps_seconde);
		
		param.getChildren().add(label_domaine);
		param.getChildren().add(domaine);
		
		param.getChildren().add(label_processus);
		param.getChildren().add(processus);
		
		param.getChildren().add(label_robot);
		param.getChildren().add(robot);
		
		param.getChildren().add(label_sitemap);
		param.getChildren().add(sitemap);
		
		param.getChildren().add(label_remontee);
		param.getChildren().add(remontee);
		
		param.getChildren().add(param_button1);
		param.getChildren().add(param_button2);
		param.getChildren().add(param_button3);
		
		/////////////////// DEMARRER ///////////////////
				
		// PARTIE 15
		
		Label label_nb_threads = new Label("Nombre de threads :");
		label_nb_threads.setFont(f1);
		label_nb_threads.setLayoutX(50);
		label_nb_threads.setLayoutY(20);
		
		nb_threads = new Label("" + nb_t);
		nb_threads.setFont(f1);
		nb_threads.setLayoutX(275);
		nb_threads.setLayoutY(20);
		
		// PARTIE 16
		
		Label label_nb_URL = new Label("Nombre d'URL traités :");
		label_nb_URL.setFont(f1);
		label_nb_URL.setLayoutX(50);
		label_nb_URL.setLayoutY(70);
		
		nb_URL = new Label("" + nb_u);
		nb_URL.setFont(f1);
		nb_URL.setLayoutX(275);
		nb_URL.setLayoutY(70);
		
		// GRAPHE
		
		Label label_graphe = new Label("Graphe");
		label_graphe.setFont(f2);
		label_graphe.setLayoutX(100);
		label_graphe.setLayoutY(120);
		
		Line line1_graphe = new Line();
		line1_graphe.setStartX(30);
		line1_graphe.setStartY(130); 
		line1_graphe.setEndX(30);
		line1_graphe.setEndY(260);
		
		Line line2_graphe = new Line();
		line2_graphe.setStartX(30);
		line2_graphe.setStartY(260); 
		line2_graphe.setEndX(420);
		line2_graphe.setEndY(260);
		
		Line line3_graphe = new Line();
		line3_graphe.setStartX(420);
		line3_graphe.setStartY(260); 
		line3_graphe.setEndX(420);
		line3_graphe.setEndY(130);
		
		Line line4_graphe = new Line();
		line4_graphe.setStartX(420);
		line4_graphe.setStartY(130); 
		line4_graphe.setEndX(185);
		line4_graphe.setEndY(130);
		
		Line line5_graphe = new Line();
		line5_graphe.setStartX(80);
		line5_graphe.setStartY(130);
		line5_graphe.setEndX(30);
		line5_graphe.setEndY(130);
		
		// PARTIE 17
		
		Label label_nb_noeud = new Label("Nombre de noeuds :");
		label_nb_noeud.setFont(f1);
		label_nb_noeud.setLayoutX(50);
		label_nb_noeud.setLayoutY(170);
		
		nb_noeuds = new Label("" + nb_n);
		nb_noeuds.setFont(f1);
		nb_noeuds.setLayoutX(275);
		nb_noeuds.setLayoutY(170);
		
		// PARTIE 18
		
		Label label_nb_lien = new Label("Nombre de liens :");
		label_nb_lien.setFont(f1);
		label_nb_lien.setLayoutX(50);
		label_nb_lien.setLayoutY(220);
		
		nb_liens = new Label("" + nb_l);
		nb_liens.setFont(f1);
		nb_liens.setLayoutX(275);
		nb_liens.setLayoutY(220);
		
		// PARTIE 19 et 23
		
		Button popup_button = new Button("\nGraphique\n ");
		popup_button.setLayoutX(320);
		popup_button.setLayoutY(170);
		
		Stage popup = new Stage();
		Pane pane_graphique = new Pane();
		Scene scene_graphique = new Scene(pane_graphique,500,400);
		
		popup.initModality(Modality.WINDOW_MODAL);
		popup.setTitle("Graphique");
		
		NumberAxis x_temps = new NumberAxis();
		x_temps.setLabel("temps (en minutes)");

		NumberAxis y_nombre = new NumberAxis();
		y_nombre.setLabel("nombre");
		
		chart = new LineChart(x_temps, y_nombre);
		
		g_noeud = new XYChart.Series<Integer, Integer>();
		g_liens = new XYChart.Series<Integer, Integer>();
		
		g_noeud.setName("Noeud");
		//g_noeud.getData().add(new XYChart.Data<Integer, Integer>(0, 1));
		
		g_liens.setName("Liens");
		//g_liens.getData().add(new XYChart.Data<Integer, Integer>(1, 0));
		
		chart.getData().add(g_noeud);
		chart.getData().add(g_liens);
		
		pane_graphique.getChildren().add(chart);
		
		popup.setScene(scene_graphique);
		popup.sizeToScene();
		popup.setResizable(false);

		
		popup_button.setOnAction(e -> {			
			popup.show();
		});
		
		chart.setOnMouseClicked(e -> {
			MouseButton button = e.getButton();
            
			if(button == MouseButton.SECONDARY){
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY_MM_dd_HH_mm_ss");  
				LocalDateTime now = LocalDateTime.now();
				
				WritableImage image = scene_graphique.snapshot(null);
				
				File fichier = new File(dtf.format(now) + ".png");
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", fichier);
				} catch (IOException e1){
					e1.printStackTrace();
				}
            }
		});
		
		// TEMPS
		
		Label label_temps_dem = new Label("Temps");
		label_temps_dem.setFont(f2);
		label_temps_dem.setLayoutX(100);
		label_temps_dem.setLayoutY(280);
		
		Line line1_temps = new Line();
		line1_temps.setStartX(30);
		line1_temps.setStartY(290); 
		line1_temps.setEndX(30);
		line1_temps.setEndY(420);
		
		Line line2_temps = new Line();
		line2_temps.setStartX(30);
		line2_temps.setStartY(420); 
		line2_temps.setEndX(420);
		line2_temps.setEndY(420);
		
		Line line3_temps = new Line();
		line3_temps.setStartX(420);
		line3_temps.setStartY(420); 
		line3_temps.setEndX(420);
		line3_temps.setEndY(290);
		
		Line line4_temps = new Line();
		line4_temps.setStartX(420);
		line4_temps.setStartY(290); 
		line4_temps.setEndX(180);
		line4_temps.setEndY(290);
		
		Line line5_temps = new Line();
		line5_temps.setStartX(80);
		line5_temps.setStartY(290);
		line5_temps.setEndX(30);
		line5_temps.setEndY(290);
					
		
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


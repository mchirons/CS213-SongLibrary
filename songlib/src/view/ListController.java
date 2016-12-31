/*
 * Mark Hirons
 * Andre Pereira
 */

package view;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import model.Song;
import application.SongLib;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public class ListController {
	@FXML
	  	ListView<Song> listView;
	@FXML
		Label titleLabel;
	@FXML
		Label artistLabel;
	@FXML
		Label albumLabel;
	@FXML
		Label yearLabel;
	@FXML
		Button addButton;
	@FXML
		Button editButton;
	@FXML
		Button deleteButton;

	private SongLib mainApp;




	public ListController(){

	}

	@FXML
	private void initialize() {

		showSongDetails(listView.getSelectionModel().getSelectedItem());

		listView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);

		listView.getSelectionModel().selectedItemProperty().addListener
		((obs, oldVal, newVal) -> showSongDetails(newVal));

          //mainApp.getPrimaryStage().close();
	}

	public void setMainApp(SongLib mainApp) {
        this.mainApp = mainApp;

        listView.setItems( mainApp.getSongList());
        listView.getSelectionModel().select(0);
        mainApp.setListController(this);
        mainApp.getPrimaryStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                //System.out.println("Stage is closing");
                mainApp.saveLibrary();

            }
        });
    }

	private void showSongDetails(Song song){
		if (song == null){
			titleLabel.setText("");
			artistLabel.setText("");
			albumLabel.setText("");
			yearLabel.setText("");
		}
		else{
			titleLabel.setText(song.getTitle());
			artistLabel.setText(song.getArtist());
			albumLabel.setText(song.getAlbum());
			yearLabel.setText(song.getYear());
		}
	}

	 @FXML
	 private void handleDeleteSong() {
		 int selectedIndex = listView.getSelectionModel().getSelectedIndex();
	     if (selectedIndex >= 0) {

	    	 Alert alert = new Alert(AlertType.CONFIRMATION);
	    	 alert.setTitle("Confirmation Window");
	    	 alert.setHeaderText("You are about to delete this song.");
	    	 alert.setContentText("Are you ok with this?");

	    	 Optional<ButtonType> result = alert.showAndWait();
	    	 if (result.get() == ButtonType.OK){
	    		 listView.getItems().remove(selectedIndex);
    	    	 listView.getSelectionModel().select(selectedIndex);
	    	 } else {
	    	     // ... user chose CANCEL or closed the dialog
	    	 }

	     }else{
	            // Nothing selected.
	            Alert alert2 = new Alert(AlertType.WARNING);
	            alert2.initOwner(mainApp.getPrimaryStage());
	            alert2.setTitle("No Selection");
	            alert2.setHeaderText("No Song Selected");
	            alert2.setContentText("Please select a song to delete.");

	            alert2.showAndWait();
	        }
	    }


	 //this is where I am getting the stupid selection problem
	 @FXML
	 private void handleAddSong() {
	        Song tempSong = new Song();
	        boolean okClicked = mainApp.showEditSongDialog(tempSong);

	        if (okClicked) {
	            mainApp.addSong(tempSong);
	            listView.setItems( mainApp.getSongList());
	            mainApp.setNewSelection(tempSong);
	        }
	    }
	 //also here
	 @FXML
	 private void handleEditSong() {
	        Song selectedSong = listView.getSelectionModel().getSelectedItem();
	        if (selectedSong != null) {
	            boolean okClicked = mainApp.showEditSongDialog(selectedSong);
	            if (okClicked) {
	               showSongDetails(selectedSong);
	               FXCollections.sort(mainApp.getSongList());
	               listView.setItems( mainApp.getSongList());
	               mainApp.setNewSelection(selectedSong);
	            }

	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("No Selection");
	            alert.setHeaderText("No Song Selected");
	            alert.setContentText("Please select a song to edit.");

	            alert.showAndWait();
	        }
	 }

	 public ListView<Song> getListView(){
		 return this.listView;
	 }





}

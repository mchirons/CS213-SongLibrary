/*
 * Mark Hirons
 * Andre Pereira
 */

package view;




import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Song;
import application.SongLib;



public class SongAddDialogController {

	@FXML
	private TextField titleField;
	@FXML
	private TextField artistField;
	@FXML
	private TextField albumField;
	@FXML
	private TextField yearField;


	private Stage addSongDialogStage;
	private Song song;
	private SongLib mainApp;
	private boolean okClicked = false;

	@FXML
	private void initialize(){

	}

	public void setAddDialogStage(Stage addSongDialogStage){
		this.addSongDialogStage = addSongDialogStage;


		//Button okButton = new Button();
		//okButton.addEventHandler(ActionEvent.ACTION, event -> handleOk());

		//Button cancelButton = new Button();
		//cancelButton.addEventHandler(ActionEvent.ACTION, event -> handleCancel());

	}

	public void setMainApp(SongLib mainApp) {
        this.mainApp = mainApp;
    }

	public void setSong(Song song){
		this.song = song;

		titleField.setText(song.getTitle());
		artistField.setText(song.getArtist());
		albumField.setText(song.getAlbum());
		yearField.setText(song.getYear());
	}

	@FXML
	private void handleOk(){
		if (inputIsValid()){

			song.setTitle(titleField.getText());
			song.setArtist(artistField.getText());
			song.setAlbum(albumField.getText());
			song.setYear(yearField.getText());

			addSongDialogStage.close();

			okClicked = true;

		}
	}

	 @FXML
	    private void handleCancel() {
	        addSongDialogStage.close();
	    }


	 /*
	  * Something is off about the way that this is run.
	  * Whenever you try to add something to the list that it
	  * already has, it only correctly
	  * checks after the first pass. Otherwise, if you try to add again,
	  * it'll just say that the songList doesn't have the song and add it anyway.
	  *
	  * Appears added in GUI, but if you look into the actual array, the size
	  * incremented by only one no matter how many times you added to the list.
	  *
	  * I'm guessing this also means that editing doesn't pan out well either.
	  *
	  */
	private boolean inputIsValid(){
		String error = "";
		Song temp = new Song(titleField.getText(), artistField.getText());

		if (titleField.getText() == null || titleField.getText().length() == 0){
			error = error + "You must enter a song title\n";

		}
		if (artistField.getText() == null || artistField.getText().length() == 0){
			error = error + "You must enter an artist name\n";

		}
		if (error.length() == 0 && !(song.getTitle() == "")){
			if(song.getTitle().equalsIgnoreCase(temp.getTitle()) && song.getArtist().equalsIgnoreCase(temp.getArtist())){
				return true;
			}
			if (mainApp.getSongList().contains(temp)){
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(addSongDialogStage);
		        alert.setTitle("Song already exists");
		        alert.setHeaderText("Please input either a different artist or a different title.");
		        alert.setContentText(error);
			}
			else {
				//nothing
			}
		}
		if (error.length() == 0 && !mainApp.getSongList().contains(temp)){
			return true;
		}
		else{
			if (mainApp.getSongList().contains(temp)){
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(addSongDialogStage);
		        alert.setTitle("Song already exists");
		        alert.setHeaderText("Please input either a different artist or a different title.");
		        alert.setContentText(error);

		        alert.showAndWait();
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(addSongDialogStage);
				alert.setTitle("Incorrect Input");
				alert.setHeaderText("Please input both an artist and a title.");
				alert.setContentText(error);

				alert.showAndWait();
			}
		}
		return false;
	}

	public boolean isOkClicked() {
        return okClicked;
    }


}

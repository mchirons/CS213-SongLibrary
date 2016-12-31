/*
 * Mark Hirons
 * Andre Pereira
 */

package application;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.ListController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import view.SongAddDialogController;
import model.Song;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.stage.Modality;
import java.util.ArrayList;


import java.io.File;

public class SongLib extends Application implements Serializable{

	/**
	 *
	 */
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Song> songList = FXCollections.observableArrayList();
	private ListController LC;
	private File saveFile;






	public SongLib(){
		this.saveFile = new File("library.txt");

		ArrayList<Song> temp;
		try {
		    FileInputStream fis = new FileInputStream(saveFile);
		    ObjectInputStream ois = new ObjectInputStream(fis);
		    temp = (ArrayList<Song>)ois.readObject();
		    ois.close();
		    songList.addAll(temp);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

	public void addSong(Song song)
	{
		this.songList.add(song);
		FXCollections.sort(songList);
	}



	public ObservableList<Song> getSongList(){
		return songList;
	}

	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Song Library");

        initRootLayout();

        showSongListOverview();
    }

	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void showSongListOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SongList.fxml"));
            AnchorPane songListOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(songListOverview);

            // Give the controller access to the main app.
            ListController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public boolean showEditSongDialog(Song song) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        //ObservableList<Song> temp = getSongList();
	        loader.setLocation(getClass().getResource("/view/SongAddDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage addSongDialogStage = new Stage();
	        addSongDialogStage.setTitle("Add/Edit Song");
	        addSongDialogStage.initModality(Modality.WINDOW_MODAL);
	        addSongDialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        addSongDialogStage.setScene(scene);

	        // Set the person into the controller.
	        SongAddDialogController controller = loader.getController();
	        controller.setAddDialogStage(addSongDialogStage);
	        controller.setSong(song);
	        controller.setMainApp(this);

	        // Show the dialog and wait until the user closes it
	        addSongDialogStage.showAndWait();
	        showSongListOverview();
	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public Stage getPrimaryStage(){
		return primaryStage;
	}

	public void setListController(ListController controller){
		this.LC = controller;
	}

	public void setNewSelection(Song song){
		LC.getListView().getSelectionModel().select(getIndex(song));
	}

	public int getIndex(Song song){
			for (int i = 0; i < songList.size(); i++){
				if (song.equals(songList.get(i))){
					return i;
				}
			}
			return 0;
	}

	public void saveLibrary(){
		ArrayList<Song> temp = new ArrayList<Song>();
		temp.addAll(songList);
		try {
		    FileOutputStream fos = new FileOutputStream(saveFile);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(temp);
		    oos.flush();
		    oos.close();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}

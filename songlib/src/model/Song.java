/*
 * Mark Hirons
 * Andre Pereira
 */

package model;

import java.util.NoSuchElementException;

import java.io.IOException;
import java.io.Serializable;


public class Song implements Comparable<Song>, java.io.Serializable
{
	private String title;
	private String artist;
	private String album;
	private String year;



	public Song()
	{
		this.title = "";
		this.artist = "";
		this.album = "";
		this.year = "";
	}

	public Song(String title, String artist)
	{
		this.title = title;
		this.artist = artist;
		this.album = "";
		this.year = "";
	}

	public Song(String title, String artist, String album)
	{
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = "";
	}

	public Song(String title, String artist, String album, String year)
	{
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	/*
	 * To save items into arraylist, a writeObject and readObject method
	 * had to be created.
	 */
	private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
	{
        stream.writeObject(title);
        stream.writeObject(artist);
        stream.writeObject(album);
        stream.writeObject(year);
    }

	private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        title = (String) stream.readObject();
        artist = (String) stream.readObject();
        album = (String) stream.readObject();
        year = (String) stream.readObject();
    }

	public String getTitle(){ return title;}
	public String getArtist(){ return artist;}
	public String getAlbum(){ return album;}
	public String getYear(){ return year;}

	public void setTitle(String title){ this.title = title;}
	public void setArtist(String artist){ this.artist = artist;}
	public void setAlbum(String album){ this.album = album;}
	public void setYear(String year){ this.year = year;}

	@Override
	 public int compareTo(Song other) {
	        String compareTitle=((Song)other).getTitle();
	        /* For Ascending order*/
	        return this.title.compareToIgnoreCase(compareTitle);

	        /* For Descending order do like this */
	        //return compareTitle.compareToIgnoreCase(this.title);
	    }

	@Override
	public boolean equals(Object o)
	{
		 if (o == null || !(o instanceof Song)) { return false; }
		 Song other = (Song)o;
		 if( other.getTitle().equalsIgnoreCase(this.getTitle()) && other.getArtist().equalsIgnoreCase(this.getArtist())) return true;
		 else return false;
	}
	@Override
	public String toString()
	{

		return getTitle();

	}
}

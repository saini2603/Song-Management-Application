
///Name: Rohit Saini
//ID: 2122294
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;


public class Song implements Comparable<Song>{


    private static   int nextId = 1;
    public int ID;

    public String title;
    public String artist;
    public int duration;

    public Song(){

    }

    public Song(int ID,String title, String artist,int duration) {

        this.ID =ID;
        this.title = title;
        this.artist = artist;
        this.duration= duration;
    }

    public Song(String title, String artist,int duration) {

        String filename= SongManager.selectFile();
        LinkedList<Song> songLinkedList = (LinkedList<Song>) SongLoader.loadCSV(filename);
        Comparator<Song> comparator = new Song.ComparatorId();
        songLinkedList.sort(comparator);

        this.ID = songLinkedList.getLast().getID() +nextId;
        this.title = title;
        this.artist = artist;
        this.duration= duration;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getID() {
        return ID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return (ID==song.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return ID +","+title +","+ artist+","+ duration+"\n";
    }

    @Override
    public int compareTo(Song o) {
        return Integer.compare(this.ID, o.ID);
    }

    public static class ComparatorId implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return o1.compareTo(o2);
        }
    }

    public static class ComparatorTitle implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return o1.title.compareTo(o2.title);
        }
    }

    public static class ComparatorArtist implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return o1.artist.compareTo(o2.artist);
        }
    }
    public static class ComparatorDuration implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return Integer.compare(o1.duration,o2.duration);
        }
    }

    public static class ComparatorDescendingID implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return - o1.compareTo(o2);
        }
    }


    public static class ComparatorDescendingTitle implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return -o1.title.compareTo(o2.title);
        }
    }

    public static class ComparatorDescendingArtist implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return -o1.artist.compareTo(o2.artist);
        }
    }
    public static class ComparatorDescendingDuration implements Comparator<Song> {

        @Override
        public int compare(Song o1, Song o2) {
            return -Integer.compare(o1.duration,o2.duration);
        }
    }

}

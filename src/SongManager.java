import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;


public class SongManager extends JFrame {

    Map<String,LinkedList<Song>> mMap = new HashMap<String, LinkedList<Song>>();
    private LinkedList<Song> list = new LinkedList<Song>();
    private LinkedList<Song> songLinkedList1 = new LinkedList<Song>();
    private LinkedList<Song> songLinkedList2 = new LinkedList<Song>();

    private JLabel id = new JLabel("Song ID:");
    private JTextField textFieldID = new JTextField(10);
    private JLabel title = new JLabel("Title:");
    private JTextField textFieldTitle = new JTextField(10);
    private JLabel artist = new JLabel("Artist:");
    private JTextField textFieldArtist = new JTextField(10);
    private JLabel Duration = new JLabel("Duration:");
    private JTextField textFieldDuration = new JTextField(10);
    private JButton buttonList = new JButton("List");



    private JButton buttonAdd = new JButton("Add");
    private JButton buttonDelete = new JButton("Delete");
    private JLabel labelSource = new JLabel("Source Index:");
    private JTextField textFieldSource = new JTextField(10);
    private JLabel labelDestination = new JLabel("Destination Index:");
    private JTextField textFieldDestination = new JTextField(10);
    private JButton buttonMove = new JButton("Move");
    private JButton buttonSort = new JButton("Sort");
    private JButton buttonExit = new JButton("Exit");
    private JButton buttonReset = new JButton("Reset");
    private JTable table1 = new JTable();
    private JScrollPane pane = new JScrollPane(table1);

    Object[] Table_values = {"Index", "ID", "Title", "Artist", "Duration"};
    Object[] row = new Object[5];


    static String[] values = {"Select Field", "With ID", "With Title", "With Artist", "With Duration"};
    private static JComboBox<String> SField = new JComboBox<>(values);
    static String[] values1 = {"Select Order", "Ascending Order", "Descending Order"};
    private static JComboBox<String> SOrder = new JComboBox<>(values1);


    static String[] selectFile = {"Select file", "songs_short.csv", "songs_long.csv"};
    private static JComboBox<String> selFile = new JComboBox<>(selectFile);
    String[] selectList = {"Select List", "songList", "songList1", "songList2"};
    private JComboBox<String> selList = new JComboBox<>(selectList);

    private static JFrame parentJFrame;
    DefaultTableModel model = new DefaultTableModel();


    public SongManager() {
        parentJFrame = new JFrame();

        model.setColumnIdentifiers(Table_values);
        table1.setModel(model);
        table1.setBackground(Color.PINK);
        table1.setForeground(Color.BLUE);
        table1.setSelectionBackground(Color.GREEN);
        table1.setGridColor(Color.MAGENTA);
        table1.setFont(new Font("Tahoma", Font.BOLD, 17));
        table1.setRowHeight(30);
        table1.setAutoCreateRowSorter(true);

        pane.setForeground(Color.BLUE);
        pane.setBackground(Color.WHITE);
        pane.setBounds(50, 400, 750, 400);
        pane.setVisible(true);
        parentJFrame.add(pane);

        selList.setBounds(60, 60, 120, 30);
        parentJFrame.add(selList);

        selFile.setBounds(60, 10, 120, 30);
        parentJFrame.add(selFile);


        SField.setBounds(60, 230, 120, 30);
        parentJFrame.add(SField);
        SOrder.setBounds(60, 280, 120, 30);
        parentJFrame.add(SOrder);



        title.setBounds(500, 10, 90, 30);
        parentJFrame.add(title);
        textFieldTitle.setBounds(580, 10, 90, 30);
        parentJFrame.add(textFieldTitle);
        artist.setBounds(500, 70, 90, 30);
        parentJFrame.add(artist);
        textFieldArtist.setBounds(580, 70, 90, 30);
        parentJFrame.add(textFieldArtist);
        Duration.setBounds(500, 130, 90, 30);
        parentJFrame.add(Duration);
        textFieldDuration.setBounds(580, 130, 90, 30);
        parentJFrame.add(textFieldDuration);

        id.setBounds(500, 250, 90, 30);
        parentJFrame.add(id);
        textFieldID.setBounds(580, 250, 90, 30);
        parentJFrame.add(textFieldID);

        buttonMove.setBounds(70, 30, 90, 30);


        buttonList.setBounds(70, 130, 90, 30);
        parentJFrame.add(buttonList);
        buttonAdd.setBounds(700, 70, 90, 30);
        parentJFrame.add(buttonAdd);
        buttonDelete.setBounds(700, 250, 90, 30);
        parentJFrame.add(buttonDelete);
        buttonReset.setBounds(640, 810, 90, 30);
        parentJFrame.add(buttonReset);
        labelSource.setBounds(250, 10, 90, 30);
        parentJFrame.add(labelSource);
        labelDestination.setBounds(250, 70, 120, 30);
        parentJFrame.add(labelDestination);
        textFieldSource.setBounds(380, 10, 90, 30);
        parentJFrame.add(textFieldSource);
        textFieldDestination.setBounds(380, 70, 90, 30);
        parentJFrame.add(textFieldDestination);
        buttonMove.setBounds(320, 130, 90, 30);
        parentJFrame.add(buttonMove);
        buttonSort.setBounds(320, 250, 90, 30);
        parentJFrame.add(buttonSort);
        buttonExit.setBounds(750, 810, 90, 30);
        parentJFrame.add(buttonExit);

        buttonAdd.addActionListener(event -> {
            try {
                addSong();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonList.addActionListener(event -> List());

        buttonMove.addActionListener(event -> {
            try {
                Move();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonReset.addActionListener(event -> Reset());

        buttonExit.addActionListener(event -> exit());
        buttonSort.addActionListener(event -> {
            try {
                Sort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttonDelete.addActionListener(event -> {
            try {
                Delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private boolean isSongIdInLinkedList(String idStr) {
        boolean isIdUnique = false;
        String filename = selectFile();
        list = (LinkedList<Song>) SongLoader.loadCSV(filename);
        assert list != null;
        for (Song sng : list) {
            if (String.valueOf(sng.getID()).compareToIgnoreCase(idStr) == 0) {
                isIdUnique = true;
                break;
            }
        }
        return isIdUnique;
    }
    static boolean isNumber(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;

        return true;
    }

    public static String selectFile() {

        return selFile.getSelectedItem().toString();
    }

    public static String selectField() {

        return SField.getSelectedItem().toString();
    }

    public static String selectOrder() {

        return SOrder.getSelectedItem().toString();
    }


    private void addSong() throws IOException {
        LinkedList<Song> maplist=new LinkedList<>();
        String filename = selectFile();
        if(selList.getSelectedItem()=="songList") {
            list = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist  = this.list;
        }

        else if(selList.getSelectedItem()=="songList1") {
            songLinkedList1 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList1;
        }
        else if(selList.getSelectedItem()=="songList2") {
            songLinkedList2 = (LinkedList<Song>) SongLoader.loadCSV(filename);

            maplist = songLinkedList2;
        }
        String title = textFieldTitle.getText();
        String artist = textFieldArtist.getText();
        String Duration = textFieldDuration.getText();

        if (!title.isEmpty() && !artist.isEmpty() && !Duration.isEmpty() && isNumber(Duration) && artist.matches("[a-z A-Z_]+") && title.matches("[a-z A-Z_]+")) {

            maplist.clear();
            maplist.add(new Song(title, artist, Integer.parseInt(textFieldDuration.getText())));
            SongLoader.saveCSV(maplist, filename);
            maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
            mMap.put(filename,maplist);
            Reset();
            assert maplist != null;
            for (Song sng : mMap.get(filename)) {
                row[0] = maplist.indexOf(sng);
                row[1] = sng.getID();
                row[2] = sng.getTitle();
                row[3] = sng.getArtist();
                row[4] = sng.getDuration();
                model.addRow(row);

            }
            JOptionPane.showMessageDialog(parentJFrame, "Song added successfully.");
            textFieldTitle.setText("");
            textFieldArtist.setText("");
            textFieldDuration.setText("");
        } else {

            JOptionPane.showMessageDialog(parentJFrame,"Error: Please enter the VALID values");
        }
    }
    private void List() {
        LinkedList<Song> maplist=new LinkedList<>();
        String filename = selectFile();
        if(selList.getSelectedItem()=="songList") {
            this.list = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist  = this.list;
        }

        else if(selList.getSelectedItem()=="songList1") {
            songLinkedList1 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList1;
        }
        else if(selList.getSelectedItem()=="songList2") {
            songLinkedList2 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList2;
        }
         mMap.put(filename,maplist);
        Reset();
        assert maplist != null;
        for (Song sng : mMap.get(filename)) {
            row[0] = maplist.indexOf(sng);
            row[1] = sng.getID();
            row[2] = sng.getTitle();
            row[3] = sng.getArtist();
            row[4] = sng.getDuration();
            model.addRow(row);
        }
    }

    private void Reset() {
        model.setRowCount(0);
    }

    private void Move() throws IOException {
        LinkedList<Song> maplist=new LinkedList<>();
        String filename = selectFile();
        if(selList.getSelectedItem()=="songList") {
            this.list = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist  = this.list;
        }

        else if(selList.getSelectedItem()=="songList1") {
            songLinkedList1 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList1;
        }
        else if(selList.getSelectedItem()=="songList2") {
            songLinkedList2 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList2;
        }

        System.out.println(filename);
        if (!textFieldSource.getText().isEmpty() && !textFieldDestination.getText().isEmpty() && isNumber(textFieldSource.getText())
                && isNumber(textFieldSource.getText()))
        {
            while (true) {

                int moveIndex = Integer.parseInt(textFieldSource.getText());

                int moveLocIndex = Integer.parseInt(textFieldDestination.getText());

                if (moveIndex < maplist.size() && moveIndex >= 0 && moveLocIndex < maplist.size() && moveLocIndex >= 0) {
                    if (moveIndex > moveLocIndex) {
                        maplist.add(moveLocIndex, maplist.get(moveIndex));
                        maplist.remove(moveIndex + 1);
                    } else if (moveIndex < moveLocIndex) {
                        maplist.add(moveLocIndex + 1, maplist.get(moveIndex));
                        maplist.remove(moveIndex);
                    } else  {
                        maplist.add(moveLocIndex, maplist.get(moveIndex));
                        maplist.remove(moveIndex);
                    }

                    FileWriter fl = new FileWriter("Data/" + filename);
                    BufferedWriter br = new BufferedWriter(fl);
                    br.flush();
                    SongLoader.saveCSV(maplist, filename);
                   maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                   mMap.put(filename,maplist);

                    Reset();
                    assert maplist != null;
                    for (Song sng : mMap.get(filename)) {
                        row[0] = maplist.indexOf(sng);
                        row[1] = sng.getID();
                        row[2] = sng.getTitle();
                        row[3] = sng.getArtist();
                        row[4] = sng.getDuration();
                        model.addRow(row);
                    }
                }
                break;
            }
        } else
            JOptionPane.showMessageDialog(parentJFrame, "Please Enter the values(Integer) of Source or destination index.");
    }

    private void Sort() throws IOException {
        String filename = selectFile();
        String fieldname = selectField();
        String ordername = selectOrder();
        LinkedList<Song> maplist=new LinkedList<>();
        if(selList.getSelectedItem()=="songList") {
            list = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist  = this.list;
        }

        else if(selList.getSelectedItem()=="songList1") {
            songLinkedList1 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList1;
        }
        else if(selList.getSelectedItem()=="songList2") {

            songLinkedList2 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList2;
        }

        if (Objects.equals(fieldname, "With ID")) {

            if (Objects.equals(ordername, "Ascending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorId();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                assert maplist != null;
                maplist.sort(comparator);
            } else if (Objects.equals(ordername, "Descending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorDescendingID();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                assert maplist != null;
                maplist.sort(comparator);
            }
        } else if (Objects.equals(fieldname, "With Title")) {

            if (Objects.equals(ordername, "Ascending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorTitle();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                assert maplist != null;
                maplist.sort(comparator);

            } else if (Objects.equals(ordername, "Descending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorDescendingTitle();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                assert maplist != null;
                maplist.sort(comparator);
            }
        } else if (Objects.equals(fieldname, "With Artist")) {
            if (Objects.equals(ordername, "Ascending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorArtist();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                assert maplist != null;
                maplist.sort(comparator);
            } else if (Objects.equals(ordername, "Descending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorDescendingArtist();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                assert maplist != null;
                maplist.sort(comparator);
            }
        } else if (Objects.equals(fieldname, "With Duration")) {

            if (Objects.equals(ordername, "Ascending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorDuration();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                assert maplist != null;
                maplist.sort(comparator);


            } else if (Objects.equals(ordername, "Descending Order")) {
                maplist.clear();
                Comparator<Song> comparator = new Song.ComparatorDescendingDuration();
                maplist = (LinkedList<Song>) SongLoader.loadCSV(filename);
                maplist.sort(comparator);
            }
        }
        FileWriter fl = new FileWriter("Data/" + filename);
        BufferedWriter br = new BufferedWriter(fl);
        br.flush();
        Reset();
        SongLoader.saveCSV(maplist, filename);
        maplist= (LinkedList<Song>) SongLoader.loadCSV(filename);
        mMap.put(filename,maplist);

        for (Song sng : mMap.get(filename)) {
            row[0] = maplist.indexOf(sng);
            row[1] = sng.getID();
            row[2] = sng.getTitle();
            row[3] = sng.getArtist();
            row[4] = sng.getDuration();
            model.addRow(row);
        }
    }
    private void exit()
    {
        int i =JOptionPane.showConfirmDialog (null, "Are you sure to exit?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (i == 0)
            System.exit (0);
    }

    private void Delete() throws IOException {
        LinkedList<Song> maplist=new LinkedList<>();
        String filename = selectFile();
        if(selList.getSelectedItem()=="songList") {
            list = (LinkedList<Song>) SongLoader.loadCSV(filename);
           maplist  = list;
        }
        else if(selList.getSelectedItem()=="songList1") {
            songLinkedList1 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList1;
        }
        else if(selList.getSelectedItem()=="songList2") {
            songLinkedList2 = (LinkedList<Song>) SongLoader.loadCSV(filename);
            maplist = songLinkedList2;
        }
        boolean result = false; // e.g. Song does note exist.

        if (!textFieldID.getText().isEmpty() && isNumber(textFieldID.getText()))
        {
            if (!isSongIdInLinkedList(String.valueOf(textFieldID.getText())))
            {
                JOptionPane.showMessageDialog (parentJFrame,
                        "Error: Please enter the valid Song ID");
            }
            else if(maplist.size() == 0)
            {
                JOptionPane.showMessageDialog (parentJFrame,
                        "Error: Database is empty.");
            }
            else
            {
                for (int s = 0; s < maplist.size(); s++)
                {
                    String currId = String.valueOf(maplist.get (s).getID());

                    if (currId.compareToIgnoreCase(String.valueOf(textFieldID.getText())) == 0)
                    {
                        maplist.remove (s);  // Song deleted.
                        result = true;
                        s = maplist.size();
                        FileWriter fl= new FileWriter("Data/"+filename);
                        BufferedWriter br=new BufferedWriter(fl);
                        br.flush();
                        SongLoader.saveCSV(maplist,filename);
                    }
                }
            }
            if (result)
            {
                mMap.put(filename,maplist);
                Reset();
                for (Song sng : mMap.get(filename)) {
                    row[0] = maplist.indexOf(sng);
                    row[1] = sng.getID();
                    row[2] = sng.getTitle();
                    row[3] = sng.getArtist();
                    row[4] = sng.getDuration();
                    model.addRow(row);
                }
                textFieldID.setText("");
                JOptionPane.showMessageDialog(parentJFrame, "Song Deleted successfully.");
            }
        }
        else
            JOptionPane.showMessageDialog (parentJFrame,
                    "Error: Please enter the  Song ID(Integer):");
    }
    public static void main(String[] args) {
        SongManager Mysong=new SongManager();
        parentJFrame.setTitle("Song Manager");
        parentJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parentJFrame.setSize(900, 900);
        parentJFrame.setLayout(null);
        parentJFrame.setVisible(true);
    }
}







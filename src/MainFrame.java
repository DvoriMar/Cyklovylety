import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MainFrame extends JFrame{

    public static final String oddelovac = ",";


    private JPanel mainPanel;
    private JButton smazbtn;
    private JTextField smaztxtfield;
    private JTextArea textArea1;
    private JMenuBar menuBar = new JMenuBar(); //Hlavní lišta
    private JMenu menu;
    private JMenuItem menuItem;
    private JFileChooser chooser = new JFileChooser(".");
    private File selectedFile;

    private ArrayList<Cyklovylet> seznam = new ArrayList<>();

    private MainFrame(){
        initComponents();
    }

    private void initComponents(){
        setContentPane(mainPanel);
        setJMenuBar(menuBar);

        menu = new JMenu("Soubor");
        menuBar.add(menu);


        menuItem = new JMenuItem("Načti");
        menu.add(menuItem);
        menuItem.addActionListener(e->nacti());

        menuItem = new JMenuItem("Refresh");
        menu.add(menuItem);
        menuItem.addActionListener(e->refresh());

        smazbtn.addActionListener(e->smaz());

    }
    private void nacti(){

        int vysledek = chooser.showOpenDialog(mainPanel);

        if (vysledek == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();

            nactiSoubor();
        }
    }

    private void refresh(){
        textArea1.setText("");

        int cislovani = 1;
        for (Cyklovylet vylet:seznam){

            textArea1.append( cislovani +". "+vylet.getCil() +" "+ vylet.getDatum() +" " +vylet.getDelka() +"\n");
            cislovani++;

        }
    }

    private void smaz(){
        int smazTextField = Integer.parseInt(smaztxtfield.getText())-1;
        seznam.remove(smazTextField);

    }



    private void nactiSoubor(){
        String line;
        String[] polozky;
        int cislovani = 1;

        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(selectedFile)));

            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                polozky = line.split(oddelovac);


                String cil = polozky[0].trim();
                int delka = Integer.parseInt(polozky[1].trim());
                LocalDate datum = LocalDate.parse(polozky[2].trim());

                Cyklovylet vylet = new Cyklovylet(cil, delka, datum);

                seznam.add(vylet);

                textArea1.append(cislovani +". " +cil+" "+delka+" "+datum +"\n");
                cislovani++;
            }
        }


        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    public static void main(String[] args) {
        MainFrame form = new MainFrame();

        form.setVisible(true);
        form.pack();
        form.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //form.
    }

}

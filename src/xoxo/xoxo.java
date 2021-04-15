package xoxo;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class xoxo extends JFrame implements ActionListener
{
    Symbol player = Symbol.O;
    Symbol splayer = Symbol.O;
    enum Symbol {X, O};
    Symbol[][] board = new Symbol[3][3];
    int movecount;
    int gameover = 0;
    Color xcolor = null;
    Color ocolor = null;
    String xname = "X";
    String oname = "O";
    boolean keepset = false;
    JMenuBar mb = new JMenuBar();
    JMenu gmenu = new JMenu("Game");
    JMenuItem newgame = new JMenuItem("New game");
    JMenu randomise = new JMenu("Randomise values");
    JMenuItem rall = new JMenuItem("All");
    JMenuItem rnames = new JMenuItem("Names");
    JMenuItem rcols = new JMenuItem("Colors");
    JMenuItem rstart = new JMenuItem("Starters");
    JCheckBoxMenuItem keep = new JCheckBoxMenuItem("Keep settings");
    JMenuItem exit = new JMenuItem("Exit");
    JButton a1 = new JButton();
    JButton a2 = new JButton();
    JButton a3 = new JButton();
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton c1 = new JButton();
    JButton c2 = new JButton();
    JButton c3 = new JButton();
    JRadioButton xstart = new JRadioButton("X starts");
    JRadioButton ostart = new JRadioButton("O starts");
    ButtonGroup bgroup = new ButtonGroup();
    JTextField p1name = new JTextField("X");
    JTextField p2name = new JTextField("O");
    JLabel p1namelabel = new JLabel("Player X's name:");
    JLabel p2namelabel = new JLabel("Player O's name:");
    JCheckBox customcol = new JCheckBox("Custom colors for players");
    JLabel xcol = new JLabel("Color for X:");
    JLabel ocol = new JLabel("Color for O:");
    String colors[] = {"Default", "FF0000", "00FF00", "0000FF", "FF00FF", "00FFFF", "FFFF00"};
    JComboBox p1col = new JComboBox(colors);
    JComboBox p2col = new JComboBox(colors);
    JLabel announcer = new JLabel("Your move, " +xname+ " (X)!", SwingConstants.CENTER);

    public xoxo()
    {
        JFrame f = new JFrame("3,14škvorky");
        f.setSize(500,425);
        //f.setLayout(new GridLayout(7,5));
        f.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        //f.setResizable(false);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        a1.addActionListener(this);
        a2.addActionListener(this);
        a3.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        c1.addActionListener(this);
        c2.addActionListener(this);
        c3.addActionListener(this);

        f.setJMenuBar(mb);
        mb.add(gmenu);
        gmenu.add(newgame);
        gmenu.add(keep);
        gmenu.add(randomise);
        randomise.add(rall); randomise.add(rnames);
        randomise.add(rcols); randomise.add(rstart);
        gmenu.add(exit);

        f.add(a1);
        a1.setPreferredSize(new Dimension(100, 100));
        f.add(a2);
        a2.setPreferredSize(new Dimension(100, 100));
        f.add(a3);
        a3.setPreferredSize(new Dimension(100, 100));
        f.add(xstart);
        xstart.setSelected(true);
        xstart.setPreferredSize(new Dimension(100, 100));
        f.add(ostart);
        ostart.setPreferredSize(new Dimension(90, 40));
        f.add(b1);
        b1.setPreferredSize(new Dimension(100, 100));
        f.add(b2);
        b2.setPreferredSize(new Dimension(100, 100));
        f.add(b3);
        b3.setPreferredSize(new Dimension(100, 100));
        f.add(Box.createHorizontalStrut(2));
        f.add(p1namelabel);
        f.add(Box.createHorizontalStrut(2));
        f.add(p1name);
        p1name.setPreferredSize(new Dimension(90, 40));
        f.add(c1);
        c1.setPreferredSize(new Dimension(100, 100));
        f.add(c2);
        c2.setPreferredSize(new Dimension(100, 100));
        f.add(c3);
        c3.setPreferredSize(new Dimension(100, 100));
        f.add(Box.createHorizontalStrut(2));
        f.add(p2namelabel);
        f.add(Box.createHorizontalStrut(2));
        f.add(p2name);
        p2name.setPreferredSize(new Dimension(90, 40));
        f.add(announcer);
        announcer.setPreferredSize(new Dimension(500, 50));
        f.add(customcol);
        customcol.setPreferredSize(new Dimension(175, 25));
        f.add(Box.createHorizontalStrut(10));
        f.add(xcol);
        xcol.setPreferredSize(new Dimension(65, 25));
        f.add(p1col);
        p1col.setEnabled(false);
        p1col.setPreferredSize(new Dimension(75, 25));
        f.add(Box.createHorizontalStrut(10));
        f.add(ocol);
        ocol.setPreferredSize(new Dimension(65, 25));
        f.add(p2col);
        p2col.setEnabled(false);
        p2col.setPreferredSize(new Dimension(75, 25));

        xstart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String m = "Do you want to start a new game?";
                String t = "New game";
                int reply = JOptionPane.showConfirmDialog(null, m, t, JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.YES_OPTION) {
                    newgame(Symbol.O);
                }
                else if(splayer == Symbol.X) ostart.setSelected(true);
            }
        });

        ostart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String m = "Do you want to start a new game?";
                String t = "New game";
                int reply = JOptionPane.showConfirmDialog(null, m, t, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    newgame(Symbol.X);
                }
                else if(splayer == Symbol.O) xstart.setSelected(true);
            }
        });

        p1name.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                change();
            }
            public void removeUpdate(DocumentEvent e) {
                change();
            }
            public void insertUpdate(DocumentEvent e) {
                change();
            }
            public void change() {
                String newname = p1name.getText();
                xname = newname;
            }
        });

        p2name.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                change();
            }
            public void removeUpdate(DocumentEvent e) {
                change();
            }
            public void insertUpdate(DocumentEvent e) {
                change();
            }
            public void change() {
                String newname = p2name.getText();
                oname = newname;
            }
        });

        customcol.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(customcol.isSelected()) {
                    p1col.setEnabled(true);
                    p2col.setEnabled(true);
                }
                else {
                    a1.setBackground(null);
                    a2.setBackground(null);
                    a3.setBackground(null);
                    b1.setBackground(null);
                    b2.setBackground(null);
                    b3.setBackground(null);
                    c1.setBackground(null);
                    c2.setBackground(null);
                    c3.setBackground(null);
                    p1col.setEnabled(false);
                    p2col.setEnabled(false);
                    p1col.setSelectedItem("Default");
                    p2col.setSelectedItem("Default");
                }
            }
        });

        p1col.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                switch(p1col.getSelectedIndex())
                {
                    case 0: xcolor = null; break;
                    case 1: xcolor = new Color(255,0,0); break;
                    case 2: xcolor = new Color(0,255,0); break;
                    case 3: xcolor = new Color(0,0,255); break;
                    case 4: xcolor = new Color(255,0,255); break;
                    case 5: xcolor = new Color(0,255,255); break;
                    case 6: xcolor = new Color(255,255,0); break;
                }
                if(board[0][0] == Symbol.X) a1.setBackground(xcolor);
                if(board[1][0] == Symbol.X) a2.setBackground(xcolor);
                if(board[2][0] == Symbol.X) a3.setBackground(xcolor);
                if(board[0][1] == Symbol.X) b1.setBackground(xcolor);
                if(board[1][1] == Symbol.X) b2.setBackground(xcolor);
                if(board[2][1] == Symbol.X) b3.setBackground(xcolor);
                if(board[0][2] == Symbol.X) c1.setBackground(xcolor);
                if(board[1][2] == Symbol.X) c2.setBackground(xcolor);
                if(board[2][2] == Symbol.X) c3.setBackground(xcolor);
            }
        });

        p2col.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                switch(p2col.getSelectedIndex())
                {
                    case 0: ocolor = null; break;
                    case 1: ocolor = new Color(255,0,0); break;
                    case 2: ocolor = new Color(0,255,0); break;
                    case 3: ocolor = new Color(0,0,255); break;
                    case 4: ocolor = new Color(255,0,255); break;
                    case 5: ocolor = new Color(0,255,255); break;
                    case 6: ocolor = new Color(255,255,0); break;
                }
                if(board[0][0] == Symbol.O) a1.setBackground(ocolor);
                if(board[1][0] == Symbol.O) a2.setBackground(ocolor);
                if(board[2][0] == Symbol.O) a3.setBackground(ocolor);
                if(board[0][1] == Symbol.O) b1.setBackground(ocolor);
                if(board[1][1] == Symbol.O) b2.setBackground(ocolor);
                if(board[2][1] == Symbol.O) b3.setBackground(ocolor);
                if(board[0][2] == Symbol.O) c1.setBackground(ocolor);
                if(board[1][2] == Symbol.O) c2.setBackground(ocolor);
                if(board[2][2] == Symbol.O) c3.setBackground(ocolor);
            }
        });

        newgame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(player == Symbol.X) newgame(Symbol.O);
                else newgame(Symbol.X);
            }
        });

        rall.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                fnames();
                fcols();
            }
        });

        rnames.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                fnames();
            }
        });

        rcols.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                fcols();
            }
        });

        rstart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                fstart();
            }
        });

        keep.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(keep.isSelected()) keepset = true;
                else keepset = false;
            }
        });

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        bgroup.add(xstart);
        bgroup.add(ostart);
        f.setVisible(true);
    }
    public void fnames() {
        Random rd1 = new Random();
        String xnewname = "";
        String onewname = "";
        switch(rd1.nextInt(8))
        {
            case 0: xnewname = "1"; break;
            case 1: xnewname = "2"; break;
            case 2: xnewname = "3"; break;
            case 3: xnewname = "4"; break;
            case 4: xnewname = "5"; break;
            case 5: xnewname = "6"; break;
            case 6: xnewname = "7"; break;
            case 7: xnewname = "8"; break;
        }
        while(true) {
            Random rd2 = new Random();
            switch(rd2.nextInt(8))
            {
                case 0: onewname = "1"; break;
                case 1: onewname = "2"; break;
                case 2: onewname = "3"; break;
                case 3: onewname = "4"; break;
                case 4: onewname = "5"; break;
                case 5: onewname = "6"; break;
                case 6: onewname = "7"; break;
                case 7: onewname = "8"; break;
            }
            if(xnewname != onewname) break;
        }
        xname = xnewname;
        p1name.setText(xnewname);
        oname = onewname;
        p2name.setText(onewname);
    }
    public void fcols() {
        Random rd1 = new Random();
        Random rd2 = new Random();
        customcol.setSelected(true);
        p1col.setEnabled(true);
        p2col.setEnabled(true);
        switch(rd1.nextInt(6)) {
            case 0: p1col.setSelectedIndex(1); break;
            case 1: p1col.setSelectedIndex(2); break;
            case 2: p1col.setSelectedIndex(3); break;
            case 3: p1col.setSelectedIndex(4); break;
            case 4: p1col.setSelectedIndex(5); break;
            case 5: p1col.setSelectedIndex(6); break;
        }
        switch(rd2.nextInt(6)) {
            case 0: p2col.setSelectedIndex(1); break;
            case 1: p2col.setSelectedIndex(2); break;
            case 2: p2col.setSelectedIndex(3); break;
            case 3: p2col.setSelectedIndex(4); break;
            case 4: p2col.setSelectedIndex(5); break;
            case 5: p2col.setSelectedIndex(6); break;
        }
    }
    public void fstart() {
        String m = "Do you want to start a new game?";
        String t = "New game";
        int reply = JOptionPane.showConfirmDialog(null, m, t, JOptionPane.YES_NO_OPTION);
        Random rd = new Random();
        Symbol s = Symbol.O;
        switch(rd.nextInt(1))
        {
            case 0: s = Symbol.X; break;
            case 1: s = Symbol.O; break;
        }
        if (reply == JOptionPane.YES_OPTION) {
            newgame(s);
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        int x = 0;
        int y = 0;
        Object source = e.getSource();
        JButton button = (JButton) source;
        if(gameover == 1) {}
        else if(button.getText() == "") {
            if(player == Symbol.X) {
                announcer.setText("Your move, " +xname+ " ("+player.toString()+")!");
                player = Symbol.O;
                button.setBackground(ocolor);
                button.setText("O");
            }
            else {
                announcer.setText("Your move, "+oname+ " ("+player.toString()+")!");
                player = Symbol.X;
                button.setBackground(xcolor);
                button.setText("X");
            }
            if(source == a1) {x=0;y=0;}
            if(source == a2) {x=1;y=0;}
            if(source == a3) {x=2;y=0;}
            if(source == b1) {x=0;y=1;}
            if(source == b2) {x=1;y=1;}
            if(source == b3) {x=2;y=1;}
            if(source == c1) {x=0;y=2;}
            if(source == c2) {x=1;y=2;}
            if(source == c3) {x=2;y=2;}
            makemove(x,y,player);
        }
        else announcer.setText("That field is already taken, "+player.toString()+"!");
    }
    public void makemove(int x, int y, Symbol symbol)
    {
        int col = 0;
        int row = 0;
        int dia = 0;
        int rdia = 0;
        board[x][y] = symbol;
        movecount++;
        for(int i = 0; i < 3; i++) {
            if(board[x][i] == symbol) col++;
            if(board[i][y] == symbol) row++;
            if(board[i][i] == symbol) dia++;
            if(board[i][2-i] == symbol) rdia++;
        }
        if(col == 3 || row == 3 || dia == 3 || rdia == 3) {
            String whowon = "";
            if(symbol == Symbol.X) whowon = xname;
            else whowon = oname;
            announcer.setText(whowon + " ("+symbol.toString()+") won!");
            gameover = 1;
        }
        else if(movecount == 9) {
            announcer.setText("The players have reached a draw!");
            gameover = 1;
        }
    }
    public void newgame(Symbol start)
    {
        a1.setText("");
        a2.setText("");
        a3.setText("");
        b1.setText("");
        b2.setText("");
        b3.setText("");
        c1.setText("");
        c2.setText("");
        c3.setText("");
        if(!keepset) {
            p1col.setSelectedItem("Default");
            p2col.setSelectedItem("Default");
            p1col.setEnabled(false);
            p2col.setEnabled(false);
            xcolor = null;
            ocolor = null;
            xname = "X";
            oname = "O";
            p1name.setText("X");
            p2name.setText("O");
        }
        a1.setBackground(null);
        a2.setBackground(null);
        a3.setBackground(null);
        b1.setBackground(null);
        b2.setBackground(null);
        b3.setBackground(null);
        c1.setBackground(null);
        c2.setBackground(null);
        c3.setBackground(null);
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
        player = start;
        splayer = start;
        if(start == Symbol.O) {
            announcer.setText("Your move, " +xname+ " (X)!");
            xstart.setSelected(true);
        }
        else { announcer.setText("Your move, " +oname+ " (O)!"); ostart.setSelected(true); }
        gameover = 0;
        movecount = 0;
    }

    public static void main(String[] args) {
        xoxo x = new xoxo();
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*; //used for user interface
import javax.swing.text.SimpleAttributeSet; //used for changing fonts
import javax.swing.text.StyleConstants; //text styles
import javax.swing.text.StyledDocument; //text styles
import java.awt.*;
import java.awt.event.ActionListener; //listens for mouse clicks on buttons
import java.awt.event.ActionEvent; //handles button press events

public class gui{
JFrame nframe;
JPanel npanel;

public void guiface(){
    nframe= new JFrame();
    npanel= new JPanel();
    nframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // program to be exited if window is closed
            nframe.setTitle("|Vehicles"); // title of main window
            nframe.setSize(600, 300); // seting the width and height of the main window
            nframe.setLocationRelativeTo(null); // position the nframe in the middle of the screen
            nframe.setLayout(new BorderLayout()); // initiate layout of nframe

    nframe.add(npanel);
    nframe.setVisible(true);
}

}
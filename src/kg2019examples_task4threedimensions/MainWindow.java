package kg2019examples_task4threedimensions;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.MyPolygon;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainWindow extends JFrame {

    private DrawPanel drawPanel;
    private JPanel buttonsPanel;

    private final JButton buttonLoadInputFromFile = new JButton("Load File");

    private JFileChooser fileChooserOpen;

    public MainWindow() throws HeadlessException {
        super("Voxelization");

        panel();
        this.add(drawPanel);
        this.add(buttonsPanel, BorderLayout.NORTH);
    }

    private void panel() {
        drawPanel = new DrawPanel();
        buttonsPanel = new JPanel();

        fileChooserOpen = new JFileChooser();

        fileChooserOpen.setCurrentDirectory(new File("."));

        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);

        buttonLoadInputFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(drawPanel) == JFileChooser.APPROVE_OPTION) {
                    FileManager fileManager = new FileManager();

                    drawPanel.scene.getModelsList().add(new IModel() {
                        @Override
                        public List<PolyLine3D> getLines() {
                            Vector3 p1 = new Vector3(0, 0, 0);
                            return Collections.singletonList(new PolyLine3D(Arrays.asList(p1, p1), false));
                        }

                        @Override
                        public List<MyPolygon> getPolygons() {
                            return fileManager.readFile(new File(fileChooserOpen.getSelectedFile().getPath()));
                        }
                    });

                    drawPanel.repaint();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        buttonLoadInputFromFile.setPreferredSize(new Dimension(200, 25));
        buttonsPanel.add(buttonLoadInputFromFile);

    }
}
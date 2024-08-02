import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class NoAccident extends JPanel {

    // Sample input coordinates for flights
    private List<int[][]> flights;

    public NoAccident() {
        flights = new ArrayList<>();
        flights.add(new int[][]{{1, 1}, {2, 2}, {3, 3}});
        flights.add(new int[][]{{1, 1}, {2, 4}, {3, 2}});
        flights.add(new int[][]{{1, 1}, {4, 2}, {3, 4}});
        adjustPaths();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Define colors for each flight
        Color[] colors = {Color.ORANGE, Color.BLUE, Color.RED};

        int height = getHeight(); // Get the height of the panel

        for (int i = 0; i < flights.size(); i++) {
            g2d.setColor(colors[i]);
            int[] prev = null;
            for (int[] coord : flights.get(i)) {
                int x = coord[0] * 100;
                int y = height - coord[1] * 100; // Flip the y-coordinate
                if (prev != null) {
                    g2d.drawLine(prev[0], prev[1], x, y);
                }
                prev = new int[]{x, y};
                g2d.fillOval(x - 5, y - 5, 10, 10);
            }
        }
    }

    private void adjustPaths() {
        // Define the adjusted paths based on the reverse order of colors
        flights.set(0, new int[][]{{1, 1}, {2, 2}, {3, 3}});
        flights.set(1, new int[][]{{1, 1}, {2, 4}, {4, 3}, {3, 2}});
        flights.set(2, new int[][]{{1, 1}, {4, 2}, {5, 4}, {3, 4}});
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Flight Paths");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new NoAccident());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NoAccident::createAndShowGUI);
    }
}

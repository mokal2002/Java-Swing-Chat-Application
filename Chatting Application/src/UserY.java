import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserY implements ActionListener {
    JTextField text;
    JPanel body;
    public static Box vertical = Box.createVerticalBox();
    public static JFrame f = new JFrame();
    public static DataOutputStream dout;
    UserY() {
        f.setLayout(null);

        // Create a custom JPanel Header with gradient background
        JPanel p1 = new GradientPanel();
        p1.setBounds(0, 0, 300, 45);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/left-arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 13, 20, 20);
        p1.add(back);

        ImageIcon pr1 = new ImageIcon(ClassLoader.getSystemResource("icons/profilem.png"));
        Image p2 = pr1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon p3 = new ImageIcon(p2);
        JLabel profile = new JLabel(p3);
        profile.setBounds(35, 8, 30, 30);
        p1.add(profile);

        ImageIcon m1 = new ImageIcon(ClassLoader.getSystemResource("icons/menu.png"));
        Image m2 = m1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon m3 = new ImageIcon(m2);
        JLabel menu = new JLabel(m3);
        menu.setBounds(250, 8, 30, 30);
        p1.add(menu);

        JLabel name = new JLabel("Jhon");
        name.setBounds(75,14,100,12);
        name.setForeground(Color.DARK_GRAY);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status = new JLabel("Active now");
        status.setBounds(75,25,100,12);
        status.setForeground(Color.DARK_GRAY);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        p1.add(status);
//        end header

        body = new JPanel();
        body.setBounds(0,50,300,400);
        f.add(body);



        text = new JTextField();
        text.setBounds(0,450,230,50);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        text.setBorder(BorderFactory.createLineBorder(new Color(128, 0, 128), 1, true));
        f.add(text);

        JButton button = new JButton("Send");
        button.setBounds(230,450,70,50);
        button.setBackground(new Color(128, 0, 128));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(this);
        f.add(button);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });



        f.setTitle("Chat Application");
        f.getContentPane().setBackground(Color.WHITE);
        f.setUndecorated(true);
        f.setSize(300, 500);
        f.setLocation(50, 50);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application exits when the window is closed
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        try {


            String out = text.getText();
            JPanel p2 = formatLabel(out);

            body.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(5));

            body.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);
            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception ex){
            System.out.println("Error" + ex);
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding around the panel

        // Use JTextArea for message to support text wrapping
        JTextArea output = new JTextArea(out);
        output.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        output.setBackground(new Color(128, 0, 128));
        output.setForeground(Color.WHITE);
        output.setWrapStyleWord(true);
        output.setLineWrap(true);
        output.setEditable(false);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(5,5,5,20));
        output.setMaximumSize(new Dimension(250, Short.MAX_VALUE)); // Limit width for wrapping
        output.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panel.add(output);

        // Add timestamp
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        time.setFont(new Font("SAN_SERIF", Font.PLAIN, 10));
        time.setForeground(Color.GRAY);
        panel.add(time);

        return panel;
    }


    // Custom JPanel with gradient background
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Define the gradient paint from pink to purple
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(176, 6, 212), // Light Pink
                    getWidth(), getHeight(), new Color(223, 6, 147) // Purple
            );

            // Apply the gradient paint
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }


    public static void main(String[] args) {
        new UserY();

        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true){
                Socket s =  skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }

        }catch (Exception e){
            System.out.println("Error" + e);
        }
    }
}

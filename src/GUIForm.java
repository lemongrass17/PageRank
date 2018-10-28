import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIForm extends JFrame{
    private JTextField textField1;
    private JButton OKButton;
    private JTextArea textArea1;
    private JPanel mainPanel;
    private RefsParser rp = new RefsParser();
    private Font font1 = new Font("SansSerif", Font.BOLD, 16);

    public GUIForm(){
        add(mainPanel);
        setTitle("PageRank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        textArea1.setFont(font1);
        textField1.setFont(font1);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while(rp.getMainPage("http://" + textField1.getText().replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","")) == null){
                }
                String link = textField1.getText();
                String dom = rp.getMainPage("http://" + textField1.getText().replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)",""));
                rp.setDomain(dom);
                rp.parse(link);
                rp.getSC().rankOfPages(100);
                String res = rp.getSC().toStr();
                textArea1.setText(res);
            }
        });
    }
}

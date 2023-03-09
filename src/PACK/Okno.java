package PACK;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;

public class Okno extends JFrame
{
    Timer updateTimer;
    int DELAY = 100;
    JLabel displayClock = new JLabel();
    JLabel DateTime = new JLabel();
    JLabel battery = new JLabel();
    JProgressBar battery_bar = new JProgressBar();
    JButton addd = new JButton("ADD");
    JButton sub = new JButton("SUB");
    JButton mov = new JButton("MOV");
    JLabel que = new JLabel("Kolejka rozkazów");
    JLabel interruptions = new JLabel("Przerwania");
    JLabel story = new JLabel("Historia rozkazów");
    JButton exec = new JButton("Wykonaj");
    JButton exit = new JButton("WYJDŹ");
    JButton que_add = new JButton("Kolejka");
    String[] order = {"","Odczyt godziny", "Odczyt daty", "Odczyt stanu baterii", "Odtworzenie dźwięku","Ilość procesorów i rdzeni"};
    JComboBox kombo = new JComboBox(order);
    public int index = 0;
    String cores = "Szybkość podstawowa: 2,90 GHz\n" +
            "Rdzenie: 6\n" +
            "Procesory Logiczne: 6\n" +
            "Pamięć podręczna poziomu 1: 384 KB\n" +
            "Pamięć podręczna poziomu 2: 1,5 MB\n" +
            "Pamięć podręczna poziomu 3: 12,0 MB";
    JTextField tf = new JTextField();
    JButton enter = new JButton("Wpisanie");
    JLabel ah_register = new JLabel("00000000");
    JCheckBox ah_button = new JCheckBox("AH");
    JLabel al_register = new JLabel("00000000");
    JCheckBox al_button = new JCheckBox("AL");
    JLabel ax_register = new JLabel("AX: "+ah_register.getText()+" "+al_register.getText()+" AX="+"0");
    JLabel bh_register = new JLabel("00000000");
    JCheckBox bh_button = new JCheckBox("BH");
    JLabel bl_register = new JLabel("00000000");
    JCheckBox bl_button = new JCheckBox("BL");
    JLabel bx_register = new JLabel("BX: "+bh_register.getText()+" "+bl_register.getText()+" BX="+"0");
    JLabel ch_register = new JLabel("00000000");
    JCheckBox ch_button = new JCheckBox("CH");
    JLabel cl_register = new JLabel("00000000");
    JCheckBox cl_button = new JCheckBox("CL");
    JLabel cx_register = new JLabel("CX: "+ch_register.getText()+" "+cl_register.getText()+" CX="+"0");
    JLabel dh_register = new JLabel("00000000");
    JCheckBox dh_button = new JCheckBox("DH");
    JLabel dl_register = new JLabel("00000000");
    JCheckBox dl_button = new JCheckBox("DL");
    JLabel dx_register = new JLabel("DX: "+dh_register.getText()+" "+dl_register.getText()+" DX="+"0");
    JLabel result_label = new JLabel("");
    public int operator = 0;
    JTextField commands = new JTextField();
    JButton ax_click = new JButton("AX");
    JButton ah_click = new JButton("AH");
    JButton al_click = new JButton("AL");
    JButton bx_click = new JButton("BX");
    JButton bh_click = new JButton("BH");
    JButton bl_click = new JButton("BL");
    JButton cx_click = new JButton("CX");
    JButton ch_click = new JButton("CH");
    JButton cl_click = new JButton("CL");
    JButton dx_click = new JButton("DX");
    JButton dh_click = new JButton("DH");
    JButton dl_click = new JButton("DL");
    public int first_number=0;
    public int second_number=0;
    JPanel story_panel = new JPanel();
    JPanel que_panel = new JPanel();
    JButton from_que = new JButton("Z kolejki");
    public void curDateTime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        DateTime.setText(dtf.format(now));
    }
    Okno()
    {
        ArrayList<Integer> que_results_list = new ArrayList<Integer>();
        curDateTime();
        updateTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date currentTime = new Date();
                String formatTimeStr = "HH:mm:ss";
                DateFormat formatTime = new SimpleDateFormat(formatTimeStr);
                String formattedTimeStr = formatTime.format(currentTime);
                displayClock.setText(formattedTimeStr);
            }
        });
        updateTimer.start();

        displayClock.setBounds(170,0,200,50);
        displayClock.setFont(new Font(null,Font.BOLD,20));
        displayClock.setForeground(Color.BLACK);
        displayClock.setVisible(false);

        DateTime.setBounds(170,0,200,50);
        DateTime.setFont(new Font(null,Font.BOLD,20));
        DateTime.setForeground(Color.BLACK);
        DateTime.setVisible(false);

        Random rand = new Random();
        int stan = rand.nextInt(1,100);
        battery_bar.setValue(stan);
        battery_bar.setForeground(Color.GREEN);
        battery_bar.setBounds(300,500,150,20);
        battery_bar.setVisible(false);

        battery.setBounds(300,460,150,50);
        battery.setText("Stan baterii = "+stan+" [%]");
        battery.setFont(new Font(null,Font.BOLD,15));
        battery.setVisible(false);

        addd.setBounds(100,380,75,35);
        addd.setFocusable(false);
        addd.setFont(new Font(null,Font.BOLD,14));
        addd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operator=1;
                commands.setText(commands.getText()+"ADD");
            }
        });

        sub.setBounds(200,380,75,35);
        sub.setFocusable(false);
        sub.setFont(new Font(null,Font.BOLD,14));
        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operator=2;
                commands.setText(commands.getText()+"SUB");
            }
        });

        List story_list = new List();
        story_panel.setBounds(20,50,150,250);
        story_panel.add(story_list);

        mov.setBounds(300,380,75,35);
        mov.setFocusable(false);
        mov.setFont(new Font(null,Font.BOLD,14));
        mov.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wynik=0;
                if(operator==1)
                {
                    wynik=first_number+second_number;
                    result_label.setText(Integer.toBinaryString(wynik));
                }
                if(operator==2)
                {
                    wynik = first_number-second_number;
                    result_label.setText(Integer.toBinaryString(wynik));
                }
                result_label.setVisible(true);
                String text;
                text = commands.getText();
                story_list.add(text);
                operator=0;
                first_number=0;
                second_number=0;
                wynik=0;
                commands.setText("");
                file_save(text);
            }
        });

        result_label.setBounds(540,480,70,70);
        result_label.setForeground(Color.RED);
        result_label.setFont(new Font(null,Font.BOLD,17));
        result_label.setVisible(false);

        que.setBounds(600,10,140,30);
        que.setFont(new Font(null,Font.BOLD,14));

        story.setBounds(20,10,140,30);
        story.setFont(new Font(null,Font.BOLD,14));

        interruptions.setBounds(350,20,100,40);
        interruptions.setFont(new Font(null,Font.BOLD,14));

        kombo.setBounds(300,80,200,20);
        kombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(kombo.getSelectedItem().equals("Odczyt godziny")) index=1;
                if(kombo.getSelectedItem().equals("Odczyt daty")) index=2;
                if(kombo.getSelectedItem().equals("Odczyt stanu baterii")) index=3;
                if(kombo.getSelectedItem().equals("Odtworzenie dźwięku")) index=4;
                if(kombo.getSelectedItem().equals("Ilość procesorów i rdzeni")) index=5;
            }
        });

        exec.setBounds(350,120,90,30);
        exec.setFocusable(false);
        exec.setFont(new Font(null,Font.BOLD,12));
        exec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(index)
                {
                    case 1: displayClock.setVisible(true); DateTime.setVisible(false);
                        break;
                    case 2: displayClock.setVisible(false); DateTime.setVisible(true);
                        break;
                    case 3: battery_bar.setVisible(true); battery.setVisible(true);
                        break;
                    case 4: audio_sound();
                        break;
                    case 5: UIManager.put("OptionPane.messageFont", new Font("System", Font.BOLD, 17));
                        JOptionPane.showMessageDialog(null,cores,"Pomoc",JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            }
        });

        exit.setBounds(650,500,100,40);
        exit.setFocusable(false);
        exit.setFont(new Font(null,Font.BOLD,14));
        exit.setBackground(Color.pink);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });


        ax_register.setBounds(180,250,210,20);
        ax_register.setFont(new Font(null,Font.BOLD,13));

        bx_register.setBounds(180,280,210,20);
        bx_register.setFont(new Font(null,Font.BOLD,13));

        cx_register.setBounds(180,310,210,20);
        cx_register.setFont(new Font(null,Font.BOLD,13));

        dx_register.setBounds(180,340,210,20);
        dx_register.setFont(new Font(null,Font.BOLD,13));

        ah_button.setBounds(440,220,60,30);
        ah_button.setFocusable(false);

        al_button.setBounds(500,220,60,30);
        al_button.setFocusable(false);

        bh_button.setBounds(440,260,60,30);
        bh_button.setFocusable(false);

        bl_button.setBounds(500,260,60,30);
        bl_button.setFocusable(false);

        ch_button.setBounds(440,300,60,30);
        ch_button.setFocusable(false);

        cl_button.setBounds(500,300,60,30);
        cl_button.setFocusable(false);

        dh_button.setBounds(440,340,60,30);
        dh_button.setFocusable(false);

        dl_button.setBounds(500,340,60,30);
        dl_button.setFocusable(false);

        tf.setBounds(240,170,150,30);
        tf.setFont(new Font(null,Font.BOLD,15));

        enter.setBounds(410,170,90,30);
        enter.setFont(new Font(null,Font.BOLD,12));
        enter.setFocusable(false);
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = tf.getText();
                int dec = Integer.parseInt(number);
                String result= "00000000";
                int i=result.length()-1;
                while(dec!=0) {
                    char a[] = result.toCharArray();
                    a[i--] = String.valueOf(dec % 2).charAt(0);
                    result = new String(a);
                    dec = dec / 2;
                }
                int decimal_number=0;
                int first_register=0;
                int second_register=0;
                if(al_button.isSelected())
                {
                    al_register.setText(result);
                    first_register=Integer.parseInt(ah_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(al_register.getText(),2);
                    decimal_number=first_register+second_register;
                    ax_register.setText("AX: "+ah_register.getText()+" "+al_register.getText()+" AX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
                if(ah_button.isSelected())
                {
                    ah_register.setText(result);
                    first_register=Integer.parseInt(ah_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(al_register.getText(),2);
                    decimal_number=first_register+second_register;
                    ax_register.setText("AX: "+ah_register.getText()+" "+al_register.getText()+" AX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
                if(bl_button.isSelected())
                {
                    bl_register.setText(result);
                    first_register=Integer.parseInt(bh_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(bl_register.getText(),2);
                    decimal_number=first_register+second_register;
                    bx_register.setText("BX: "+bh_register.getText()+" "+bl_register.getText()+" BX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
                if(bh_button.isSelected())
                {
                    bh_register.setText(result);
                    first_register=Integer.parseInt(bh_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(bl_register.getText(),2);
                    decimal_number=first_register+second_register;
                    bx_register.setText("BX: "+bh_register.getText()+" "+bl_register.getText()+" BX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
                if(cl_button.isSelected())
                {
                    cl_register.setText(result);
                    first_register=Integer.parseInt(ch_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(cl_register.getText(),2);
                    decimal_number=first_register+second_register;
                    cx_register.setText("CX: "+ch_register.getText()+" "+cl_register.getText()+" CX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
                if(ch_button.isSelected())
                {
                    ch_register.setText(result);
                    first_register=Integer.parseInt(ch_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(cl_register.getText(),2);
                    decimal_number=first_register+second_register;
                    cx_register.setText("CX: "+ch_register.getText()+" "+cl_register.getText()+" CX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
                if(dl_button.isSelected())
                {
                    dl_register.setText(result);
                    first_register=Integer.parseInt(dh_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(dl_register.getText(),2);
                    decimal_number=first_register+second_register;
                    dx_register.setText("DX: "+dh_register.getText()+" "+dl_register.getText()+" DX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
                if(dh_button.isSelected())
                {
                    dh_register.setText(result);
                    first_register=Integer.parseInt(dh_register.getText(),2);
                    first_register=first_register<<8;
                    second_register=Integer.parseInt(dl_register.getText(),2);
                    decimal_number=first_register+second_register;
                    dx_register.setText("DX: "+dh_register.getText()+" "+dl_register.getText()+" DX="+decimal_number);
                    decimal_number=0;
                    first_register=0;
                    second_register=0;
                }
            }
        });

        ax_click.setBounds(50,420,50,20);
        ax_click.setFocusable(false);
        ax_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" AX ");
                if(operator==0) first_number=Integer.parseInt(ah_register.getText()+al_register.getText(),2);
                else second_number=Integer.parseInt(ah_register.getText()+al_register.getText(),2);
            }
        });

        ah_click.setBounds(110,420,50,20);
        ah_click.setFocusable(false);
        ah_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" AH ");
                if(operator==0) first_number=Integer.parseInt(ah_register.getText(),2);
                else second_number=Integer.parseInt(ah_register.getText(),2);
            }
        });

        al_click.setBounds(170,420,50,20);
        al_click.setFocusable(false);
        al_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" AL ");
                if(operator==0) first_number=Integer.parseInt(al_register.getText(),2);
                else second_number=Integer.parseInt(al_register.getText(),2);
            }
        });

        bx_click.setBounds(50,450,50,20);
        bx_click.setFocusable(false);
        bx_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" BX ");
                if(operator==0) first_number=Integer.parseInt(bh_register.getText()+bl_register.getText(),2);
                else second_number=Integer.parseInt(bh_register.getText()+bl_register.getText(),2);
            }
        });

        bh_click.setBounds(110,450,50,20);
        bh_click.setFocusable(false);
        bh_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" BH ");
                if(operator==0) first_number=Integer.parseInt(bh_register.getText(),2);
                else second_number=Integer.parseInt(bh_register.getText(),2);
            }
        });

        bl_click.setBounds(170,450,50,20);
        bl_click.setFocusable(false);
        bl_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" BL ");
                if(operator==0) first_number=Integer.parseInt(bl_register.getText(),2);
                else second_number=Integer.parseInt(bl_register.getText(),2);
            }
        });

        cx_click.setBounds(50,480,50,20);
        cx_click.setFocusable(false);
        cx_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" CX ");
                if(operator==0) first_number=Integer.parseInt(ch_register.getText()+cl_register.getText(),2);
                else second_number=Integer.parseInt(ch_register.getText()+cl_register.getText(),2);
            }
        });

        ch_click.setBounds(110,480,50,20);
        ch_click.setFocusable(false);
        ch_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" CH ");
                if(operator==0) first_number=Integer.parseInt(ch_register.getText(),2);
                else second_number=Integer.parseInt(ch_register.getText(),2);
            }
        });

        cl_click.setBounds(170,480,50,20);
        cl_click.setFocusable(false);
        cl_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" CL ");
                if(operator==0) first_number=Integer.parseInt(cl_register.getText(),2);
                else second_number=Integer.parseInt(cl_register.getText(),2);
            }
        });

        dx_click.setBounds(50,510,50,20);
        dx_click.setFocusable(false);
        dx_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" DX ");
                if(operator==0) first_number=Integer.parseInt(dh_register.getText()+dl_register.getText(),2);
                else second_number=Integer.parseInt(dh_register.getText()+dl_register.getText(),2);
            }
        });

        dh_click.setBounds(110,510,50,20);
        dh_click.setFocusable(false);
        dh_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" DH ");
                if(operator==0) first_number=Integer.parseInt(dh_register.getText(),2);
                else second_number=Integer.parseInt(dh_register.getText(),2);
            }
        });

        dl_click.setBounds(170,510,50,20);
        dl_click.setFocusable(false);
        dl_click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.setText(commands.getText()+" DL ");
                if(operator==0) first_number=Integer.parseInt(dl_register.getText(),2);
                else second_number=Integer.parseInt(dl_register.getText(),2);
            }
        });

        commands.setBounds(300,420,150,30);
        commands.setFont(new Font(null,Font.BOLD,15));

        List que_list = new List();

        que_panel.setBounds(600,50,150,250);
        que_panel.add(que_list);

        que_add.setBounds(400,380,95,35);
        que_add.setFont(new Font(null,Font.BOLD,14));
        que_add.setFocusable(false);
        que_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wynik=0;
                if(operator==1)
                {
                    wynik=first_number+second_number;
                    result_label.setText("Q");
                }
                if(operator==2)
                {
                    wynik = first_number-second_number;
                    result_label.setText("Q");
                }
                result_label.setVisible(true);
                String text;
                text = commands.getText();
                que_list.add(text);
                operator=0;
                first_number=0;
                second_number=0;
                que_results_list.add(wynik);
                wynik=0;
                commands.setText("");
            }
        });

        from_que.setBounds(640,370,100,40);
        from_que.setFocusable(false);
        from_que.setFont(new Font(null,Font.BOLD,14));
        from_que.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wynik=0;
                wynik=que_results_list.get(que_results_list.size()-1);
                result_label.setText(Integer.toBinaryString(wynik));
                result_label.setVisible(true);
                String text;
                text = que_list.getItem(que_results_list.size()-1);
                story_list.add(text);
                que_list.remove(que_results_list.size()-1);
                que_results_list.remove(que_results_list.size()-1);
                file_save(text);
            }
        });

        setTitle("Symulator mikroprocesora");
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(800,600);
        setBackground(Color.cyan);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        add(addd);
        add(sub);
        add(mov);
        add(story);
        add(interruptions);
        add(que);
        add(kombo);
        add(tf);
        add(exec);
        add(exit);
        add(que_add);
        add(ax_register);
        add(ah_register);
        add(al_register);
        add(bx_register);
        add(bh_register);
        add(bl_register);
        add(cx_register);
        add(ch_register);
        add(cl_register);
        add(dx_register);
        add(dh_register);
        add(dl_register);
        add(enter);
        add(displayClock);
        add(DateTime);
        add(battery);
        add(battery_bar);
        add(ah_button);
        add(al_button);
        add(bh_button);
        add(bl_button);
        add(ch_button);
        add(cl_button);
        add(dh_button);
        add(dl_button);
        add(result_label);
        add(commands);
        add(ax_click);
        add(ah_click);
        add(al_click);
        add(bx_click);
        add(bh_click);
        add(bl_click);
        add(cx_click);
        add(ch_click);
        add(cl_click);
        add(dx_click);
        add(dh_click);
        add(dl_click);
        add(story_panel);
        add(que_panel);
        add(from_que);
    }
    public void audio_sound()
    {
        try {
            File click = new File("src\\res\\cpu_audio.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(click));
            c.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void file_save(String order)
    {
        File f = new File("rozkazy.txt");
        if(!f.exists())
        {
            try
            {
                f.createNewFile();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        if(f.canWrite())
        {
            try
            {
                FileWriter fw = new FileWriter(f,true);
                Formatter fm = new Formatter(fw);
                String txt;
                txt=order;
                fm.format("%s \r\n", txt);
                fm.close();
                fw.close();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args)
    {
        Okno okno = new Okno();
    }
}

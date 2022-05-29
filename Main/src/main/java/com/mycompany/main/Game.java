/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

/**
 *
 * @author USER
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;


public class Game extends JFrame {
    private ArrayList<String> array = new ArrayList<>();
    ArrayList<JLabel> listLabel = new ArrayList<>();
    JButton reset;
    JPanel panelButton;


    public Game(){
        setTitle("Пятнашки");
        init();
        setBounds(500,150,600,600);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void init() {
        panelButton = new JPanel(new GridLayout(4,4));
        arrayFilling();
        button();
        reset = new JButton("Сброс");
        add(reset,BorderLayout.SOUTH);
        reset.setFont(reset.getFont().deriveFont(24.f));
        reset.addActionListener(e -> resetButton());



    }
    //Метод заполняющий массив и перемешивает
    private void arrayFilling() {
        for (int i =0; i<=15; i++){
            if (i == 0){
                continue;
            }
            array.add(String.valueOf(i));
        }
        Collections.shuffle(array);
        array.add("");
    }


    // Создадие кнопок
    private void button() {
        for (String butt: array){
            if (butt.equals("0")) continue;
            panelButton.add(createLabel(butt));

        }
        add(panelButton,BorderLayout.CENTER);

    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        listLabel.add(label);
        label.setFont(label.getFont().deriveFont(24.f));

        label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Game.this.mousePressed(label);
            }

        } );
        return label;
    }

    private void mousePressed(JLabel label) {

        int start = 0;
        for (int i =0; i < listLabel.size(); i++){
            if (array.get(i) == label.getText()){
                start = i;
            }
        }
        for (int i=start; i < listLabel.size();i++ ) {
            if (array.get(i).equals("")) break;
            if (i == 0) {
                if (array.get(i+1).equals("") || array.get(i+4).equals("")) {
                    pressed(label.getText());
                    label.setText("");
                    break;
                }
                break;
            }
            if (i == 1 || i == 2 || i == 3) {
                if (array.get(i-1).equals("") || array.get(i+1).equals("") || array.get(i+4).equals("")) {
                    pressed(label.getText());
                    label.setText("");
                    break;
                }
                break;
            }
            if (i >= 4 && i <= 11) {
                if (array.get(i-1).equals("") || array.get(i-4).equals("") || array.get(i+1).equals("") || array.get(i+4).equals("")) {
                    pressed(label.getText());
                    label.setText("");
                    break;
                }
                break;
            }
            if (i == 12 || i == 13 || i == 14) {
                if (array.get(i-1).equals("") || array.get(i+1).equals("") || array.get(i-4).equals("")) {
                    pressed(label.getText());
                    label.setText("");
                    break;
                }
                break;
            }
            if (i == 15) {
                if (array.get(i-1).equals("") || array.get(i-4).equals("")) {
                    pressed(label.getText());
                    label.setText("");
                    break;
                }
                break;
            }
        }
    }

    private void pressed(String digit) {
        ArrayList<String> arrayNew = new ArrayList<>();

        for (int i = 0; i <array.size(); i++) {
            if (array.get(i).equals("")) {
                arrayNew.add(digit);
                continue;
            }

            if (array.get(i).equals(digit)) {
                arrayNew.add("");
                continue;
            }
            arrayNew.add(array.get(i));
        }
        array = arrayNew;

        int i = 0;
        for (JLabel lbl: listLabel) {
            lbl.setText(array.get(i++));
        }
        checkWin();
    }

    private void checkWin() {
        int count = 0;
        for (int i = 1; i < array.size();i++ ) {
            if (array.get(i-1).equals("")) continue;

            if (i == 14 || i == 15 ) {
                count++;
                continue;
            }
            if (i == 15 || i == 14) {
                count++;
                continue;
            }
            if (Integer.parseInt(array.get(i-1)) == i )
                count++;
        }
        // если победа, то выводим результат
        if (count == array.size()-1) {
            JOptionPane.showMessageDialog(null,"Вы победили!");
            System.exit(0);
        }
    }


    //Метод сбрасывает расположение костяшек
    private void resetButton() {
        if (JOptionPane.showConfirmDialog(null,"Вы уверены?","Сброс расположения",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ==JOptionPane.YES_OPTION){
            //Перемешиваем
            Collections.shuffle(array);

            // Переносим пустоту в конец
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).equals("")) {
                    array.set(i, array.get(array.size() - 1));
                    array.set(array.size() - 1, "");
                }
            }
            // Заполнение полей
            int i = 0;
            for (JLabel lbl : listLabel) {
                lbl.setText(array.get(i));
                i++;
            }
        }
    }

}

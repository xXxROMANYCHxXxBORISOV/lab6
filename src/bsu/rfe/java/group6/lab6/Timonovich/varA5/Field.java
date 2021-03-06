package bsu.rfe.java.group6.lab6.Timonovich.varA5;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Field extends JPanel {

    // Флаг приостановленности движения
    private boolean paused;
    private boolean paused1;
    private boolean resumeLol;



    // Динамический список скачущих мячей
    private ArrayList<BouncingBalls> balls = new ArrayList<BouncingBalls>(10);




    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
    // При создании его экземпляра используется анонимный класс,
    // реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            // Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });


    // Конструктор класса
    public Field() {
        // Установить цвет заднего фона
        setBackground(Color.WHITE);
        // Запустить таймер
        repaintTimer.start();
    }



    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
        // Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        // Последовательно запросить прорисовку от всех мячей из списка
        for (BouncingBalls ball : balls) {
            ball.paint(canvas);
        }

    }

    // Метод добавления нового мяча в список
    public void addBall() {
        //Заключается в добавлении в список нового экземпляра BouncingBall
        // Всю инициализацию положения, скорости, размера, цвета
        // BouncingBall выполняет сам в конструкторе
        balls.add(new BouncingBalls(this));

    }

    // Метод добавления нового кирпичика в массив




    public void pause() {
        // Включить режим паузы

        paused = true;

    }

    public  void pause1() {
        // Включить режим паузы
        paused1 = true;
        paused = true;
        resumeLol = false;

    }

    public synchronized void resumeLol() {
        // Включить режим паузы
        paused = false;
        resumeLol = true;
        notifyAll();
    }


    // Метод синхронизированный, т.е. только один поток может
    // одновременно быть внутри
    public synchronized void resume() {
        // Выключить режим паузы
        paused = false;
        paused1 = false;

        // Будим все ожидающие продолжения потоки
        notifyAll();
    }


    // Синхронизированный метод проверки, может ли мяч двигаться
    // (не включен ли режим паузы?)
    public synchronized void canMove(BouncingBalls ball) throws
            InterruptedException {

        if (paused) {
            if ( ball.gettime() > 8)
                wait();
            // Если режим паузы включен, то поток, зашедший
            // внутрь данного метода, засыпает
            //wait();
        }
        if(paused1)
            if(resumeLol)
            {
                if (ball.gettime() < 8)
                    wait();
            }
            else
                wait();


    }


}
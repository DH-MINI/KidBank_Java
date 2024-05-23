package com.group52.bank.GUI;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * This class represents a date chooser component in the banking application.
 */
public class DateChooser extends JPanel {
    private Date initDate;
    private static Calendar now = Calendar.getInstance();
    private static Calendar select;
    private JPanel monthPanel;
    private JP1 jp1;
    private JP2 jp2;
    private JP3 jp3;
    private JP4 jp4;
    private Font font = new Font("宋体", Font.PLAIN, 12);
    private JLabel showDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private boolean isShow = false;
    private Popup pop;
    /**
     * Constructs a new DateChooser with the current date.
     */
    public DateChooser() {
        this(new Date());
    }
    /**
     * Constructs a new DateChooser with the given date.
     *
     * @param date the initial date
     */
    public DateChooser(Date date) {
        initDate = date;
        select = Calendar.getInstance();
        select.setTime(initDate);
        initPanel();
        initLabel();
    }
    /**
     * Returns the selected date.
     *
     * @return the selected date
     */
    public Date getDate() {
        return select.getTime();
    }

    private void initPanel() {
        monthPanel = new JPanel(new BorderLayout());
        monthPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        JPanel up = new JPanel(new BorderLayout());
        up.add(jp1 = new JP1(), BorderLayout.NORTH);
        up.add(jp2 = new JP2(), BorderLayout.CENTER);
        monthPanel.add(jp3 = new JP3(), BorderLayout.CENTER);
        monthPanel.add(up, BorderLayout.NORTH);
        monthPanel.add(jp4 = new JP4(), BorderLayout.SOUTH);
        this.addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent event) {}

            public void ancestorRemoved(AncestorEvent event) {}

            public void ancestorMoved(AncestorEvent event) {
                hidePanel();
            }
        });
    }

    private void initLabel() {
        showDate = new JLabel(sdf.format(initDate));
        showDate.setRequestFocusEnabled(true);
        showDate.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                showDate.requestFocusInWindow();
            }
        });

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(showDate, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(150, 30));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        showDate.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                if (showDate.isEnabled()) {
                    showDate.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    showDate.setForeground(Color.RED);
                }
            }

            public void mouseExited(MouseEvent me) {
                if (showDate.isEnabled()) {
                    showDate.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    showDate.setForeground(Color.BLACK);
                }
            }

            public void mousePressed(MouseEvent me) {
                if (showDate.isEnabled()) {
                    showDate.setForeground(Color.CYAN);
                    if (isShow) {
                        hidePanel();
                    } else {
                        showPanel(showDate);
                    }
                }
            }

            public void mouseReleased(MouseEvent me) {
                if (showDate.isEnabled()) {
                    showDate.setForeground(Color.BLACK);
                }
            }
        });
        showDate.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                hidePanel();
            }

            public void focusGained(FocusEvent e) {}
        });
    }

    private void refresh() {
        jp1.updateDate();
        jp3.updateDate();
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void commit() {
        showDate.setText(sdf.format(select.getTime()));
        hidePanel();
    }

    private void hidePanel() {
        if (pop != null) {
            isShow = false;
            pop.hide();
            pop = null;
        }
    }

    private void showPanel(Component owner) {
        if (pop != null) {
            pop.hide();
        }
        Point show = new Point(0, showDate.getHeight());
        SwingUtilities.convertPointToScreen(show, showDate);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = show.x;
        int y = show.y;
        if (x < 0) {
            x = 0;
        }
        if (x > size.width - 295) {
            x = size.width - 295;
        }
        if (y < size.height - 170) {
        } else {
            y -= 188;
        }
        pop = PopupFactory.getSharedInstance().getPopup(owner, monthPanel, x, y);
        pop.show();
        isShow = true;
    }

    private class JP1 extends JPanel {
        JLabel left, right, center;

        public JP1() {
            super(new BorderLayout());
            this.setBackground(new Color(160, 185, 215));
            initJP1();
        }

        private void initJP1() {
            left = new JLabel(" << ", JLabel.CENTER);
            left.setToolTipText("上一月");
            right = new JLabel(" >> ", JLabel.CENTER);
            right.setToolTipText("下一月");
            left.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            right.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            center = new JLabel("", JLabel.CENTER);
            updateDate();
            this.add(left, BorderLayout.WEST);
            this.add(center, BorderLayout.CENTER);
            this.add(right, BorderLayout.EAST);
            this.setPreferredSize(new Dimension(295, 25));
            left.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me) {
                    left.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    left.setForeground(Color.RED);
                }

                public void mouseExited(MouseEvent me) {
                    left.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    left.setForeground(Color.BLACK);
                }

                public void mousePressed(MouseEvent me) {
                    select.add(Calendar.MONTH, -1);
                    left.setForeground(Color.WHITE);
                    refresh();
                }

                public void mouseReleased(MouseEvent me) {
                    left.setForeground(Color.BLACK);
                }
            });
            right.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me) {
                    right.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    right.setForeground(Color.RED);
                }

                public void mouseExited(MouseEvent me) {
                    right.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    right.setForeground(Color.BLACK);
                }

                public void mousePressed(MouseEvent me) {
                    select.add(Calendar.MONTH, 1);
                    right.setForeground(Color.WHITE);
                    refresh();
                }

                public void mouseReleased(MouseEvent me) {
                    right.setForeground(Color.BLACK);
                }
            });
        }

        private void updateDate() {
            center.setText(select.get(Calendar.YEAR) + "年" + (select.get(Calendar.MONTH) + 1) + "月");
        }
    }

    private class JP2 extends JPanel {
        public JP2() {
            this.setPreferredSize(new Dimension(295, 20));
        }

        protected void paintComponent(Graphics g) {
            g.setFont(font);
            g.drawString("星期日 星期一 星期二 星期三 星期四 星期五 星期六", 5, 10);
            g.drawLine(0, 15, getWidth(), 15);
        }
    }

    private class JP3 extends JPanel {
        public JP3() {
            super(new GridLayout(6, 7));
            this.setPreferredSize(new Dimension(295, 100));
            initJP3();
        }

        private void initJP3() {
            updateDate();
        }

        public void updateDate() {
            this.removeAll();
            Calendar tempSelect = (Calendar) select.clone();
            tempSelect.set(Calendar.DAY_OF_MONTH, 1);
            int index = tempSelect.get(Calendar.DAY_OF_WEEK);
            int sum = (index == 1 ? 8 : index);
            tempSelect.add(Calendar.DAY_OF_MONTH, -sum);
            for (int i = 0; i < 42; i++) {
                tempSelect.add(Calendar.DAY_OF_MONTH, 1);
                this.add(new MyLabel(tempSelect.get(Calendar.YEAR),
                        tempSelect.get(Calendar.MONTH), tempSelect.get(Calendar.DAY_OF_MONTH)));
            }
            select.setTime(select.getTime());
        }
    }

    private class MyLabel extends JLabel implements MouseListener {
        private int year, month, day;
        private boolean isSelected;

        public MyLabel(int year, int month, int day) {
            super("" + day, JLabel.CENTER);
            this.year = year;
            this.month = month;
            this.day = day;
            this.addMouseListener(this);
            this.setFont(new Font("宋体", Font.PLAIN, 12));
            if (month == select.get(Calendar.MONTH)) {
                this.setForeground(Color.BLACK);
            } else {
                this.setForeground(Color.LIGHT_GRAY);
            }
            if (day == select.get(Calendar.DAY_OF_MONTH) &&
                    month == select.get(Calendar.MONTH) &&
                    year == select.get(Calendar.YEAR)) {
                this.setBackground(new Color(160, 185, 215));
            } else {
                this.setBackground(Color.WHITE);
            }
        }

        public void setSelected(boolean b) {
            isSelected = b;
            if (b) {
                int tempMonth = select.get(Calendar.MONTH);
                int tempYear = select.get(Calendar.YEAR);
                select.set(year, month, day);
                if (tempMonth != month || tempYear != year) {
                    refresh();
                } else {
                    SwingUtilities.updateComponentTreeUI(DateChooser.this);
                }
            }
            this.repaint();
        }

        protected void paintComponent(Graphics g) {
            if (day == select.get(Calendar.DAY_OF_MONTH) &&
                    month == select.get(Calendar.MONTH)) {
                g.setColor(new Color(160, 185, 215));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            if (year == now.get(Calendar.YEAR) &&
                    month == now.get(Calendar.MONTH) &&
                    day == now.get(Calendar.DAY_OF_MONTH)) {
                Graphics2D gd = (Graphics2D) g;
                gd.setColor(Color.RED);
                gd.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
            if (isSelected) {
                Graphics2D gd = (Graphics2D) g;
                gd.setColor(Color.BLACK);
                gd.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
            super.paintComponent(g);
        }

        public void mouseClicked(MouseEvent e) {
            setSelected(true);
            commit();
        }

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
    }

    private class JP4 extends JPanel {
        public JP4() {
            super(new BorderLayout());
            this.setPreferredSize(new Dimension(295, 20));
            this.setBackground(new Color(160, 185, 215));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            final JLabel jl = new JLabel("今天: " + sdf.format(new Date()));
            jl.setToolTipText("点击回到今天日期");
            this.add(jl, BorderLayout.CENTER);
            jl.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me) {
                    jl.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    jl.setForeground(Color.RED);
                }

                public void mouseExited(MouseEvent me) {
                    jl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    jl.setForeground(Color.BLACK);
                }

                public void mousePressed(MouseEvent me) {
                    jl.setForeground(Color.WHITE);
                    select.setTime(new Date());
                    refresh();
                    commit();
                }

                public void mouseReleased(MouseEvent me) {
                    jl.setForeground(Color.BLACK);
                }
            });
        }
    }
    /**
     * The main method for testing the DateChooser class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Date Chooser Test");
        DateChooser dateChooser = new DateChooser();
        frame.getContentPane().add(dateChooser);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

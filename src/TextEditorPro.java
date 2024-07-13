import java.awt.geom.Line2D;
import java.io.*;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


class textEditor extends JPanel implements ActionListener {

    int cnt =0;
    JFrame frameMain;
    JEditorPane editorPane;
    JLayeredPane tp2;

    textEditor()
    {
        frameMain = new JFrame("Text Editor");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {
        }

        //menu bar
        JMenuBar menuBar1 = new JMenuBar();

        JMenu Edit = new JMenu("Edit");

        JMenuItem Cut = new JMenuItem("Cut");

        JMenuItem Copy = new JMenuItem("Copy");

        JMenuItem Paste = new JMenuItem("Paste");

        Cut.addActionListener(this);
        Copy.addActionListener(this);
        Paste.addActionListener(this);

        Edit.add(Cut);
        Edit.add(Copy);
        Edit.add(Paste);

        JMenu File = new JMenu("File");

        JMenuItem New = new JMenuItem("New");

        JMenuItem Open = new JMenuItem("Open");

        JMenuItem Save = new JMenuItem("Save");

        New.addActionListener(this);
        Open.addActionListener(this);
        Save.addActionListener(this);

        File.add(New);
        File.add(Open);
        File.add(Save);

        JMenu Review = new JMenu("Review");
        JMenu Help = new JMenu("Help");
        JMenuItem Rev=new JMenuItem("Review");
        JMenuItem Hel=new JMenuItem("Help");
        Review.add(Rev);
        Help.add(Hel);
        Rev.addActionListener((ActionEvent r1)->{
            JOptionPane.showMessageDialog(this,"Done by A.Dheeraj Raj, 2110110119.");
        });
        Hel.addActionListener((ActionEvent r1)->{
            JOptionPane.showMessageDialog(this,"E-mail ar333@snu.edu.in for any queries.");
        });

        menuBar1.add(File);
        menuBar1.add(Edit);
        menuBar1.add(Review);
        menuBar1.add(Help);

        //container
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        //panel 1
        JPanel panel1 = new JPanel();
        BoxLayout boxlayout1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        panel1.setLayout(boxlayout1);
        panel1.setBorder(BorderFactory.createLineBorder(Color.black,2));

        //panel 1 text-area
        editorPane = new JEditorPane();

        //panel 1 menu-bar font-size
        JMenuBar panel1MB = new JMenuBar();


        JButton Bold = new JButton("B");
        JButton Italics = new JButton("I");
        JButton Underline = new JButton("U");
        JButton UpperCase = new JButton("UpperCase");
        UpperCase.addActionListener(e -> editorPane.replaceSelection(editorPane.getSelectedText().toUpperCase(Locale.ROOT))
        );
        JButton LowerCase = new JButton("LowerCase");
        LowerCase.addActionListener(e -> editorPane.replaceSelection(editorPane.getSelectedText().toLowerCase(Locale.ROOT))
        );

        String[] fontTypes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Integer[] fontSize = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        JComboBox fontList = new JComboBox(fontTypes);
        JComboBox fontSizes = new JComboBox(fontSize);

        StyledDocument doc = new DefaultStyledDocument();
        StyledEditorKit styledEditorKit = new StyledEditorKit();

        editorPane.setDocument(doc);
        editorPane.setEditorKit(styledEditorKit);

        JScrollPane scrollpane = new JScrollPane(editorPane);
        scrollpane.setPreferredSize(new Dimension(900, 600));

        JPanel comboPanel = new JPanel();
        comboPanel.add(fontSizes);

        for (int i = 0; i < 20; i++) {
            int offset = doc.getLength();
            String str = "This is line number: " + i + "\n";
            try {
                doc.insertString(offset, str, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        fontSizes.addActionListener(e -> {
            int size = (Integer) fontSizes.getSelectedItem();

            Action fontAction = new StyledEditorKit.FontSizeAction(String
                    .valueOf(size), size);

            fontAction.actionPerformed(e);
        });

        fontList.addActionListener(e -> {
            String font = (String) fontList.getSelectedItem();
            Action fontAction = new StyledEditorKit.FontFamilyAction(font,font);
            fontAction.actionPerformed(e);
        });

        Bold.addActionListener(e -> {
            Action boldAction = new StyledEditorKit.BoldAction();
            boldAction.actionPerformed(e);
        });
        Italics.addActionListener(e -> {
            Action italicAction = new StyledEditorKit.ItalicAction();
            italicAction.actionPerformed(e);
        });
        Underline.addActionListener(e -> {
            Action underlineAction = new StyledEditorKit.UnderlineAction();
            underlineAction.actionPerformed(e);
        });

        panel1MB.add(Bold);
        panel1MB.add(Italics);
        panel1MB.add(Underline);
        panel1MB.add(UpperCase);
        panel1MB.add(LowerCase);
        panel1MB.add(fontList);
        panel1MB.add(fontSizes);

        //panel 1 find-replace
        JLabel find = new JLabel("Find");
        JTextField findtext = new JTextField();
        JLabel replace = new JLabel("Replace");
        JTextField replacetext = new JTextField();



        //panel 1 menu-bar find all replace all buttons
        JMenuBar panel1mb2 = new JMenuBar();
        JButton findAll = new JButton("Find All");
        JButton findNext = new JButton("Find Next");
        JButton repl = new JButton("Replace");
        JButton replaceAll = new JButton("Replace All");
        JButton count = new JButton("UpdateCount");




        findAll.addActionListener(e -> {
            try{
                String findstr =findtext.getText();
                if(findstr.isEmpty()) throw new NullPointerException();

                String text=editorPane.getText();
                Highlighter.HighlightPainter highlighterpainter =
                        new DefaultHighlighter.DefaultHighlightPainter(Color.green);
                Highlighter highlighter = editorPane.getHighlighter();
                int cnt=0;
                while(true)
                {
                    if ((cnt = text.toUpperCase().indexOf(findstr.toUpperCase(), cnt)) < 0) break;
                    highlighter.addHighlight(cnt, cnt+findstr.length(), highlighterpainter);
                    cnt+=findstr.length();
                }
            }
            catch(BadLocationException ble) {JOptionPane.showMessageDialog(null, "Location Exception");}
            catch(NullPointerException ae1) {JOptionPane.showMessageDialog(null, "Please Enter Text");}
        });

        findNext.addActionListener(e -> {
            try{
                String findstr =findtext.getText();
                if(findstr.isEmpty()) throw new NullPointerException();

                String text=editorPane.getText();
                Highlighter.HighlightPainter painters =
                        new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
                Highlighter highlighter = editorPane.getHighlighter();
                while((cnt =text.toUpperCase().indexOf(findstr.toUpperCase(), cnt))>=0)
                {
                    highlighter.removeAllHighlights();
                    highlighter.addHighlight(cnt, cnt +findstr.length(), painters);
                    cnt +=findstr.length();
                    break;
                }

            }
            catch(BadLocationException ble) {JOptionPane.showMessageDialog(null, "Location Exception");}
            catch(NullPointerException ae1) {JOptionPane.showMessageDialog(null, "Please Enter Text");}


        });

        replaceAll.addActionListener(e -> {
            String st= editorPane.getText();
            String st1=editorPane.getText();

            if(st.isEmpty() || st.trim().isEmpty() )
                JOptionPane.showMessageDialog(null, "Please Enter Text");
            else{
                try{
                    String str1=findtext.getText();
                    String str2=replacetext.getText();

                    if(!st.contains(str1))
                        JOptionPane.showMessageDialog(null, "No "+str1+" in the editorPane.");

                    st=st.replaceAll(str1, str2);
                    if(st.equals(st1))
                        JOptionPane.showMessageDialog(null, "Text is same.");
                    editorPane.setText(st);

                }
                catch(NullPointerException ae1) {JOptionPane.showMessageDialog(null, "Please Enter Text");}

            }

        });

        repl.addActionListener(e -> {
            String st= editorPane.getText();
            String st1=editorPane.getText();

            if(st.isEmpty() || st.trim().isEmpty() )
                JOptionPane.showMessageDialog(null, "Please Enter Text");
            else{
                try{
                    String str1=findtext.getText();
                    String str2=replacetext.getText();

                    if(!st.contains(str1)) JOptionPane.showMessageDialog(null, "No "+str1+" in the editorPane.");

                    st=st.replaceFirst(str1, str2);
                    if(st.equals(st1)) JOptionPane.showMessageDialog(null, "Text is same.");
                    editorPane.setText(st);

                }
                catch(NullPointerException ae1) {JOptionPane.showMessageDialog(null, "Please Enter Text");}

            }
        });

        panel1mb2.add(findAll);
        panel1mb2.add(findNext);
        panel1mb2.add(repl);
        panel1mb2.add(replaceAll);
        panel1mb2.add(count);

        panel1.add(panel1MB);
        panel1.add(find);
        panel1.add(findtext);
        panel1.add(replace);
        panel1.add(replacetext);
        panel1.add(panel1mb2);
        panel1.add(scrollpane);

        //panel 2
        JPanel panel2 = new JPanel();
        BoxLayout boxlayout2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxlayout2);
        panel2.setBorder(BorderFactory.createLineBorder(Color.black,2));

        JLabel p2label = new JLabel("SketchPad");

        //panel 2 menu-bar
        JMenuBar panel2MB = new JMenuBar();

        JButton Rectangle = new JButton("Rectangle");
        JButton Oval = new JButton("Oval");
        JButton Circle = new JButton("Circle");
        JButton Line = new JButton("Line");
        JButton Square = new JButton("Square");
        JButton Clear = new JButton("Clear");


        JFrame Rectanglefig = new JFrame("Rectangle");
        JFrame Ovalfig = new JFrame("Oval");
        JFrame Circlefig = new JFrame("Circle");
        JFrame Linefig = new JFrame("Line");
        JFrame Squarefig = new JFrame("Square");

        Squarefig.setSize(250, 250);
        Rectanglefig.setSize(250, 250);
        Circlefig.setSize(250, 250);
        Ovalfig.setSize(250,250);
        Linefig.setSize(250,250);
        Ovalfig.setLocationRelativeTo(null);
        Circlefig.setLocationRelativeTo(null);
        Rectanglefig.setLocationRelativeTo(null);
        Squarefig.setLocationRelativeTo(null);
        Linefig.setLocationRelativeTo(null);

        panel2MB.add(Rectangle);
        panel2MB.add(Oval);
        panel2MB.add(Circle);
        panel2MB.add(Line);
        panel2MB.add(Square);
        panel2MB.add(Clear);


        class ResizeRectangle extends JPanel {
            int Mass = 8;

            Rectangle2D[] mP = { new Rectangle2D.Double(50, 50, Mass, Mass),
                    new Rectangle2D.Double(150, 100, Mass, Mass),
                    new Rectangle2D.Double(100, 75, Mass, Mass)};
            Rectangle2D rec = new Rectangle2D.Double();

            ShapeResizeHandler ada = new ShapeResizeHandler();

            public ResizeRectangle() {

                addMouseListener(ada);
                addMouseMotionListener(ada);

            }

            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2D = (Graphics2D) g;

                for (Rectangle2D markedPoint : mP) {
                    g2D.fill(markedPoint);
                }
                rec.setFrame(mP[0].getCenterX(), mP[0].getCenterY(),
                        Math.abs(mP[1].getCenterX()- mP[0].getCenterX()),
                        Math.abs(mP[1].getCenterY()- mP[0].getCenterY()));
                g2D.draw(rec);
                repaint();



            }

            class ShapeResizeHandler extends MouseAdapter {

                Point2D[] lastPoints = new Point2D[3];
                int pos = -1;
                public void mousePressed(MouseEvent eve) {
                    Point p = eve.getPoint();

                    for (int i = 0; i < mP.length; i++) {
                        if (mP[i].contains(p)) {
                            pos = i;
                            for(int j = 0; j < 3; j++){
                                lastPoints[j] = new Point2D.Double(mP[j].getX(), mP[j].getY());
                            }
                            return;
                        }
                    }
                }

                public void mouseReleased(MouseEvent eve) {
                    pos = -1;
                }

                public void mouseDragged(MouseEvent eve) {
                    if (pos == -1)
                        return;
                    if(pos != 2){
                        mP[pos].setRect(eve.getPoint().x,eve.getPoint().y, mP[pos].getWidth(),
                                mP[pos].getHeight());
                        int otherEnd = (pos==1)?2:1;
                        double newPoint2X = mP[otherEnd].getX() + (mP[pos].getX() - mP[otherEnd].getX())/2;
                        double newPoint2Y = mP[otherEnd].getY() + (mP[pos].getY() - mP[otherEnd].getY())/2;
                        mP[2].setRect(newPoint2X, newPoint2Y, mP[2].getWidth(), mP[2].getHeight());
                    }
                    else{
                        Double deltaX = eve.getPoint().x - lastPoints[2].getX();
                        Double deltaY = eve.getPoint().y - lastPoints[2].getY();
                        for(int j = 0; j < 3; j++)
                            mP[j].setRect((lastPoints[j].getX() + deltaX),(lastPoints[j].getY() + deltaY), mP[j].getWidth(),
                                    mP[j].getHeight());

                    }
                    repaint();
                }
            }
        }

        class ResizeCircle extends JPanel {

            int Mass = 5;

            Rectangle2D[] mP = { new Rectangle2D.Double(100, 100, Mass, Mass),
                    new Rectangle2D.Double(200, 200, Mass, Mass),
                    new Rectangle2D.Double(150, 150, Mass, Mass)};
            Ellipse2D elip = new Ellipse2D.Double();

            ShapeResizeHandler srh = new ShapeResizeHandler();

            public ResizeCircle() {

                addMouseListener(srh);
                addMouseMotionListener(srh);

            }

            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2D = (Graphics2D) g;

                for (Rectangle2D markedPoint : mP) {
                    g2D.fill(markedPoint);
                }
                elip.setFrame(mP[0].getCenterX(), mP[0].getCenterY(),
                        Math.abs(mP[1].getCenterX()-mP[0].getCenterX()),
                        Math.abs(mP[1].getCenterY()- mP[0].getCenterY()));
                g2D.draw(elip);



            }

            class ShapeResizeHandler extends MouseAdapter {

                Point2D[] lastPoints = new Point2D[3];
                int pos = -1;
                public void mousePressed(MouseEvent eve) {
                    Point p = eve.getPoint();

                    for (int i = 0; i < mP.length; i++) {
                        if (mP[i].contains(p)) {
                            pos = i;
                            for(int j = 0; j < 3; j++){
                                lastPoints[j] = new Point2D.Double(mP[j].getX(), mP[j].getY());
                            }
                            return;
                        }
                    }
                }
                public void mouseReleased(MouseEvent eve) {
                    pos = -1;
                }

                public void mouseDragged(MouseEvent eve) {
                    if (pos == -1)
                        return;
                    if(pos != 2){
                        mP[pos].setRect(eve.getPoint().x,eve.getPoint().y,mP[pos].getWidth(),
                                mP[pos].getHeight());
                        int otherEnd = (pos==1)?2:1;
                        double newPoint2X = mP[otherEnd].getX() + (mP[pos].getX() - mP[otherEnd].getX())/2;
                        double newPoint2Y = mP[otherEnd].getY() + (mP[pos].getY() - mP[otherEnd].getY())/2;
                        mP[2].setRect(newPoint2X, newPoint2Y, mP[2].getWidth(), mP[2].getHeight());
                    }
                    else{
                        Double deltaX = eve.getPoint().x - lastPoints[2].getX();
                        Double deltaY = eve.getPoint().y - lastPoints[2].getY();
                        for(int j = 0; j < 3; j++)
                            mP[j].setRect((lastPoints[j].getX() + deltaX),(lastPoints[j].getY() + deltaY),mP[j].getWidth(),
                                    mP[j].getHeight());

                    }

                    repaint();

                }
            }
        }
        class ResizeSquare extends JPanel {
            int Mass = 8;
            //we have made three points, markedPoints[0] and [1] and top-left and bottom-right of the shape.
            //one point is the center of the shape
            Rectangle2D[] mP = { new Rectangle2D.Double(50, 50, Mass, Mass),
                    new Rectangle2D.Double(150, 150, Mass, Mass),
                    new Rectangle2D.Double(100, 100, Mass, Mass)};
            Rectangle2D rect = new Rectangle2D.Double();

            ShapeResizeHandler ada = new ShapeResizeHandler();

            public ResizeSquare() {
                addMouseListener(ada);
                addMouseMotionListener(ada);
            }


            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2D = (Graphics2D) g;

                for (Rectangle2D markedPoint : mP) {
                    g2D.fill(markedPoint);
                }
                rect.setFrame(mP[0].getCenterX(), mP[0].getCenterY(),
                        Math.abs(mP[1].getCenterX()- mP[0].getCenterX()),
                        Math.abs(mP[1].getCenterY()- mP[0].getCenterY()));
                g2D.draw(rect);
                repaint();
            }

            class ShapeResizeHandler extends MouseAdapter {

                Point2D[] lastPoints = new Point2D[3];
                int pos = -1;
                public void mousePressed(MouseEvent eve) {
                    Point p = eve.getPoint();

                    for (int i = 0; i < mP.length; i++) {
                        if (mP[i].contains(p)) {
                            pos = i;
                            // initialize preDrag points
                            for(int j = 0; j < 3; j++){
                                lastPoints[j] = new Point2D.Double(mP[j].getX(), mP[j].getY());
                            }
                            return;
                        }
                    }
                }

                public void mouseReleased(MouseEvent eve) {
                    pos = -1;
                }

                public void mouseDragged(MouseEvent eve) {
                    if (pos == -1)
                        return;
                    if(pos != 2){
                        mP[pos].setRect(eve.getPoint().x,eve.getPoint().y, mP[pos].getWidth(),
                                mP[pos].getHeight());
                        int otherEnd = (pos==1)?2:1;
                        double newPoint2X = mP[otherEnd].getX() + (mP[pos].getX() - mP[otherEnd].getX())/2;
                        double newPoint2Y = mP[otherEnd].getY() + (mP[pos].getY() - mP[otherEnd].getY())/2;
                        mP[2].setRect(newPoint2X, newPoint2Y, mP[2].getWidth(), mP[2].getHeight());
                    }
                    else{
                        Double deltaX = eve.getPoint().x - lastPoints[2].getX();
                        Double deltaY = eve.getPoint().y - lastPoints[2].getY();
                        for(int j = 0; j < 3; j++)
                            mP[j].setRect((lastPoints[j].getX() + deltaX),(lastPoints[j].getY() + deltaY), mP[j].getWidth(),
                                    mP[j].getHeight());

                    }
                    repaint();

                }
            }
        }

        class ResizeOval extends JPanel {
            int Mass = 8;

            Rectangle2D[] mP = { new Rectangle2D.Double(50, 50, Mass, Mass),
                    new Rectangle2D.Double(150, 100, Mass, Mass),
                    new Rectangle2D.Double(100, 75, Mass, Mass)};
            Ellipse2D ellip = new Ellipse2D.Double();

            ShapeResizeHandler ada = new ShapeResizeHandler();

            public ResizeOval() {
                addMouseListener(ada);
                addMouseMotionListener(ada);

            }


            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2D = (Graphics2D) g;

                for (Rectangle2D markedPoint : mP) {
                    g2D.fill(markedPoint);
                }
                ellip.setFrame(mP[0].getCenterX(), mP[0].getCenterY(),
                        Math.abs(mP[1].getCenterX()- mP[0].getCenterX()),
                        Math.abs(mP[1].getCenterY()- mP[0].getCenterY()));
                g2D.draw(ellip);
                repaint();

            }

            class ShapeResizeHandler extends MouseAdapter {

                Point2D[] lastPoints = new Point2D[3];
                int pos = -1;
                public void mousePressed(MouseEvent eve) {
                    Point p = eve.getPoint();

                    for (int i = 0; i < mP.length; i++) {
                        if (mP[i].contains(p)) {
                            pos = i;
                            for(int j = 0; j < 3; j++){
                                lastPoints[j] = new Point2D.Double(mP[j].getX(), mP[j].getY());
                            }
                            return;
                        }
                    }
                }

                public void mouseReleased(MouseEvent eve) {
                    pos = -1;
                }

                public void mouseDragged(MouseEvent eve) {
                    if (pos == -1)
                        return;
                    if(pos != 2){
                        mP[pos].setRect(eve.getPoint().x,eve.getPoint().y, mP[pos].getWidth(),
                                mP[pos].getHeight());
                        int otherEnd = (pos==1)?2:1;
                        double newPoint2X = mP[otherEnd].getX() + (mP[pos].getX() - mP[otherEnd].getX())/2;
                        double newPoint2Y = mP[otherEnd].getY() + (mP[pos].getY() - mP[otherEnd].getY())/2;
                        mP[2].setRect(newPoint2X, newPoint2Y, mP[2].getWidth(), mP[2].getHeight());
                    }
                    else{
                        Double deltaX = eve.getPoint().x - lastPoints[2].getX();
                        Double deltaY = eve.getPoint().y - lastPoints[2].getY();
                        for(int j = 0; j < 3; j++)
                            mP[j].setRect((lastPoints[j].getX() + deltaX),(lastPoints[j].getY() + deltaY), mP[j].getWidth(),
                                    mP[j].getHeight());

                    }
                    repaint();

                }
            }
        }


        class ResizeLine extends JPanel {
            int Mass = 8;
            //we have made three points, markedPoints[0] and [1] and top-left and bottom-right of the shape.
            //one point is the center of the shape
            Rectangle2D[] mP = { new Rectangle2D.Double(50, 50, Mass, Mass),
                    new Rectangle2D.Double(150, 150, Mass, Mass)};

            Line2D rect = new Line2D.Double();

            ShapeResizeHandler ada = new ShapeResizeHandler();

            public ResizeLine() {

                addMouseListener(ada);
                addMouseMotionListener(ada);

            }
            public void paintComponent(Graphics g) {


                super.paintComponent(g);

                Graphics2D g2D = (Graphics2D) g;

                for (Rectangle2D markedPoint : mP) {
                    g2D.fill(markedPoint);
                }
                rect.setLine(mP[0].getCenterX(), mP[0].getCenterY(),
                        Math.abs(mP[1].getCenterX()- mP[0].getCenterX()),
                        Math.abs(mP[1].getCenterY()- mP[0].getCenterY()));
                g2D.draw(rect);
                repaint();



            }




            class ShapeResizeHandler extends MouseAdapter {

                Point2D[] lastPoints = new Point2D[3];
                int pos = -1;
                public void mousePressed(MouseEvent eve) {
                    Point p = eve.getPoint();

                    for (int i = 0; i < mP.length; i++) {
                        if (mP[i].contains(p)) {
                            pos = i;
                            // initialize preDrag markedPoints
                            for(int j = 0; j < 2; j++){
                                lastPoints[j] = new Point2D.Double(mP[j].getX(), mP[j].getY());
                            }
                            return;
                        }
                    }
                }

                public void mouseDragged(MouseEvent eve) {
                    if (pos == -1)
                        return;
                    if(pos != 2){
                        mP[pos].setRect(eve.getPoint().x,eve.getPoint().y, mP[pos].getWidth(),
                                mP[pos].getHeight());
                        int otherEnd = (pos==1)?2:1;
                        double newPoint2X = mP[otherEnd].getX() + (mP[pos].getX() - mP[otherEnd].getX())/2;
                        double newPoint2Y = mP[otherEnd].getY() + (mP[pos].getY() - mP[otherEnd].getY())/2;
                        mP[2].setRect(newPoint2X, newPoint2Y, mP[2].getWidth(), mP[2].getHeight());
                    }
                    else{
                        Double deltaX = eve.getPoint().x - lastPoints[2].getX();
                        Double deltaY = eve.getPoint().y - lastPoints[2].getY();
                        for(int j = 0; j < 2; j++)
                            mP[j].setRect((lastPoints[j].getX() + deltaX),(lastPoints[j].getY() + deltaY), mP[j].getWidth(),
                                    mP[j].getHeight());

                    }
                    repaint();
                }

                public void mouseReleased(MouseEvent eve) {
                    pos = -1;
                }


            }
        }

        Rectangle.addActionListener(e->{
            Rectanglefig.add(new ResizeRectangle());
            Squarefig.dispose();
            Rectanglefig.setVisible(true);
            Circlefig.dispose();
            Ovalfig.dispose();
            Linefig.dispose();
        });
        Oval.addActionListener(e->{
            Ovalfig.add(new ResizeOval());
            Squarefig.dispose();
            Ovalfig.setVisible(true);
            Circlefig.dispose();
            Rectanglefig.dispose();
            Linefig.dispose();
        });
        Circle.addActionListener(e->{
            Circlefig.add(new ResizeCircle());
            Squarefig.dispose();
            Circlefig.setVisible(true);
            Rectanglefig.dispose();
            Ovalfig.dispose();
            Linefig.dispose();
        });
        Line.addActionListener(e->{
            Linefig.add(new ResizeLine());
            Squarefig.dispose();
            Linefig.setVisible(true);
            Circlefig.dispose();
            Ovalfig.dispose();
            Rectanglefig.dispose();
        });
        Square.addActionListener(e->{
            Squarefig.add(new ResizeSquare());
            Rectanglefig.dispose();
            Squarefig.setVisible(true);
            Circlefig.dispose();
            Ovalfig.dispose();
            Linefig.dispose();
        });


        //panel 2 tex-area
        tp2 = new JLayeredPane();
        tp2.setBackground(Color.lightGray);

        //panel 2 count label
        JLabel counttxt = new JLabel("Word Count:  Character Count:   ");
        counttxt.setAlignmentX(SwingConstants.LEFT);

        count.addActionListener(e ->{
            String str= editorPane.getSelectedText();

            int y1=0;
            int y2=str.length();
            String y3=str.substring(y1, y2);

            String[] wordArray = y3.trim().split("\\s+");
            int word = wordArray.length;
            int chr=0;

            for (int ik = 0; ik < y3.length(); ik++) {
                if (Character.isDefined(y3.charAt(ik)))
                    chr++;
            }
            counttxt.setText("Word Count:"+word+"     Character Count:   "+chr);

        });



        panel2.add(p2label);
        panel2.add(panel2MB);
        panel2.add(tp2);
        panel2.add(counttxt);

        container.add(panel1);
        container.add(panel2);

        frameMain.setJMenuBar(menuBar1);
        frameMain.add(container);
        frameMain.setSize(1300, 1000);
        frameMain.setVisible(true);

    }




    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        switch (s) {
            case "paste" -> editorPane.paste();
            case "copy" -> editorPane.copy();
            case "cut" -> editorPane.cut();
            case "Save" -> {
                JFileChooser jfileChooser = new JFileChooser("f:");
                int r = jfileChooser.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    File fi = new File(jfileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        FileWriter wr = new FileWriter(fi, false);
                        BufferedWriter buffWrite = new BufferedWriter(wr);
                        buffWrite.write(editorPane.getText());
                        buffWrite.flush();
                        buffWrite.close();
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(frameMain, evt.getMessage());
                    }
                }
                else
                    JOptionPane.showMessageDialog(frameMain, "Cancelled");
            }
            case "Open" -> {
                JFileChooser jfileChooser = new JFileChooser("f:");
                int rnull = jfileChooser.showOpenDialog(null);
                if (rnull == JFileChooser.APPROVE_OPTION) {
                    File jfileFile = new File(jfileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        String str1;
                        StringBuilder sb1;

                        FileReader fr = new FileReader(jfileFile);
                        BufferedReader br = new BufferedReader(fr);

                        sb1 = new StringBuilder(br.readLine());

                        while ((str1 = br.readLine()) != null) {
                            sb1.append("\n").append(str1);
                        }
                        editorPane.setText(sb1.toString());
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(frameMain, evt.getMessage());
                    }
                }
                else
                    JOptionPane.showMessageDialog(frameMain, "Cancelled");
            }
            case "New" -> editorPane.setText("");
        }
    }
    public static void main(String[] args)
    {
        textEditor e = new textEditor();
    }
}

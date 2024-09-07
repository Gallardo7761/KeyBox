/*
 * Created by JFormDesigner on Sat Sep 07 03:40:40 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import javax.swing.*;

import dev.gallardo.kb.util.Constants;
import dev.gallardo.kb.util.UIUtil;
import net.miginfocom.swing.*;
import jiconfont.icons.font_awesome.FontAwesome;

/**
 * @author jomaa
 */
public class UIKeyBox extends JFrame {
    private static UIKeyBox instance = null;

    private UIKeyBox() {
        initComponents();
        UIUtil.setTitle("KeyBox v" + Constants.APP_VERSION, this);
        UIUtil.setButtonIcon(createBtn, FontAwesome.PLUS, 24f, Constants.COLOR_PRIMARY);
        UIUtil.setButtonIcon(editBtn, FontAwesome.PENCIL, 24f, Constants.COLOR_PRIMARY);
        UIUtil.setButtonIcon(deleteBtn, FontAwesome.TRASH, 24f, Constants.COLOR_PRIMARY);
    }

    public static UIKeyBox getInstance() {
        if (instance == null) {
            instance = new UIKeyBox();
        }
        return instance;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        toolBar1 = new JToolBar();
        createBtn = new JButton();
        editBtn = new JButton();
        deleteBtn = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/images/keybox_icon_64.png")).getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill,grow]",
            // rows
            "[fill]" +
            "[fill,grow]"));

        //======== toolBar1 ========
        {

            //---- createBtn ----
            createBtn.setPreferredSize(new Dimension(32, 32));
            createBtn.setMinimumSize(new Dimension(32, 32));
            createBtn.setMaximumSize(new Dimension(32, 32));
            toolBar1.add(createBtn);

            //---- editBtn ----
            editBtn.setPreferredSize(new Dimension(32, 32));
            editBtn.setMinimumSize(new Dimension(32, 32));
            editBtn.setMaximumSize(new Dimension(32, 32));
            toolBar1.add(editBtn);

            //---- deleteBtn ----
            deleteBtn.setPreferredSize(new Dimension(32, 32));
            deleteBtn.setMinimumSize(new Dimension(32, 32));
            deleteBtn.setMaximumSize(new Dimension(32, 32));
            toolBar1.add(deleteBtn);
        }
        contentPane.add(toolBar1, "cell 0 0");
        setSize(900, 600);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JToolBar toolBar1;
    private JButton createBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}

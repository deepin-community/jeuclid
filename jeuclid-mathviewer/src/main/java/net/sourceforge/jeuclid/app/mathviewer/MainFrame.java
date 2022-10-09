/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: MainFrame.java,v 9fcde7bec879 2009/04/04 20:27:55 maxberger $ */

package net.sourceforge.jeuclid.app.mathviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sourceforge.jeuclid.MathMLSerializer;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.swing.JMathComponent;

import org.w3c.dom.Document;

/**
 * Main frame for the MathViewer application.
 * 
 * @version $Revision: 9fcde7bec879 $
 */
// CHECKSTYLE:OFF
public class MainFrame extends JFrame {
    // CHECKSTYLE:ON
    private static final int DEFAULT_HEIGHT = 200;

    private static final int DEFAULT_WIDTH = 350;

    private static final FileIO FILEIO = FileIO.getInstance();

    // /**
    // * Logger for this class
    // */
    // currently unused.
    // private static final Log LOGGER = LogFactory.getLog(MathViewer.class);

    private static final long serialVersionUID = 1L;

    private static final float FONT_SIZE_MULTIPLICATOR = 1.20f;

    private JPanel jContentPane;

    private JMenuBar jJMenuBar;

    private JMenu fileMenu;

    private JMenu editMenu;

    private JMenu helpMenu;

    private JMenuItem exitMenuItem;

    private JMenuItem unformattedCopyMenuItem;

    private JMenuItem formattedCopyMenuItem;

    private JMenuItem pasteMenuItem;

    private JMenuItem aboutMenuItem;

    private JMenuItem openMenuItem;

    private JDialog aboutDialog;

    private JSplitPane splitPane;

    private JScrollPane scrollPane;

    private JScrollPane scrollPane2;

    private JTextArea textArea;

    private JMathComponent mathComponent;

    private JMenu viewMenu;

    private JMenuItem biggerMenuItem;

    private JMenuItem smallerMenuItem;

    private JMenuItem exportMenuItem;

    private JCheckBoxMenuItem aliasMenuItem;

    private JCheckBoxMenuItem debugMenuItem;

    /**
     * This is the default constructor.
     */
    public MainFrame() {
        super();
        this.initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setJMenuBar(this.getJJMenuBar());
        this.setSize(MainFrame.DEFAULT_WIDTH, MainFrame.DEFAULT_HEIGHT);
        this.setContentPane(this.getJContentPane());
        this.setTitle(Messages.getString("MathViewer.windowTitle")); //$NON-NLS-1$
        this.setLocationByPlatform(true);
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (this.jContentPane == null) {
            this.jContentPane = new JPanel();
            this.jContentPane.setLayout(new BorderLayout());
            this.jContentPane.add(this.getSplitPane(), BorderLayout.CENTER);
        }
        return this.jContentPane;
    }

    /**
     * This method initializes jJMenuBar
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getJJMenuBar() {
        if (this.jJMenuBar == null) {
            this.jJMenuBar = new JMenuBar();
            this.jJMenuBar.add(this.getFileMenu());
            this.jJMenuBar.add(this.getEditMenu());
            this.jJMenuBar.add(this.getViewMenu());
            if (!MathViewer.OSX) {
                // This will need to be changed once the Help menu contains
                // more that just the About item.
                this.jJMenuBar.add(this.getHelpMenu());
            }
        }
        return this.jJMenuBar;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getFileMenu() {
        if (this.fileMenu == null) {
            this.fileMenu = new JMenu();
            this.fileMenu.setText(Messages.getString("MathViewer.FileMenu")); //$NON-NLS-1$
            this.fileMenu.add(this.getOpenMenuItem());
            this.fileMenu.add(this.getExportMenuItem());
            if (!MathViewer.OSX) {
                this.fileMenu.add(this.getExitMenuItem());
            }
        }
        return this.fileMenu;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getEditMenu() {
        if (this.editMenu == null) {
            this.editMenu = new JMenu();
            this.editMenu.setText(Messages.getString("MathViewer.EditMenu")); //$NON-NLS-1$
            this.editMenu.add(this.getUnformattedCopyMenuItem());
            this.editMenu.add(this.getFormattedCopyMenuItem());
            this.editMenu.add(this.getPasteMenuItem());
        }
        return this.editMenu;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getHelpMenu() {
        if (this.helpMenu == null) {
            this.helpMenu = new JMenu();
            this.helpMenu.setText(Messages.getString("MathViewer.helpMenu")); //$NON-NLS-1$
            // If there are more items, please modify getJJMenuBar to always
            // display the help menu and this function to not display about on
            // OS X
            this.helpMenu.add(this.getAboutMenuItem());
        }
        return this.helpMenu;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getExitMenuItem() {
        if (this.exitMenuItem == null) {
            this.exitMenuItem = new JMenuItem();
            this.exitMenuItem.setText(Messages.getString("MathViewer.exit")); //$NON-NLS-1$
            this.exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_Q, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));

            this.exitMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return this.exitMenuItem;
    }

    private JMenuItem getUnformattedCopyMenuItem() {
        if (this.unformattedCopyMenuItem == null) {
            this.unformattedCopyMenuItem = new JMenuItem();
            this.unformattedCopyMenuItem.setText(Messages
                    .getString("MathViewer.unformattedCopy")); //$NON-NLS-1$
            this.unformattedCopyMenuItem.setAccelerator(KeyStroke
                    .getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));

            this.unformattedCopyMenuItem
                    .addActionListener(new ActionListener() {
                        public void actionPerformed(final ActionEvent e) {
                            MainFrame.this.copyToClipboard(false);
                        }
                    });
        }
        return this.unformattedCopyMenuItem;
    }

    private JMenuItem getFormattedCopyMenuItem() {
        if (this.formattedCopyMenuItem == null) {
            this.formattedCopyMenuItem = new JMenuItem();
            this.formattedCopyMenuItem.setText(Messages
                    .getString("MathViewer.formattedCopy")); //$NON-NLS-1$
            this.formattedCopyMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_C, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask()
                            | InputEvent.SHIFT_DOWN_MASK, true));

            this.formattedCopyMenuItem
                    .addActionListener(new ActionListener() {
                        public void actionPerformed(final ActionEvent e) {
                            MainFrame.this.copyToClipboard(true);
                        }
                    });
        }
        return this.formattedCopyMenuItem;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getPasteMenuItem() {
        if (this.pasteMenuItem == null) {
            this.pasteMenuItem = new JMenuItem();
            this.pasteMenuItem
                    .setText(Messages.getString("MathViewer.paste")); //$NON-NLS-1$
            this.pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_V, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));

            this.pasteMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    MainFrame.this.pasteFromClipboard();
                }
            });
        }
        return this.pasteMenuItem;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getAboutMenuItem() {
        if (this.aboutMenuItem == null) {
            this.aboutMenuItem = new JMenuItem();
            this.aboutMenuItem.setText(Messages
                    .getString("MathViewer.aboutMenuItem")); //$NON-NLS-1$
            this.aboutMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    MainFrame.this.displayAbout();
                }
            });
        }
        return this.aboutMenuItem;
    }

    /**
     * Display the about dialog.
     */
    public void displayAbout() {
        final JDialog aDialog = MainFrame.this.getAboutDialog();
        aDialog.pack();
        final Point loc = MainFrame.this.getLocation();
        loc
                .translate(
                        (MainFrame.this.getWidth() - aDialog.getWidth()) / 2,
                        0);
        aDialog.setLocation(loc);
        aDialog.setVisible(true);

    }

    /**
     * This method initializes aboutDialog
     * 
     * @return javax.swing.JDialog
     */
    private JDialog getAboutDialog() {
        if (this.aboutDialog == null) {
            this.aboutDialog = new AboutDialog(this);
        }
        return this.aboutDialog;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getOpenMenuItem() {
        if (this.openMenuItem == null) {
            this.openMenuItem = new JMenuItem();
            this.openMenuItem.setText(Messages.getString("MathViewer.open")); //$NON-NLS-1$
            this.openMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_O, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));
            this.openMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            MainFrame.this.openFile();
                        }
                    });
        }
        return this.openMenuItem;
    }

    /**
     * Try to load a given file into this frame.
     * 
     * @param f
     *            reference to the file.
     */
    public void loadFile(final File f) {
        final Document doc = MainFrame.FILEIO.loadFile(this, f);
        if (doc != null) {
            this.getMathComponent().setDocument(doc);
            this.getTextArea().setText(
                    MathMLSerializer.serializeDocument(doc, false, false));
        }

    }

    /**
     * carries out the actual file-open procedure.
     */
    protected void openFile() {
        final File file = MainFrame.FILEIO.selectFileToOpen(this);
        this.loadFile(file);
    }

    /**
     * This method initializes splitPane
     * 
     * @return {@link JSplitPane}
     */
    private JSplitPane getSplitPane() {
        if (this.splitPane == null) {
            this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this
                    .getScrollPane(), this.getScrollPane2());
            this.splitPane.setOneTouchExpandable(true);
            this.splitPane.setResizeWeight(1.0);
        }
        return this.splitPane;
    }

    /**
     * This method initializes textArea
     * 
     * @return {@link JTextArea}
     */
    private JTextArea getTextArea() {
        if (this.textArea == null) {
            this.textArea = new JTextArea();
            this.textArea.setText("<?xml version='1.0'?>\n"
                    + "<math xmlns='http://www.w3.org/1998/Math/MathML'>\n"
                    + "</math>");
            this.textArea.setEditable(true);
            this.textArea.getDocument().addDocumentListener(
                    new DocumentListener() {

                        public void changedUpdate(
                                final DocumentEvent documentevent) {
                            MainFrame.this.updateFromTextArea();
                        }

                        public void insertUpdate(
                                final DocumentEvent documentevent) {
                            MainFrame.this.updateFromTextArea();
                        }

                        public void removeUpdate(
                                final DocumentEvent documentevent) {
                            MainFrame.this.updateFromTextArea();
                        }
                    });
            this.textArea.setBackground(Color.WHITE);
        }
        return this.textArea;
    }

    private void updateFromTextArea() {
        try {
            this.getMathComponent().setContent(this.getTextArea().getText());
            this.textArea.setForeground(Color.BLACK);
            // CHECKSTYLE:OFF
            // in this case, we want to explicitly provide catch-all error
            // handling.
        } catch (final RuntimeException e) {
            // CHECKSTYLE:ON
            this.textArea.setForeground(Color.RED);
        }
    }

    /**
     * This method initializes scrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getScrollPane() {
        if (this.scrollPane == null) {
            this.scrollPane = new JScrollPane();
            this.scrollPane.setViewportView(this.getMathComponent());

            if (MathViewer.OSX) {
                this.scrollPane
                        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                this.scrollPane
                        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            }
        }
        return this.scrollPane;
    }

    /**
     * This method initializes scrollPane2
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getScrollPane2() {
        if (this.scrollPane2 == null) {
            this.scrollPane2 = new JScrollPane();
            this.scrollPane2.setViewportView(this.getTextArea());

            if (MathViewer.OSX) {
                this.scrollPane2
                        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                this.scrollPane2
                        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            }
        }
        return this.scrollPane2;
    }

    /**
     * This method initializes mathComponent.
     * 
     * @return net.sourceforge.jeuclid.swing.JMathComponent
     */
    public JMathComponent getMathComponent() {
        if (this.mathComponent == null) {
            this.mathComponent = new JMathComponent();
            this.mathComponent
                    .setContent("<math><mtext>" //$NON-NLS-1$
                            + Messages.getString("MathViewer.noFileLoaded") + "</mtext></math>"); //$NON-NLS-1$ //$NON-NLS-2$
            this.mathComponent.setFocusable(true);
        }
        return this.mathComponent;
    }

    /**
     * This method initializes viewMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getViewMenu() {
        if (this.viewMenu == null) {
            this.viewMenu = new JMenu();
            this.viewMenu.setText(Messages.getString("MathViewer.viewMenu")); //$NON-NLS-1$
            this.viewMenu.add(this.getBiggerMenuItem());
            this.viewMenu.add(this.getSmallerMenuItem());
            this.viewMenu.add(this.getAliasMenuItem());
            this.viewMenu.add(this.getDebugMenuItem());
            if (!MathViewer.OSX) {
                this.viewMenu.add(this.getViewModifyParams());
            }
        }
        return this.viewMenu;
    }

    private JMenuItem getViewModifyParams() {
        final JMenuItem mi = new JMenuItem(Messages
                .getString("MathViewer.viewModifyParams"));
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                MainFrame.this.displaySettings();
            }
        });
        return mi;
    }

    /**
     * Display the settings dialog.
     */
    public void displaySettings() {
        new ParametersDialog(MainFrame.this).setVisible(true);
        MainFrame.this.debugMenuItem
                .setSelected(((Boolean) MainFrame.this.mathComponent
                        .getParameters().getParameter(Parameter.DEBUG))
                        .booleanValue());
        MainFrame.this.aliasMenuItem
                .setSelected(((Boolean) MainFrame.this.mathComponent
                        .getParameters().getParameter(Parameter.ANTIALIAS))
                        .booleanValue());

    }

    /**
     * This method initializes biggerMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getBiggerMenuItem() {
        if (this.biggerMenuItem == null) {
            this.biggerMenuItem = new JMenuItem();
            this.biggerMenuItem.setText(Messages
                    .getString("MathViewer.textBigger")); //$NON-NLS-1$
            this.biggerMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_ADD, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));
            this.biggerMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            final JMathComponent jmc = MainFrame.this
                                    .getMathComponent();
                            jmc.setFontSize(jmc.getFontSize()
                                    * MainFrame.FONT_SIZE_MULTIPLICATOR);
                        }
                    });
        }
        return this.biggerMenuItem;
    }

    /**
     * This method initializes smallerMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getSmallerMenuItem() {
        if (this.smallerMenuItem == null) {
            this.smallerMenuItem = new JMenuItem();
            this.smallerMenuItem.setText(Messages
                    .getString("MathViewer.textSmaller")); //$NON-NLS-1$
            this.smallerMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_SUBTRACT, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));

            this.smallerMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            final JMathComponent jmc = MainFrame.this
                                    .getMathComponent();
                            jmc.setFontSize(jmc.getFontSize()
                                    / MainFrame.FONT_SIZE_MULTIPLICATOR);
                        }
                    });
        }
        return this.smallerMenuItem;
    }

    /**
     * This method initializes exportMenuItem.
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getExportMenuItem() {
        if (this.exportMenuItem == null) {
            this.exportMenuItem = new JMenuItem();
            this.exportMenuItem.setText(Messages
                    .getString("MathViewer.export")); //$NON-NLS-1$
            this.exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_S, Toolkit.getDefaultToolkit()
                            .getMenuShortcutKeyMask(), true));
            this.exportMenuItem
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(
                                final java.awt.event.ActionEvent e) {
                            MainFrame.this.exportFile();
                        }
                    });
        }
        return this.exportMenuItem;
    }

    /**
     * Carries out the actual export File operation.
     */
    protected void exportFile() {
        MainFrame.FILEIO.saveDocument(this, this.getMathComponent()
                .getDocument(), this.getMathComponent().getParameters());
    }

    /**
     * This method initializes aliasMenuItem
     * 
     * @return javax.swing.JCheckBoxMenuItem
     */
    private JCheckBoxMenuItem getAliasMenuItem() {
        if (this.aliasMenuItem == null) {
            this.aliasMenuItem = new JCheckBoxMenuItem();
            this.aliasMenuItem
                    .setText(Messages.getString("MathViewer.alias")); //$NON-NLS-1$
            this.aliasMenuItem.setSelected((Boolean) LayoutContextImpl
                    .getDefaultLayoutContext().getParameter(
                            Parameter.ANTIALIAS));
            this.aliasMenuItem
                    .addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(
                                final java.awt.event.ItemEvent e) {
                            MainFrame.this.getMathComponent()
                                    .setParameter(
                                            Parameter.ANTIALIAS,
                                            MainFrame.this.aliasMenuItem
                                                    .isSelected());
                        }
                    });
        }
        return this.aliasMenuItem;
    }

    /**
     * This method initializes debugMenuItem
     * 
     * @return javax.swing.JCheckBoxMenuItem
     */
    private JCheckBoxMenuItem getDebugMenuItem() {
        if (this.debugMenuItem == null) {
            this.debugMenuItem = new JCheckBoxMenuItem();
            this.debugMenuItem
                    .setText(Messages.getString("MathViewer.debug")); //$NON-NLS-1$
            this.debugMenuItem.setSelected((Boolean) LayoutContextImpl
                    .getDefaultLayoutContext().getParameter(Parameter.DEBUG));
            this.debugMenuItem
                    .addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(
                                final java.awt.event.ItemEvent e) {
                            MainFrame.this.getMathComponent()
                                    .setParameter(
                                            Parameter.DEBUG,
                                            MainFrame.this.debugMenuItem
                                                    .isSelected());
                        }
                    });
        }
        return this.debugMenuItem;
    }

    private void pasteFromClipboard() {
        final Transferable content = Toolkit.getDefaultToolkit()
                .getSystemClipboard().getContents(null);
        if (content != null
                && content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                final String newContent = (String) content
                        .getTransferData(DataFlavor.stringFlavor);
                this.getMathComponent().setContent(newContent);
                this.getTextArea().setText(newContent);
                // CHECKSTYLE:OFF
                // in this case, we want to explicitly provide catch-all error
                // handling.
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                JOptionPane.showMessageDialog(this, new String[] {
                        Messages.getString("MathViewer.pasteFailure"),
                        e.toString(), }, Messages
                        .getString("MathViewer.error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void copyToClipboard(final boolean formatted) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(MathMLSerializer.serializeDocument(
                        this.mathComponent.getDocument(), false, formatted)),
                null);
    }

}

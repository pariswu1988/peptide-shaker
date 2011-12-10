package eu.isas.peptideshaker.gui;

import com.compomics.util.gui.renderers.AlignedListCellRenderer;
import eu.isas.peptideshaker.filtering.PeptideFilter;
import eu.isas.peptideshaker.filtering.ProteinFilter;
import eu.isas.peptideshaker.filtering.PsmFilter;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Marc Vaudel
 */
public class CreateFilterDialog extends javax.swing.JDialog {

    private PeptideShakerGUI peptideShakerGUI;
    private ProteinFilter proteinFilter;
    private PeptideFilter peptideFilter;
    private PsmFilter psmFilter;

    /**
     * Create a new create filter dailog.
     * 
     * @param peptideShakerGUI
     * @param proteinFilter
     * @param peptideFilter
     * @param psmFilter 
     */
    public CreateFilterDialog(PeptideShakerGUI peptideShakerGUI, ProteinFilter proteinFilter, PeptideFilter peptideFilter, PsmFilter psmFilter) {
        super(peptideShakerGUI, true);
        this.peptideShakerGUI = peptideShakerGUI;
        initComponents();
        this.proteinFilter = proteinFilter;
        this.peptideFilter = peptideFilter;
        this.psmFilter = psmFilter;

        proteinEffectCmb.setRenderer(new AlignedListCellRenderer(SwingConstants.CENTER));
        peptideEffectCmb.setRenderer(new AlignedListCellRenderer(SwingConstants.CENTER));
        psmEffectCmb.setRenderer(new AlignedListCellRenderer(SwingConstants.CENTER));
                
        if (proteinFilter != null) {
            proteinSplitPane.setDividerLocation(1.0);
            this.setTitle("Protein Filter");
        } else if (peptideFilter != null) {
            proteinSplitPane.setDividerLocation(0);
            peptideAndPsmsSplitPane.setDividerLocation(0);
            this.setTitle("Peptide Filter");
        } else if (psmFilter != null) {
            proteinSplitPane.setDividerLocation(0);
            peptideAndPsmsSplitPane.setDividerLocation(1.0);
            this.setTitle("PSM Filter");
        } else {
            // an error occured, this shoud not happen....
        }
        
        setLocationRelativeTo(peptideShakerGUI);
        setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        proteinSplitPane = new javax.swing.JSplitPane();
        peptideAndPsmsSplitPane = new javax.swing.JSplitPane();
        peptideFilterPane = new javax.swing.JPanel();
        peptideEffectCmb = new javax.swing.JComboBox();
        peptideFilterTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        peptideFilterDescription = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        psmFilterPane = new javax.swing.JPanel();
        psmEffectCmb = new javax.swing.JComboBox();
        psmFilterTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        psmFilterDescription = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        proteinFilterPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        proteinFilterTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        proteinEffectCmb = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        proteinFilterDescription = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        proteinSplitPane.setBorder(null);
        proteinSplitPane.setDividerLocation(0);
        proteinSplitPane.setDividerSize(-1);
        proteinSplitPane.setMinimumSize(new java.awt.Dimension(0, 0));
        proteinSplitPane.setPreferredSize(new java.awt.Dimension(99, 83));

        peptideAndPsmsSplitPane.setBorder(null);
        peptideAndPsmsSplitPane.setDividerLocation(0);
        peptideAndPsmsSplitPane.setDividerSize(-1);

        peptideEffectCmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Star", "Hide" }));

        peptideFilterTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        peptideFilterTxt.setText("New filter");

        jLabel3.setText("Filter Name:");

        jLabel4.setText("Effect:");

        peptideFilterDescription.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText("Description:");

        javax.swing.GroupLayout peptideFilterPaneLayout = new javax.swing.GroupLayout(peptideFilterPane);
        peptideFilterPane.setLayout(peptideFilterPaneLayout);
        peptideFilterPaneLayout.setHorizontalGroup(
            peptideFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peptideFilterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peptideFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(peptideFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(peptideEffectCmb, javax.swing.GroupLayout.Alignment.TRAILING, 0, 296, Short.MAX_VALUE)
                    .addComponent(peptideFilterDescription, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                    .addComponent(peptideFilterTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                .addContainerGap())
        );
        peptideFilterPaneLayout.setVerticalGroup(
            peptideFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peptideFilterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peptideFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(peptideFilterTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(peptideFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(peptideFilterDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(peptideFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(peptideEffectCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(14, 14, 14))
        );

        peptideAndPsmsSplitPane.setRightComponent(peptideFilterPane);

        psmEffectCmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Star", "Hide" }));

        psmFilterTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        psmFilterTxt.setText("New filter");

        jLabel5.setText("Filter Name:");

        jLabel6.setText("Effect:");

        psmFilterDescription.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setText("Description:");

        javax.swing.GroupLayout psmFilterPaneLayout = new javax.swing.GroupLayout(psmFilterPane);
        psmFilterPane.setLayout(psmFilterPaneLayout);
        psmFilterPaneLayout.setHorizontalGroup(
            psmFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psmFilterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(psmFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(psmFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(psmEffectCmb, javax.swing.GroupLayout.Alignment.TRAILING, 0, 393, Short.MAX_VALUE)
                    .addComponent(psmFilterTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                    .addComponent(psmFilterDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
                .addContainerGap())
        );
        psmFilterPaneLayout.setVerticalGroup(
            psmFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psmFilterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(psmFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(psmFilterDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(psmFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(psmFilterTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(psmFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(psmEffectCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(14, 14, 14))
        );

        peptideAndPsmsSplitPane.setTopComponent(psmFilterPane);

        proteinSplitPane.setBottomComponent(peptideAndPsmsSplitPane);

        jLabel1.setText("Filter Name:");

        proteinFilterTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proteinFilterTxt.setText("New filter");

        jLabel2.setText("Effect:");

        proteinEffectCmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Star", "Hide" }));

        jLabel7.setText("Description:");

        proteinFilterDescription.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout proteinFilterPaneLayout = new javax.swing.GroupLayout(proteinFilterPane);
        proteinFilterPane.setLayout(proteinFilterPaneLayout);
        proteinFilterPaneLayout.setHorizontalGroup(
            proteinFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinFilterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proteinFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addGap(13, 13, 13)
                .addGroup(proteinFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(proteinEffectCmb, 0, 241, Short.MAX_VALUE)
                    .addComponent(proteinFilterDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                    .addComponent(proteinFilterTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
                .addContainerGap())
        );
        proteinFilterPaneLayout.setVerticalGroup(
            proteinFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinFilterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proteinFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proteinFilterTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proteinFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proteinFilterDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proteinFilterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proteinEffectCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        proteinSplitPane.setTopComponent(proteinFilterPane);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(244, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
            .addComponent(proteinSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(proteinSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (proteinFilter != null) {
            String name = proteinFilterTxt.getText().trim();
            int outcome = JOptionPane.YES_OPTION;
            if (peptideShakerGUI.getFilterPreferences().filterExists(name)) {
                outcome = JOptionPane.showConfirmDialog(this,
                        "Should protein filter " + name + " be overwritten?", "Selected Filter Already Exists",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            }
            if (outcome != JOptionPane.YES_OPTION) {
                return;
            }
            proteinFilter.setName(name);
            proteinFilter.setDescription(proteinFilterDescription.getText().trim());
            if (proteinEffectCmb.getSelectedIndex() == 0) {
                peptideShakerGUI.getFilterPreferences().addStarringFilter(proteinFilter);
            } else {
                peptideShakerGUI.getFilterPreferences().addHidingFilter(proteinFilter);
            }
        }
        if (peptideFilter != null) {
            String name = peptideFilterTxt.getText().trim();
            int outcome = JOptionPane.YES_OPTION;
            if (peptideShakerGUI.getFilterPreferences().filterExists(name)) {
                outcome = JOptionPane.showConfirmDialog(this,
                        "Should peptide filter " + name + " be overwritten?", "Selected Filter Already Exists",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            }
            if (outcome != JOptionPane.YES_OPTION) {
                return;
            }
            peptideFilter.setName(name);
            peptideFilter.setDescription(peptideFilterDescription.getText().trim());
            if (peptideEffectCmb.getSelectedIndex() == 0) {
                peptideShakerGUI.getFilterPreferences().addStarringFilter(peptideFilter);
            } else {
                peptideShakerGUI.getFilterPreferences().addHidingFilter(peptideFilter);
            }
        }
        if (psmFilter != null) {
            String name = psmFilterTxt.getText().trim();
            int outcome = JOptionPane.YES_OPTION;
            if (peptideShakerGUI.getFilterPreferences().filterExists(name)) {
                outcome = JOptionPane.showConfirmDialog(this,
                        "Should PSM filter " + name + " be overwritten?", "Selected Filter Already Exists",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            }
            if (outcome != JOptionPane.YES_OPTION) {
                return;
            }
            psmFilter.setName(name);
            psmFilter.setDescription(psmFilterDescription.getText().trim());
            if (psmEffectCmb.getSelectedIndex() == 0) {
                peptideShakerGUI.getFilterPreferences().addStarringFilter(psmFilter);
            } else {
                peptideShakerGUI.getFilterPreferences().addHidingFilter(psmFilter);
            }
        }
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton okButton;
    private javax.swing.JSplitPane peptideAndPsmsSplitPane;
    private javax.swing.JComboBox peptideEffectCmb;
    private javax.swing.JTextField peptideFilterDescription;
    private javax.swing.JPanel peptideFilterPane;
    private javax.swing.JTextField peptideFilterTxt;
    private javax.swing.JComboBox proteinEffectCmb;
    private javax.swing.JTextField proteinFilterDescription;
    private javax.swing.JPanel proteinFilterPane;
    private javax.swing.JTextField proteinFilterTxt;
    private javax.swing.JSplitPane proteinSplitPane;
    private javax.swing.JComboBox psmEffectCmb;
    private javax.swing.JTextField psmFilterDescription;
    private javax.swing.JPanel psmFilterPane;
    private javax.swing.JTextField psmFilterTxt;
    // End of variables declaration//GEN-END:variables
}
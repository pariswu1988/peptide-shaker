
package eu.isas.peptideshaker.gui;

import com.compomics.util.Util;
import com.compomics.util.experiment.biology.Protein;
import com.compomics.util.experiment.identification.Identification;
import com.compomics.util.experiment.identification.SequenceDataBase;
import com.compomics.util.experiment.identification.matches.ProteinMatch;
import com.compomics.util.gui.renderers.AlignedListCellRenderer;
import eu.isas.peptideshaker.myparameters.PSParameter;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import no.uib.jsparklines.extra.NimbusCheckBoxRenderer;

/**
 * This dialog allows the user to resolve manually some protein inference issues
 *
 * @author Marc Vaudel
 * @author Harald Barsnes
 */
public class ProteinInferenceDialog extends javax.swing.JDialog {

    /**
     * The inspected protein match
     */
    private ProteinMatch inspectedMatch;
    /**
     * The protein accessions
     */
    private ArrayList<String> accessions;
    /**
     * The detected unique matches (if any)
     */
    private ArrayList<ProteinMatch> uniqueMatches = new ArrayList<ProteinMatch>();
    /**
     * Associated matches presenting the same proteins or a share.
     */
    private ArrayList<ProteinMatch> associatedMatches = new ArrayList<ProteinMatch>();
    /**
     * The sequence database
     */
    private SequenceDataBase db;
    /**
     * The protein table.
     */
    private JTable proteinTable;
    /**
     * The selected row.
     */
    private int selectedRow;
    /**
     * The PeptideShaker parent frame.
     */
    private PeptideShakerGUI peptideShakerGUI;

    /** 
     * Creates new form ProteinInferenceDialog
     * 
     * @param peptideShakerGUI
     * @param proteinTable 
     * @param selectedRow 
     * @param inspectedMatch 
     * @param identification
     * @param db  
     */
    public ProteinInferenceDialog(PeptideShakerGUI peptideShakerGUI, JTable proteinTable, int selectedRow, ProteinMatch inspectedMatch, Identification identification, SequenceDataBase db) {
        super(peptideShakerGUI, true);

        this.peptideShakerGUI = peptideShakerGUI;
        this.proteinTable = proteinTable;
        this.selectedRow = selectedRow;
        this.db = db;
        this.inspectedMatch = inspectedMatch;
        accessions = new ArrayList(inspectedMatch.getTheoreticProteinsAccessions());
        for (String proteinAccession : inspectedMatch.getTheoreticProteinsAccessions()) {
            ProteinMatch uniqueProteinMatch = identification.getProteinIdentification().get(inspectedMatch.getTheoreticProtein(proteinAccession).getProteinKey());
            if (uniqueProteinMatch != null) {
                uniqueMatches.add(uniqueProteinMatch);
            }
        }
        Protein singleProtein;
        for (ProteinMatch proteinMatch : identification.getProteinIdentification().values()) {
            if (proteinMatch.getNProteins() > 1 && !associatedMatches.contains(proteinMatch) && !proteinMatch.getKey().equals(inspectedMatch.getKey())) {
                for (String proteinAccession : inspectedMatch.getTheoreticProteinsAccessions()) {
                    singleProtein = inspectedMatch.getTheoreticProtein(proteinAccession);
                    if (proteinMatch.contains(singleProtein)) {
                        associatedMatches.add(proteinMatch);
                    }
                }
            }
        }

        initComponents();
        
        groupClassJComboBox.setRenderer(new AlignedListCellRenderer(SwingConstants.CENTER));

        PSParameter psParameter = (PSParameter) inspectedMatch.getUrParam(new PSParameter());
        matchInfoLbl.setText("Score: "+ Util.roundDouble(psParameter.getProteinScore(), 2)
                + " Confidence: " + Util.roundDouble(psParameter.getProteinConfidence(), 2));

        // set up the table column properties
        setColumnProperies();

        // The index should be set in the design according to the PSParameter class static fields!
        groupClassJComboBox.setSelectedIndex(psParameter.getGroupClass());

        setLocationRelativeTo(peptideShakerGUI);
        setVisible(true);
    }

     /**
     * Set the properties for the columns in the results tables.
     */
    private void setColumnProperies() {

        proteinMatchTable.getTableHeader().setReorderingAllowed(false);
        uniqueHitsTable.getTableHeader().setReorderingAllowed(false);
        relatedHitsTable.getTableHeader().setReorderingAllowed(false);

        proteinMatchTable.getColumn("Main Match").setMinWidth(80);
        proteinMatchTable.getColumn("Main Match").setMaxWidth(80);

        // change the cell renderer to fix a problem in Nimbus and alternating row colors
        proteinMatchTable.getColumn("Main Match").setCellRenderer(new NimbusCheckBoxRenderer());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        relatedHitsTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        uniqueHitsTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        proteinMatchTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        matchInfoLbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        groupClassJComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Unresolved Protein Inference");
        setResizable(false);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Related Hits"));

        relatedHitsTable.setModel(new AssociatedMatches());
        jScrollPane3.setViewportView(relatedHitsTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Unique Hits"));

        uniqueHitsTable.setModel(new UniqueMatches());
        jScrollPane2.setViewportView(uniqueHitsTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Candidate Proteins"));

        proteinMatchTable.setModel(new MatchTable());
        proteinMatchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proteinMatchTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(proteinMatchTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Protein Group Details"));

        jLabel1.setText("Group Score:");

        matchInfoLbl.setText("protein match information");

        jLabel2.setText("Group Class:");

        groupClassJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single Protein", "Group of Isoforms", "Group of Unrelated Isoforms", "Group of Unrelated Proteins" }));
        groupClassJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupClassJComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupClassJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matchInfoLbl))
                .addContainerGap(389, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(matchInfoLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(groupClassJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(okButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel3, jPanel4});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Update the protein table according to the protein inference selection.
     * 
     * @param evt 
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        
        Protein mainMatch = inspectedMatch.getMainMatch();
        proteinTable.setValueAt(peptideShakerGUI.addDatabaseLink(mainMatch.getAccession()), selectedRow, proteinTable.getColumn("Accession").getModelIndex());
        proteinTable.setValueAt(groupClassJComboBox.getSelectedIndex(), selectedRow, proteinTable.getColumn("PI").getModelIndex());
        String description = db.getProteinHeader(mainMatch.getAccession()).getDescription();
        proteinTable.setValueAt(description, selectedRow, proteinTable.getColumn("Description").getModelIndex());
        
        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void proteinMatchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinMatchTableMouseClicked
        int row = proteinMatchTable.rowAtPoint(evt.getPoint());
        inspectedMatch.setMainMatch(inspectedMatch.getTheoreticProtein(accessions.get(row)));
        proteinMatchTable.revalidate();
        proteinMatchTable.repaint();
    }//GEN-LAST:event_proteinMatchTableMouseClicked

    private void groupClassJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupClassJComboBoxActionPerformed
        PSParameter pSParameter = new PSParameter();
        pSParameter = (PSParameter) inspectedMatch.getUrParam(pSParameter);
        pSParameter.setGroupClass(groupClassJComboBox.getSelectedIndex());
    }//GEN-LAST:event_groupClassJComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox groupClassJComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel matchInfoLbl;
    private javax.swing.JButton okButton;
    private javax.swing.JTable proteinMatchTable;
    private javax.swing.JTable relatedHitsTable;
    private javax.swing.JTable uniqueHitsTable;
    // End of variables declaration//GEN-END:variables

    /**
     * Table model for the protein match table
     */
    private class MatchTable extends DefaultTableModel {

        @Override
        public int getRowCount() {
            return inspectedMatch.getNProteins();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0: return "Main Match";
                case 1: return "Accession";
                case 2: return "Description";
                default: return " ";
            }
        }

        @Override
        public Object getValueAt(int row, int column) {
            switch(column) {
                case 0: return inspectedMatch.getMainMatch().getAccession().equals(accessions.get(row));
                case 1: return accessions.get(row);
                case 2:
                    if (db != null) {
                            return db.getProteinHeader(inspectedMatch.getTheoreticProtein(accessions.get(row)).getProteinKey()).getDescription();
                    } else {
                        return "Database not loaded";
                    }
                default: return " ";
            }
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 0;
        }
    }

    /**
     * Table model for the unique matches table
     */
    private class UniqueMatches extends DefaultTableModel {

        @Override
        public int getRowCount() {
            return uniqueMatches.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0: return "Accession";
                case 1: return "Score";
                case 2: return "Confidence";
                case 3: return "Validated";
                default: return "";
            }
        }

        @Override
        public Object getValueAt(int row, int column) {
            ProteinMatch currentMatch = uniqueMatches.get(row);
            PSParameter pSParameter = (PSParameter) currentMatch.getUrParam(new PSParameter());
            switch(column) {
                case 0: return currentMatch.getKey();
                case 1: return pSParameter.getProteinScore();
                case 2: return pSParameter.getProteinConfidence();
                case 3: return pSParameter.isValidated();
                default: return "";
            }

        }

        @Override
        public Class getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }
    }

    /**
     * Table model for the associated matches table
     */
    private class AssociatedMatches extends DefaultTableModel {

        @Override
        public int getRowCount() {
            return associatedMatches.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0: return "Accession";
                case 1: return "Score";
                case 2: return "Confidence";
                case 3: return "Validated";
                default: return "";
            }
        }

        @Override
        public Object getValueAt(int row, int column) {
            ProteinMatch currentMatch = associatedMatches.get(row);
            PSParameter pSParameter = (PSParameter) currentMatch.getUrParam(new PSParameter());
            switch(column) {
                case 0: return currentMatch.getKey();
                case 1: return pSParameter.getProteinScore();
                case 2: return pSParameter.getProteinConfidence();
                case 3: return pSParameter.isValidated();
                default: return "";
            }
            
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }
    }
}

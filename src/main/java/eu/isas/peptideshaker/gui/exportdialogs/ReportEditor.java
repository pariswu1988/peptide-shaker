package eu.isas.peptideshaker.gui.exportdialogs;

import eu.isas.peptideshaker.export.ExportFactory;
import eu.isas.peptideshaker.export.ExportFeature;
import eu.isas.peptideshaker.export.ExportScheme;
import eu.isas.peptideshaker.export.exportfeatures.AnnotationFeatures;
import eu.isas.peptideshaker.export.exportfeatures.InputFilterFeatures;
import eu.isas.peptideshaker.export.exportfeatures.PeptideFeatures;
import eu.isas.peptideshaker.export.exportfeatures.ProjectFeatures;
import eu.isas.peptideshaker.export.exportfeatures.ProteinFeatures;
import eu.isas.peptideshaker.export.exportfeatures.PsmFeatures;
import eu.isas.peptideshaker.export.exportfeatures.PtmScoringFeatures;
import eu.isas.peptideshaker.export.exportfeatures.SearchFeatures;
import eu.isas.peptideshaker.export.exportfeatures.SpectrumCountingFeatures;
import eu.isas.peptideshaker.export.exportfeatures.ValidationFeatures;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import no.uib.jsparklines.extra.NimbusCheckBoxRenderer;

/**
 * Dialog for editing reports.
 *
 * @author Marc Vaudel
 * @author Harald Barsnes
 */
public class ReportEditor extends javax.swing.JDialog {

    /**
     * The export factory.
     */
    private ExportFactory exportFactory = ExportFactory.getInstance();
    /**
     * The original name of the report.
     */
    private String oldName;
    /**
     * A boolean indicating whether the report can be edited.
     */
    private boolean editable = true;
    /**
     * The original selection.
     */
    private HashMap<String, ArrayList<ExportFeature>> selection = new HashMap<String, ArrayList<ExportFeature>>();
    /**
     * The selected section name.
     */
    private String sectionName = null;
    /**
     * The list of implemented features for the selected section.
     */
    private ArrayList<ExportFeature> featuresList;

    /**
     * Constructor.
     *
     * @param parent the parent frame
     * @param exportSchemeName the name of the export scheme to edit
     * @param editable
     */
    public ReportEditor(java.awt.Frame parent, String exportSchemeName, boolean editable) {
        super(parent, true);
        initComponents();
        oldName = exportSchemeName;
        this.editable = editable;
        selection = new HashMap<String, ArrayList<ExportFeature>>();
        ExportScheme exportScheme = exportFactory.getExportScheme(exportSchemeName);
        for (String section : exportScheme.getSections()) {
            if (!selection.containsKey(section)) {
                selection.put(section, new ArrayList<ExportFeature>());
            }
            selection.get(section).addAll(exportScheme.getExportFeatures(section));
        }
        setUpGUI();
        this.setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * Constructor.
     *
     * @param parent the parent frame
     */
    public ReportEditor(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        setUpGUI();
        this.setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * Sets up the GUI components.
     */
    private void setUpGUI() {

        sectionsTable.getTableHeader().setReorderingAllowed(false);
        featuresTable.getTableHeader().setReorderingAllowed(false);

        sectionsTable.getColumn(" ").setMaxWidth(30);
        sectionsTable.getColumn(" ").setMinWidth(30);

        featuresTable.getColumn(" ").setMaxWidth(30);
        featuresTable.getColumn(" ").setMinWidth(30);
        featuresTable.getColumn("  ").setMaxWidth(30);
        featuresTable.getColumn("  ").setMinWidth(30);

        featuresTable.getColumn("  ").setCellRenderer(new NimbusCheckBoxRenderer());

        nameTxt.setEnabled(editable);
        maintTitleCheckBox.setEnabled(editable);
        separatorTxt.setEnabled(editable);
        sectionTitleCheckBox.setEnabled(editable);
        separationLinesSpinner.setEnabled(editable);
        sectionsTable.setEnabled(editable);
        featuresTable.setEnabled(editable);
        lineNumberCheckBox.setEnabled(editable);
        headerCheckBox.setEnabled(editable);

        // make sure that the scroll panes are see-through
        featuresScrollPane.getViewport().setOpaque(false);
        sectionsScrollPane.getViewport().setOpaque(false);

        if (oldName != null) {
            nameTxt.setText(oldName);

            ExportScheme exportScheme = exportFactory.getExportScheme(oldName);
            if (exportScheme.getMainTitle() != null) {
                maintTitleCheckBox.setSelected(true);
                mainTitleTxt.setText(exportScheme.getMainTitle());
            }
            separatorTxt.setText(exportScheme.getSeparator());
            sectionTitleCheckBox.setSelected(exportScheme.isIncludeSectionTitles());
            separationLinesSpinner.setValue(exportScheme.getSeparationLines());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        reporterTypePanel = new javax.swing.JPanel();
        nameTxt = new javax.swing.JTextField();
        reporterSettingsPanel = new javax.swing.JPanel();
        mainTitleTxt = new javax.swing.JTextField();
        maintTitleCheckBox = new javax.swing.JCheckBox();
        sectionTitleCheckBox = new javax.swing.JCheckBox();
        separationLinesSpinner = new javax.swing.JSpinner();
        numberOfSeparationLinesLabel = new javax.swing.JLabel();
        separatorTxt = new javax.swing.JTextField();
        columnSeparatorLabel = new javax.swing.JLabel();
        lineNumberCheckBox = new javax.swing.JCheckBox();
        headerCheckBox = new javax.swing.JCheckBox();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        sectionsPanel = new javax.swing.JPanel();
        sectionsScrollPane = new javax.swing.JScrollPane();
        sectionsTable = new javax.swing.JTable();
        sectionContentPanel = new javax.swing.JPanel();
        featuresScrollPane = new javax.swing.JScrollPane();
        featuresTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Report");

        backgroundPanel.setBackground(new java.awt.Color(230, 230, 230));

        reporterTypePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Report Type"));
        reporterTypePanel.setOpaque(false);

        javax.swing.GroupLayout reporterTypePanelLayout = new javax.swing.GroupLayout(reporterTypePanel);
        reporterTypePanel.setLayout(reporterTypePanelLayout);
        reporterTypePanelLayout.setHorizontalGroup(
            reporterTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reporterTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameTxt)
                .addContainerGap())
        );
        reporterTypePanelLayout.setVerticalGroup(
            reporterTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reporterTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        reporterSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));
        reporterSettingsPanel.setOpaque(false);

        mainTitleTxt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mainTitleTxt.setEnabled(false);

        maintTitleCheckBox.setText("Include Title");
        maintTitleCheckBox.setIconTextGap(15);
        maintTitleCheckBox.setOpaque(false);
        maintTitleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maintTitleCheckBoxActionPerformed(evt);
            }
        });

        sectionTitleCheckBox.setText("Include Section Titles");
        sectionTitleCheckBox.setIconTextGap(15);
        sectionTitleCheckBox.setOpaque(false);

        separationLinesSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        separationLinesSpinner.setEnabled(false);

        numberOfSeparationLinesLabel.setText("# Separation Lines");

        separatorTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        separatorTxt.setText("\\t");

        columnSeparatorLabel.setText("Column Separator");

        lineNumberCheckBox.setText("Include Line Numbers");
        lineNumberCheckBox.setIconTextGap(15);
        lineNumberCheckBox.setOpaque(false);

        headerCheckBox.setText("Include Header");
        headerCheckBox.setIconTextGap(15);
        headerCheckBox.setOpaque(false);

        javax.swing.GroupLayout reporterSettingsPanelLayout = new javax.swing.GroupLayout(reporterSettingsPanel);
        reporterSettingsPanel.setLayout(reporterSettingsPanelLayout);
        reporterSettingsPanelLayout.setHorizontalGroup(
            reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                        .addComponent(maintTitleCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mainTitleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sectionTitleCheckBox))
                .addGap(40, 40, 40)
                .addGroup(reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lineNumberCheckBox)
                    .addComponent(headerCheckBox))
                .addGap(40, 40, 40)
                .addGroup(reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                        .addComponent(columnSeparatorLabel)
                        .addGap(14, 14, 14)
                        .addComponent(separatorTxt))
                    .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                        .addComponent(numberOfSeparationLinesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(separationLinesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        reporterSettingsPanelLayout.setVerticalGroup(
            reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                        .addGroup(reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mainTitleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maintTitleCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sectionTitleCheckBox))
                    .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                        .addComponent(lineNumberCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(headerCheckBox))
                    .addGroup(reporterSettingsPanelLayout.createSequentialGroup()
                        .addGroup(reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(separatorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(columnSeparatorLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(reporterSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(separationLinesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberOfSeparationLinesLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reporterSettingsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {columnSeparatorLabel, headerCheckBox, lineNumberCheckBox, mainTitleTxt, maintTitleCheckBox, numberOfSeparationLinesLabel, sectionTitleCheckBox, separationLinesSpinner, separatorTxt});

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        sectionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Sections"));
        sectionsPanel.setOpaque(false);

        sectionsTable.setModel(new SectionsTableModel());
        sectionsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sectionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sectionsTableMouseReleased(evt);
            }
        });
        sectionsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sectionsTableKeyReleased(evt);
            }
        });
        sectionsScrollPane.setViewportView(sectionsTable);

        javax.swing.GroupLayout sectionsPanelLayout = new javax.swing.GroupLayout(sectionsPanel);
        sectionsPanel.setLayout(sectionsPanelLayout);
        sectionsPanelLayout.setHorizontalGroup(
            sectionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sectionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sectionsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                .addContainerGap())
        );
        sectionsPanelLayout.setVerticalGroup(
            sectionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sectionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sectionsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addContainerGap())
        );

        sectionContentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Section Content"));
        sectionContentPanel.setOpaque(false);

        featuresTable.setModel(new FeaturesTableModel());
        featuresTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        featuresScrollPane.setViewportView(featuresTable);

        javax.swing.GroupLayout sectionContentPanelLayout = new javax.swing.GroupLayout(sectionContentPanel);
        sectionContentPanel.setLayout(sectionContentPanelLayout);
        sectionContentPanelLayout.setHorizontalGroup(
            sectionContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sectionContentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(featuresScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                .addContainerGap())
        );
        sectionContentPanelLayout.setVerticalGroup(
            sectionContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sectionContentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(featuresScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reporterSettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(reporterTypePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sectionContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sectionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reporterTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(reporterSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sectionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sectionContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Close the dialog without saving.
     *
     * @param evt
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Save the export scheme and close the dialog.
     *
     * @param evt
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (editable) {
            String newName = nameTxt.getText().trim();

            if (newName.trim().length() == 0) {
                JOptionPane.showMessageDialog(this, "Please provide a name for the report type.", "Report Type Missing", JOptionPane.INFORMATION_MESSAGE);
                nameTxt.requestFocus();
                return;
            }

            if (oldName != null && !oldName.contains(newName)) {
                exportFactory.removeExportScheme(oldName);
            }
            ArrayList<ExportFeature> features = new ArrayList<ExportFeature>();
            for (ArrayList<ExportFeature> exportFeatures : selection.values()) {
                features.addAll(exportFeatures);
            }
            if (maintTitleCheckBox.isSelected()) {
                ExportScheme exportScheme = new ExportScheme(newName, true, features, separatorTxt.getText(),
                        lineNumberCheckBox.isSelected(), headerCheckBox.isSelected(), (Integer) separationLinesSpinner.getValue(),
                        sectionTitleCheckBox.isSelected(), mainTitleTxt.getText().trim());
                exportFactory.addExportScheme(exportScheme);
            } else {
                ExportScheme exportScheme = new ExportScheme(newName, true, features, separatorTxt.getText(),
                        lineNumberCheckBox.isSelected(), headerCheckBox.isSelected(), (Integer) separationLinesSpinner.getValue(),
                        sectionTitleCheckBox.isSelected());
                exportFactory.addExportScheme(exportScheme);
            }
        }
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * Enable/disable the main title text field.
     *
     * @param evt
     */
    private void maintTitleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maintTitleCheckBoxActionPerformed
        mainTitleTxt.setEnabled(maintTitleCheckBox.isSelected());
    }//GEN-LAST:event_maintTitleCheckBoxActionPerformed

    /**
     * Update the section content table.
     *
     * @param evt
     */
    private void sectionsTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sectionsTableMouseReleased
        if (sectionsTable.getSelectedRow() != -1) {
            sectionName = (String) sectionsTable.getValueAt(sectionsTable.getSelectedRow(), 1);
            updateFeatureTableContent();
        }
    }//GEN-LAST:event_sectionsTableMouseReleased

    /**
     * Update the section content table.
     *
     * @param evt
     */
    private void sectionsTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sectionsTableKeyReleased
        sectionsTableMouseReleased(null);
    }//GEN-LAST:event_sectionsTableKeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel columnSeparatorLabel;
    private javax.swing.JScrollPane featuresScrollPane;
    private javax.swing.JTable featuresTable;
    private javax.swing.JCheckBox headerCheckBox;
    private javax.swing.JCheckBox lineNumberCheckBox;
    private javax.swing.JTextField mainTitleTxt;
    private javax.swing.JCheckBox maintTitleCheckBox;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JLabel numberOfSeparationLinesLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel reporterSettingsPanel;
    private javax.swing.JPanel reporterTypePanel;
    private javax.swing.JPanel sectionContentPanel;
    private javax.swing.JCheckBox sectionTitleCheckBox;
    private javax.swing.JPanel sectionsPanel;
    private javax.swing.JScrollPane sectionsScrollPane;
    private javax.swing.JTable sectionsTable;
    private javax.swing.JSpinner separationLinesSpinner;
    private javax.swing.JTextField separatorTxt;
    // End of variables declaration//GEN-END:variables

    /**
     * Indicates whether a feature has been selected in the given section.
     *
     * @param section the section of interest
     * @param exportFeature the export feature of interest
     * @return a boolean indicating whether a feature has been selected in the
     * given section
     */
    private boolean isSelected(String section, ExportFeature exportFeature) {
        ArrayList<ExportFeature> selectedSectionFeatures = selection.get(section);
        if (selectedSectionFeatures != null && selectedSectionFeatures.contains(exportFeature)) {
            return true;
        }
        return false;
    }

    /**
     * Sets whether a feature is selected in the given section.
     *
     * @param section the section of interest
     * @param exportFeature the feature of interest
     * @param selected a boolean indicating whether the feature shall be
     * selected or not
     */
    private void setSelected(String section, ExportFeature exportFeature, Boolean selected) {
        if (selected) {
            if (!selection.containsKey(section)) {
                selection.put(section, new ArrayList<ExportFeature>());
            }
            if (!selection.get(section).contains(exportFeature)) {
                selection.get(section).add(exportFeature);
            }
        } else {
            if (selection.containsKey(section)) {
                selection.get(section).remove(exportFeature);
                if (selection.get(section).isEmpty()) {
                    selection.remove(section);
                }
            }
        }
    }

    /**
     * Updates the feature table content based on the section name.
     */
    private void updateFeatureTableContent() {
        featuresList = new ArrayList<ExportFeature>();
        if (sectionName != null) {
            if (sectionName.equals(AnnotationFeatures.type)) {
                featuresList.addAll(Arrays.asList(AnnotationFeatures.values()));
            } else if (sectionName.equals(InputFilterFeatures.type)) {
                featuresList.addAll(Arrays.asList(InputFilterFeatures.values()));
            } else if (sectionName.equals(PeptideFeatures.type)) {
                featuresList.addAll(Arrays.asList(PeptideFeatures.values()));
            } else if (sectionName.equals(ProjectFeatures.type)) {
                featuresList.addAll(Arrays.asList(ProjectFeatures.values()));
            } else if (sectionName.equals(ProteinFeatures.type)) {
                featuresList.addAll(Arrays.asList(ProteinFeatures.values()));
            } else if (sectionName.equals(PsmFeatures.type)) {
                featuresList.addAll(Arrays.asList(PsmFeatures.values()));
            } else if (sectionName.equals(PtmScoringFeatures.type)) {
                featuresList.addAll(Arrays.asList(PtmScoringFeatures.values()));
            } else if (sectionName.equals(SearchFeatures.type)) {
                featuresList.addAll(Arrays.asList(SearchFeatures.values()));
            } else if (sectionName.equals(SpectrumCountingFeatures.type)) {
                featuresList.addAll(Arrays.asList(SpectrumCountingFeatures.values()));
            } else if (sectionName.equals(ValidationFeatures.type)) {
                featuresList.addAll(Arrays.asList(ValidationFeatures.values()));
            }
        }
        ((DefaultTableModel) featuresTable.getModel()).fireTableDataChanged();
    }

    /**
     * Table model for the reports table.
     */
    private class SectionsTableModel extends DefaultTableModel {

        /**
         * The list of implemented sections.
         */
        private ArrayList<String> sectionList;

        /**
         * Constructor.
         */
        public SectionsTableModel() {
            sectionList = new ArrayList<String>();
            sectionList.addAll(ExportFactory.getImplementedSections());
        }

        @Override
        public int getRowCount() {
            if (sectionList == null) {
                return 0;
            }
            return sectionList.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return " ";
                case 1:
                    return "Name";
                default:
                    return "";
            }
        }

        @Override
        public Object getValueAt(int row, int column) {
            switch (column) {
                case 0:
                    return row + 1;
                case 1:
                    return sectionList.get(row);
                default:
                    return "";
            }
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            for (int i = 0; i < getRowCount(); i++) {
                if (getValueAt(i, columnIndex) != null) {
                    return getValueAt(i, columnIndex).getClass();
                }
            }
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    /**
     * Table model for the reports table.
     */
    private class FeaturesTableModel extends DefaultTableModel {

        /**
         * Constructor
         */
        public FeaturesTableModel() {
        }

        @Override
        public int getRowCount() {
            if (featuresList == null) {
                return 0;
            }
            return featuresList.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return " ";
                case 1:
                    return "  ";
                case 2:
                    return "Name";
                case 3:
                    return "Description";
                default:
                    return "";
            }
        }

        @Override
        public Object getValueAt(int row, int column) {
            switch (column) {
                case 0:
                    return row + 1;
                case 1:
                    return isSelected(sectionName, featuresList.get(row));
                case 2:
                    return featuresList.get(row).getTitle();
                case 3:
                    return featuresList.get(row).getDescription();
                default:
                    return "";
            }
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            for (int i = 0; i < getRowCount(); i++) {
                if (getValueAt(i, columnIndex) != null) {
                    return getValueAt(i, columnIndex).getClass();
                }
            }
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            setSelected(sectionName, featuresList.get(row), (Boolean) aValue);
        }
    }
}
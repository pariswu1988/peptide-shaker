package eu.isas.peptideshaker.gui.protein_inference;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.BasicEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.apache.commons.collections15.Transformer;

/**
 * A panel to display protein inference graphs.
 *
 * @author Harald Barsnes
 */
public class ProteinInferenceGraphPanel extends javax.swing.JPanel {

    /**
     * The nodes.
     */
    private ArrayList<String> nodes;
    /**
     * The edges: the keys are the node labels and the elements the list of
     * objects.
     */
    private HashMap<String, ArrayList<String>> edges;
    /**
     * The labels of the currently selected nodes.
     */
    private ArrayList<String> selectedNodes = new ArrayList<String>();
    /**
     * The labels of the currently selected neighbor nodes.
     */
    private ArrayList<String> selectedNeighborNodes = new ArrayList<String>();
    /**
     * The labels of the currently selected edges.
     */
    private ArrayList<String> selectedEdges = new ArrayList<String>();
    /**
     * Set if if the peptide labels are to be shown.
     */
    private boolean showPeptideLabels = false;
    /**
     * Set if if the protein labels are to be shown.
     */
    private boolean showProteinLabels = false;
    /**
     * The edge properties: the keys are the edge names.
     */
    private HashMap<String, String> edgeProperties;
    /**
     * The node properties: the keys are the node names.
     */
    private HashMap<String, String> nodeProperties;
    /**
     * The node tooltips: the keys are the node names.
     */
    private HashMap<String, String> nodeToolTips;
    /**
     * The graph.
     */
    private UndirectedSparseGraph<String, String> graph;
    /**
     * The visualization viewer.
     */
    private VisualizationViewer visualizationViewer;
    /**
     * The parent dialog.
     */
    private JDialog parentDialog;

    /**
     * Creates a new ProteinInferenceGraphPanel.
     *
     * @param parentDialog the parent dialog
     * @param parentPanel the parent panel
     * @param nodes the protein and peptide nodes
     * @param edges the edges, key is the starting node and the element is all
     * the ending nodes
     * @param nodeProperties the node properties
     * @param edgeProperties the edge properties
     * @param nodeToolTips the node tooltips
     * @param selectedNodes the list of selected nodes
     */
    public ProteinInferenceGraphPanel(JDialog parentDialog, JPanel parentPanel, ArrayList<String> nodes, HashMap<String, ArrayList<String>> edges,
            HashMap<String, String> nodeProperties, HashMap<String, String> edgeProperties, HashMap<String, String> nodeToolTips, ArrayList<String> selectedNodes) {
        initComponents();

        this.parentDialog = parentDialog;
        this.nodes = nodes;
        this.edges = edges;
        this.nodeProperties = nodeProperties;
        this.edgeProperties = edgeProperties;
        this.nodeToolTips = nodeToolTips;

        visualizationViewer = setUpGraph(parentPanel);

        // select the proteins part of the current protein group
        for (String tempNode : nodes) {
            if (selectedNodes != null && selectedNodes.contains(tempNode)) {
                visualizationViewer.getPickedVertexState().pick(tempNode, true);
            }
        }

        graphPanel.add(visualizationViewer);

        ScalingControl scaler = new CrossoverScalingControl();
        scaler.scale(visualizationViewer, 0.9f, visualizationViewer.getCenter());
    }

    /**
     * Set up the graph.
     *
     * @param parentPanel the parent panel
     * @return the visualization viewer
     */
    private VisualizationViewer setUpGraph(JPanel parentPanel) {

        graph = new UndirectedSparseGraph<String, String>();

        // add all the nodes
        for (String node : nodes) {
            graph.addVertex(node);
        }

        // add the vertexes
        Iterator<String> startNodeKeys = edges.keySet().iterator();

        while (startNodeKeys.hasNext()) {

            String startNode = startNodeKeys.next();

            for (String endNode : edges.get(startNode)) {
                graph.addEdge(startNode + "|" + endNode, startNode, endNode);
            }
        }

        // create the visualization viewer
        VisualizationViewer<String, String> vv = new VisualizationViewer<String, String>(new FRLayout<String, String>(graph),
                new Dimension(parentPanel.getWidth() - 20, parentPanel.getHeight() - 100));
        vv.setBackground(Color.WHITE);

        // set the vertex label transformer
        vv.getRenderContext().setVertexLabelTransformer(new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        });

        // set the edge label transformer
        vv.getRenderContext().setEdgeLabelTransformer(new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        });

        // set the vertex renderer
        vv.getRenderer().setVertexRenderer(new ProteinInferenceVertexRenderer());

        // set the edge label renderer
        vv.getRenderer().setEdgeLabelRenderer(new BasicEdgeLabelRenderer<String, String>() {

            @Override
            public void labelEdge(RenderContext<String, String> rc, Layout<String, String> layout, String e, String label) {
                // do nothing
            }
        });

        // set the vertex label renderer
        vv.getRenderer().setVertexLabelRenderer(new BasicVertexLabelRenderer<String, String>() {

            @Override
            public void labelVertex(RenderContext<String, String> rc, Layout<String, String> layout, String v, String label) {
                if (label.startsWith("Peptide") && showPeptideLabels) {
                    super.labelVertex(rc, layout, v, label.substring(label.indexOf(" ") + 1));
                }
                if (label.startsWith("Protein") && showProteinLabels) {
                    super.labelVertex(rc, layout, v, label.substring(label.indexOf(" ") + 1));
                }
            }
        });

        // set the edge format
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStroke);

        // set the mouse interaction mode
        final DefaultModalGraphMouse<String, Number> graphMouse = new DefaultModalGraphMouse<String, Number>();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(graphMouse);

        // add a key listener
        vv.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
                    for (String tempNode : nodes) {
                        visualizationViewer.getPickedVertexState().pick(tempNode, true);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    for (String tempNode : nodes) {
                        visualizationViewer.getPickedVertexState().pick(tempNode, false);
                    }
                }
                super.keyReleased(e);
            }

        });

        // set the vertex tooltips
        vv.setVertexToolTipTransformer(
                new ToStringLabeller<String>() {

                    @Override
                    public String transform(String v) {
                        if (nodeToolTips != null && nodeToolTips.get(v) != null) {
                            return super.transform(nodeToolTips.get(v));
                        } else {
                            return super.transform(v.substring(v.indexOf(" ") + 1));
                        }
                    }
                }
        );

        // attach the listener that will print when the vertices selection changes
        final PickedState<String> pickedState = vv.getPickedVertexState();
        pickedState.addItemListener(
                new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        Object subject = e.getItem();
                        if (subject instanceof String) {
                            String vertex = (String) subject;
                            if (pickedState.isPicked(vertex)) {
                                if (!selectedNodes.contains(vertex)) {
                                    selectedNodes.add(vertex);
                                }
                            } else {
                                selectedNodes.remove(vertex);
                            }
                        }
                        updateNodeSelection();
                    }
                }
        );

        return vv;
    }

    /**
     * Update the node selection.
     */
    private void updateNodeSelection() {

        selectedNeighborNodes = new ArrayList<String>();
        selectedEdges = new ArrayList<String>();

        // get the list of all neighbors of selected nodes and the edges involved
        for (String node : selectedNodes) {

            Collection<String> neighbors = graph.getNeighbors(node);

            for (String tempNode : neighbors) {
                if (allRadioButton.isSelected()) {
                    if (!selectedNeighborNodes.contains(tempNode)) {
                        selectedNeighborNodes.add(tempNode);
                    }
                } else {
                    if (graph.getNeighbors(tempNode).size() == 1) {
                        if (!selectedNeighborNodes.contains(tempNode)) {
                            selectedNeighborNodes.add(tempNode);
                        }
                    }
                }
            }

            Collection<String> inEdges = graph.getInEdges(node);
            for (String tempEdge : inEdges) {

                String[] tempNodes = tempEdge.split("\\|");

                if ((selectedNodes.contains(tempNodes[0]) && selectedNeighborNodes.contains(tempNodes[1]))
                        || selectedNodes.contains(tempNodes[1]) && selectedNeighborNodes.contains(tempNodes[0])) {
                    if (!selectedEdges.contains(tempEdge)) {
                        selectedEdges.add(tempEdge);
                    }
                }
            }

            Collection<String> outEdges = graph.getOutEdges(node);
            for (String tempEdge : outEdges) {
                String[] tempNodes = tempEdge.split("\\|");

                if ((selectedNodes.contains(tempNodes[0]) && selectedNeighborNodes.contains(tempNodes[1]))
                        || selectedNodes.contains(tempNodes[1]) && selectedNeighborNodes.contains(tempNodes[0])) {
                    if (!selectedEdges.contains(tempEdge)) {
                        selectedEdges.add(tempEdge);
                    }
                }
            }
        }

        int proteinCount = 0;
        int peptideCount = 0;
        for (String tempNode : selectedNodes) {
            if (tempNode.startsWith("Protein")) {
                proteinCount++;
            } else if (tempNode.startsWith("Peptide")) {
                peptideCount++;
            }
        }
        for (String tempNode : selectedNeighborNodes) {
            if (!selectedNodes.contains(tempNode)) {
                if (tempNode.startsWith("Protein")) {
                    proteinCount++;
                } else if (tempNode.startsWith("Peptide")) {
                    peptideCount++;
                }
            }
        }

        proteinCountValueLabel.setText("<html><a href>" + proteinCount + "</html>");
        peptideCountValueLabel.setText("<html><a href>" + peptideCount + "</html>");
    }

    /**
     * The color formatting for the edges.
     */
    private Transformer<String, Paint> edgePaint = new Transformer<String, Paint>() {
        public Paint transform(String s) {
            if (selectedEdges.isEmpty()) {
                return new Color(100, 100, 100, 100);
            } else if (selectedEdges.contains(s)) {
                return new Color(100, 100, 100, 255);
            } else {
                return new Color(100, 100, 100, 100);
            }
        }
    };

    /**
     * The stroke type for the edges.
     */
    private Transformer<String, Stroke> edgeStroke = new Transformer<String, Stroke>() {
        float dash[] = {10.0f};

        public Stroke transform(String s) {

            String edgeProperty = edgeProperties.get(s);

            if (edgeProperty != null) {
                if (Boolean.parseBoolean(edgeProperty)) {
                    return new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER, 10.0f);
                } else {
                    return new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
                }
            }

            return new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        annotationButtonGroup = new javax.swing.ButtonGroup();
        selectionButtonGroup = new javax.swing.ButtonGroup();
        selectionPanel = new javax.swing.JPanel();
        proteinCountLabel = new javax.swing.JLabel();
        proteinCountValueLabel = new javax.swing.JLabel();
        peptideCountLabel = new javax.swing.JLabel();
        peptideCountValueLabel = new javax.swing.JLabel();
        graphPanel = new javax.swing.JPanel();
        settingsPanel = new javax.swing.JPanel();
        evidenceRadioButton = new javax.swing.JRadioButton();
        confidenceRadioButton = new javax.swing.JRadioButton();
        noAnnotationRadioButton = new javax.swing.JRadioButton();
        allRadioButton = new javax.swing.JRadioButton();
        uniqueRadioButton = new javax.swing.JRadioButton();
        highlightCheckBox = new javax.swing.JCheckBox();

        selectionPanel.setBackground(new java.awt.Color(255, 255, 255));

        proteinCountLabel.setText("#Proteins:");

        proteinCountValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        proteinCountValueLabel.setText("0");
        proteinCountValueLabel.setMinimumSize(new java.awt.Dimension(14, 14));
        proteinCountValueLabel.setPreferredSize(new java.awt.Dimension(14, 14));
        proteinCountValueLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                proteinCountValueLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                proteinCountValueLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                proteinCountValueLabelMouseReleased(evt);
            }
        });

        peptideCountLabel.setText("#Peptides:");

        peptideCountValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        peptideCountValueLabel.setText("0");
        peptideCountValueLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                peptideCountValueLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                peptideCountValueLabelMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                peptideCountValueLabelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout selectionPanelLayout = new javax.swing.GroupLayout(selectionPanel);
        selectionPanel.setLayout(selectionPanelLayout);
        selectionPanelLayout.setHorizontalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectionPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proteinCountLabel)
                    .addComponent(peptideCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proteinCountValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(peptideCountValueLabel))
                .addContainerGap())
        );

        selectionPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {peptideCountValueLabel, proteinCountValueLabel});

        selectionPanelLayout.setVerticalGroup(
            selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectionPanelLayout.createSequentialGroup()
                .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proteinCountLabel)
                    .addComponent(proteinCountValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(selectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(peptideCountLabel)
                    .addComponent(peptideCountValueLabel)))
        );

        graphPanel.setBackground(new java.awt.Color(255, 255, 255));
        graphPanel.setLayout(new javax.swing.BoxLayout(graphPanel, javax.swing.BoxLayout.LINE_AXIS));

        settingsPanel.setBackground(new java.awt.Color(255, 255, 255));

        annotationButtonGroup.add(evidenceRadioButton);
        evidenceRadioButton.setText("Evidence");
        evidenceRadioButton.setOpaque(false);
        evidenceRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evidenceRadioButtonActionPerformed(evt);
            }
        });

        annotationButtonGroup.add(confidenceRadioButton);
        confidenceRadioButton.setText("Confidence");
        confidenceRadioButton.setOpaque(false);
        confidenceRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confidenceRadioButtonActionPerformed(evt);
            }
        });

        annotationButtonGroup.add(noAnnotationRadioButton);
        noAnnotationRadioButton.setSelected(true);
        noAnnotationRadioButton.setText("None");
        noAnnotationRadioButton.setOpaque(false);
        noAnnotationRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noAnnotationRadioButtonActionPerformed(evt);
            }
        });

        selectionButtonGroup.add(allRadioButton);
        allRadioButton.setSelected(true);
        allRadioButton.setText("All Neighbors");
        allRadioButton.setOpaque(false);
        allRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allRadioButtonActionPerformed(evt);
            }
        });

        selectionButtonGroup.add(uniqueRadioButton);
        uniqueRadioButton.setText("Unique Only");
        uniqueRadioButton.setOpaque(false);
        uniqueRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uniqueRadioButtonActionPerformed(evt);
            }
        });

        highlightCheckBox.setText("Highlight");
        highlightCheckBox.setOpaque(false);
        highlightCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(allRadioButton)
                    .addComponent(confidenceRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addComponent(evidenceRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(noAnnotationRadioButton))
                    .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addComponent(uniqueRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(highlightCheckBox)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        settingsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {allRadioButton, confidenceRadioButton, evidenceRadioButton, highlightCheckBox, noAnnotationRadioButton, uniqueRadioButton});

        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(evidenceRadioButton)
                    .addComponent(confidenceRadioButton)
                    .addComponent(noAnnotationRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uniqueRadioButton)
                    .addComponent(allRadioButton)
                    .addComponent(highlightCheckBox))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(selectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(selectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(settingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Update the graph.
     *
     * @param evt
     */
    private void allRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allRadioButtonActionPerformed
        updateNodeSelection();
        this.repaint();
    }//GEN-LAST:event_allRadioButtonActionPerformed

    /**
     * Update the graph.
     *
     * @param evt
     */
    private void uniqueRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uniqueRadioButtonActionPerformed
        updateNodeSelection();
        this.repaint();
    }//GEN-LAST:event_uniqueRadioButtonActionPerformed

    /**
     * Update the graph.
     *
     * @param evt
     */
    private void confidenceRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confidenceRadioButtonActionPerformed
        this.repaint();
    }//GEN-LAST:event_confidenceRadioButtonActionPerformed

    /**
     * Update the graph.
     *
     * @param evt
     */
    private void evidenceRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evidenceRadioButtonActionPerformed
        this.repaint();
    }//GEN-LAST:event_evidenceRadioButtonActionPerformed

    /**
     * Update the graph.
     *
     * @param evt
     */
    private void noAnnotationRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noAnnotationRadioButtonActionPerformed
        this.repaint();
    }//GEN-LAST:event_noAnnotationRadioButtonActionPerformed

    /**
     * Update the graph.
     *
     * @param evt
     */
    private void highlightCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightCheckBoxActionPerformed
        this.repaint();
    }//GEN-LAST:event_highlightCheckBoxActionPerformed

    /**
     * Change the cursor to a hand cursor.
     *
     * @param evt
     */
    private void proteinCountValueLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinCountValueLabelMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_proteinCountValueLabelMouseEntered

    /**
     * Change the cursor back to the default cursor.
     *
     * @param evt
     */
    private void proteinCountValueLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinCountValueLabelMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_proteinCountValueLabelMouseExited

    /**
     * Open the ProteinInferenceGraphSelectionDialog.
     *
     * @param evt
     */
    private void proteinCountValueLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinCountValueLabelMouseReleased
        new ProteinInferenceGraphSelectionDialog(parentDialog, true, getSelectedValuesAsString());
    }//GEN-LAST:event_proteinCountValueLabelMouseReleased

    /**
     * Change the cursor to a hand cursor.
     *
     * @param evt
     */
    private void peptideCountValueLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peptideCountValueLabelMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_peptideCountValueLabelMouseEntered

    /**
     * Change the cursor back to the default cursor.
     *
     * @param evt
     */
    private void peptideCountValueLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peptideCountValueLabelMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_peptideCountValueLabelMouseExited

    /**
     * Open the ProteinInferenceGraphSelectionDialog.
     *
     * @param evt
     */
    private void peptideCountValueLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peptideCountValueLabelMouseReleased
        new ProteinInferenceGraphSelectionDialog(parentDialog, true, getSelectedValuesAsString());
    }//GEN-LAST:event_peptideCountValueLabelMouseReleased

    /**
     * Get the selected node as an HTML string.
     *
     * @return the selected node as an HTML string
     */
    private String getSelectedValuesAsString() {

        String proteinSelection = "";
        String peptideSelection = "";

        int proteinCount = 0;
        int peptideCount = 0;

        for (String tempNode : selectedNodes) { // @TODO: clean up and reformat this code
            if (tempNode.startsWith("Protein")) {
                if (!proteinSelection.isEmpty()) {
                    proteinSelection += "<br>";
                }
                if (nodeToolTips != null && nodeToolTips.containsKey(tempNode)) {
                    proteinSelection += ++proteinCount + ": " + convertHtmlTooltip(nodeToolTips.get(tempNode));
                } else {
                    proteinSelection += ++proteinCount + ": " + tempNode.substring(tempNode.indexOf(" ") + 1);
                }
            } else if (tempNode.startsWith("Peptide")) {
                if (!peptideSelection.isEmpty()) {
                    peptideSelection += "<br>";
                }
                if (nodeToolTips != null && nodeToolTips.containsKey(tempNode)) {
                    peptideSelection += ++peptideCount + ": " + convertHtmlTooltip(nodeToolTips.get(tempNode));
                } else {
                    peptideSelection += ++peptideCount + ": " + tempNode.substring(tempNode.indexOf(" ") + 1);
                }
            }
        }

        for (String tempNode : selectedNeighborNodes) {
            if (!selectedNodes.contains(tempNode)) {
                if (tempNode.startsWith("Protein")) {
                    if (!proteinSelection.isEmpty()) {
                        proteinSelection += "<br>";
                    }
                    if (nodeToolTips != null && nodeToolTips.containsKey(tempNode)) {
                        proteinSelection += ++proteinCount + ": " + convertHtmlTooltip(nodeToolTips.get(tempNode));
                    } else {
                        proteinSelection += ++proteinCount + ": " + tempNode.substring(tempNode.indexOf(" ") + 1);
                    }
                } else if (tempNode.startsWith("Peptide")) {
                    if (!peptideSelection.isEmpty()) {
                        peptideSelection += "<br>";
                    }
                    if (nodeToolTips != null && nodeToolTips.containsKey(tempNode)) {
                        peptideSelection += ++peptideCount + ": " + convertHtmlTooltip(nodeToolTips.get(tempNode));
                    } else {
                        peptideSelection += ++peptideCount + ": " + tempNode.substring(tempNode.indexOf(" ") + 1);
                    }
                }
            }
        }

        return "<html><b>Proteins:</b><br>" + proteinSelection + "<br><br><b>Peptides:</b><br>" + peptideSelection + "</html>";
    }

    /**
     * Replaces the HTML tags in a node tooltip.
     *
     * @param tooltipAsHtml the original HTML tooltip
     * @return the new tooltip without HTML tags
     */
    private String convertHtmlTooltip(String tooltipAsHtml) {
        String temp = tooltipAsHtml.replaceAll(Pattern.quote("<br>"), " - ");
        temp = temp.replaceAll(Pattern.quote("<html>"), "");
        temp = temp.replaceAll(Pattern.quote("</html>"), "");
        return temp;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton allRadioButton;
    private javax.swing.ButtonGroup annotationButtonGroup;
    private javax.swing.JRadioButton confidenceRadioButton;
    private javax.swing.JRadioButton evidenceRadioButton;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JCheckBox highlightCheckBox;
    private javax.swing.JRadioButton noAnnotationRadioButton;
    private javax.swing.JLabel peptideCountLabel;
    private javax.swing.JLabel peptideCountValueLabel;
    private javax.swing.JLabel proteinCountLabel;
    private javax.swing.JLabel proteinCountValueLabel;
    private javax.swing.ButtonGroup selectionButtonGroup;
    private javax.swing.JPanel selectionPanel;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JRadioButton uniqueRadioButton;
    // End of variables declaration//GEN-END:variables
    
    /**
     * The protein inference vertex renderer.
     */
    public class ProteinInferenceVertexRenderer implements Renderer.Vertex<String, String> {

        @Override
        public void paintVertex(RenderContext<String, String> rc, Layout<String, String> layout, String vertex) {
            GraphicsDecorator graphicsContext = rc.getGraphicsContext();
            Point2D center = layout.transform(vertex);
            Shape shape = null;
            Color color = null;

            int alpha = 255;
            boolean selectedAsNeighbor = false;

            // check if the node is selected or should be semi-transparent
            if (!selectedNeighborNodes.isEmpty()) {
                if (selectedNeighborNodes.contains(vertex)) {
                    selectedAsNeighbor = true;
                }
                if (!selectedNeighborNodes.contains(vertex) && !selectedNodes.contains(vertex)) {
                    alpha = 50;
                }
            } else {
                if (!selectedNodes.contains(vertex)) {
                    alpha = 50;
                }
            }

            // draw a highlight to indicate the selected nodes
            if (selectedNodes.contains(vertex) && highlightCheckBox.isSelected()) {
                if (confidenceRadioButton.isSelected()) {
                    shape = new Ellipse2D.Double(center.getX() - 18, center.getY() - 18, 36, 36);
                    color = new Color(150, 150, 150);
                    graphicsContext.setPaint(color);
                    graphicsContext.draw(shape);
                } else {
                    shape = new Ellipse2D.Double(center.getX() - 14, center.getY() - 14, 28, 28);
                    color = new Color(150, 150, 150);
                    graphicsContext.setPaint(color);
                    graphicsContext.draw(shape);
                }
            }

            // draw a highlight indicating the validation or evidence level
            boolean highlightAdded = false;
            //if ((selectedNodes.contains(vertex) || selectedAsNeighbor) && !noAnnotationRadioButton.isSelected()) {
            if (!noAnnotationRadioButton.isSelected()) {
                shape = new Ellipse2D.Double(center.getX() - 14, center.getY() - 14, 28, 28);

                if (nodeProperties.get(vertex) != null) {

                    String[] properties = nodeProperties.get(vertex).split("\\|");

                    if (confidenceRadioButton.isSelected()) {
                        int validationLevel = Integer.parseInt(properties[0]);

                        if (validationLevel == 0) {
                            color = new Color(255, 0, 0, alpha);
                        } else if (validationLevel == 1) {
                            color = new Color(255, 204, 0, alpha);
                        } else if (validationLevel == 2) {
                            color = new Color(110, 196, 97, alpha);
                        } else {
                            color = new Color(200, 200, 200, alpha);
                        }

                        graphicsContext.setPaint(color);
                        graphicsContext.fill(shape);
                        highlightAdded = true;

                    } else if (properties.length > 1) {
                        int evidenceLevel = Integer.parseInt(properties[1]);

                        if (evidenceLevel == 1) { // protein
                            color = new Color(110, 196, 97, alpha);
                        } else if (evidenceLevel == 2) { // transcript
                            color = new Color(255, 204, 0, alpha);
                        } else if (evidenceLevel == 3) { // homology
                            color = new Color(110, 196, 197, alpha);
                        } else if (evidenceLevel == 4) { // predicted
                            color = new Color(80, 106, 197, alpha);
                        } else if (evidenceLevel == 5) { // uncertain
                            color = new Color(255, 0, 0, alpha);
                        } else { // unknown...
                            color = new Color(200, 200, 200, alpha);
                        }

                        graphicsContext.setPaint(color); // @TODO: check the colors used!
                        graphicsContext.fill(shape);
                        highlightAdded = true;
                    }
                }
            }

            // draw the actual vertex
            if (vertex.startsWith("Protein")) {
                shape = new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20);
                color = new Color(255, 0, 0, alpha);
            } else if (vertex.startsWith("Peptide")) {
                shape = new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20);
                color = new Color(0, 0, 255, alpha);
            }
            graphicsContext.setPaint(color);
            graphicsContext.fill(shape);

            // draw a thin border around the vertex
            shape = new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20);
            color = new Color(150, 150, 150, alpha);
            graphicsContext.setPaint(color);
            graphicsContext.draw(shape);

            // draw a thin border around the highlight
            if ((selectedNodes.contains(vertex) || selectedAsNeighbor) && !noAnnotationRadioButton.isSelected() && highlightAdded) {
                shape = new Ellipse2D.Double(center.getX() - 14, center.getY() - 14, 28, 28);
                color = new Color(150, 150, 150, alpha);
                graphicsContext.setPaint(color);
                graphicsContext.draw(shape);
            }
        }
    }
}

package Vista;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Modelo.Candidato;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Carlos
 */
public class VentanaTabla extends javax.swing.JFrame {

    /**
     * Creates new form VtnTabla
     */
    public VentanaTabla() {
        initComponents();
    }

    private void Clear_Table() {
        DefaultTableModel modeloDeLaTabla = (DefaultTableModel) this.tablaVotos.getModel();
        for (int i = 0; i < tablaVotos.getRowCount(); i++) {
            modeloDeLaTabla.removeRow(0);
            i -= 1;
        }
    }

    public void llenaTabla(ArrayList<Candidato> votos) {
        
        Clear_Table();
        
        //Declaramos las columnas:
        Object columnasDeDatos[] = new Object[2];

        //obtenemos el modelo default de la tabla:
        DefaultTableModel modeloDeLaTabla = (DefaultTableModel) this.tablaVotos.getModel();

        if (votos != null) {
            //agregamos a cada columna los datos que le corresponden:
            for (Candidato cand : votos) {
                columnasDeDatos[0] = cand.getNombre();
                columnasDeDatos[1] = cand.getNumVotos();

                //agregamos los datos de cada columna en cada renglÃ³n:
                modeloDeLaTabla.addRow(columnasDeDatos);
            }

        }
        //establecemos a nuestra tabla, el modelo que tenÃ­a:

        this.tablaVotos.setModel(modeloDeLaTabla);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVotos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaVotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Candidato", "Votos"
            }
        ));
        jScrollPane1.setViewportView(tablaVotos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVotos;
    // End of variables declaration//GEN-END:variables
}

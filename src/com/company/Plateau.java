package com.company;

import com.company.configurationjframe.boutton.ConfigurationDimensionFrame;
import com.company.configurationjframe.boutton.ConfigurerBouttonJframeBas;
import com.company.configurationjframe.boutton.ConfigurerBouttonJframeHaut;

import javax.swing.*;

import static java.lang.Integer.parseInt;

public class Plateau extends JFrame  {
    public JFrame getFrame() {
        return frame;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    private JFrame frame = new JFrame();
    ConfigurationDimensionFrame configurationBouttonDuHaut = new ConfigurerBouttonJframeHaut();
    ConfigurationDimensionFrame configurationBouttonDuBas = new ConfigurerBouttonJframeBas();

    public Plateau() throws Exception {
        // first frame let the choice to the player
        JFrame frameChoixJoueur = new JFrame(ConfigManager.recupererVariableGlobale("titre"));
        JButton btHuman = new JButton(ConfigManager.recupererVariableGlobale("choixhumain"));
        JButton btBot = new JButton(ConfigManager.recupererVariableGlobale("choixbot"));
        configurationBouttonDuHaut.configure(btHuman);
        configurationBouttonDuBas.configure(btBot);
        frameChoixJoueur.add(btHuman);
        frameChoixJoueur.add(btBot);
        frameChoixJoueur.setSize(
                parseInt(ConfigManager.recupererVariableGlobale("framelargeur")),
                parseInt(ConfigManager.recupererVariableGlobale("frameHauteur"))
        );
        frameChoixJoueur.setLayout(null);
        frameChoixJoueur.setVisible(true);

        // prepare the main frame
        creerFramePrincipaleDuJeu();

        // if the player choose against human, open the regular jframe
        btHuman.addActionListener(e -> {
            frameChoixJoueur.setVisible(false);
            frame.setVisible(true);
            frame.setContentPane(new Human());
        });

        // if player choose agains't bot
        btBot.addActionListener(e -> {
                frameChoixJoueur.setVisible(false);
                JFrame colorChoice =new JFrame(ConfigManager.recupererVariableGlobale("choixCouleur"));
                JButton btWhite = new JButton(ConfigManager.recupererVariableGlobale("blanc"));
                JButton btBlack = new JButton(ConfigManager.recupererVariableGlobale("noir"));
                configurationBouttonDuHaut.configure(btWhite);
                configurationBouttonDuBas.configure(btBlack);
                colorChoice.add(btWhite);
                colorChoice.add(btBlack);
                colorChoice.setSize(
                        parseInt(ConfigManager.recupererVariableGlobale("framelargeur")),
                        parseInt(ConfigManager.recupererVariableGlobale("frameHauteur"))
                );
                colorChoice.setLayout(null);
                colorChoice.setVisible(true);

                btWhite.addActionListener(e2 -> {
                    colorChoice.setVisible(false);
                    frame.setVisible(true);
                    frame.setContentPane(new Bot(true));
                });
                btBlack.addActionListener(e3 -> {
                    colorChoice.setVisible(false);
                    frame.setVisible(true);
                    frame.setContentPane(new Bot(false));
                });

        });
    }

    private void creerFramePrincipaleDuJeu(){
        frame.setTitle("Jeux de dame");
        frame.setSize(
                parseInt(ConfigManager.recupererVariableGlobale("framePrincipalLargeur")),
                parseInt(ConfigManager.recupererVariableGlobale("framePrincipalHauteur"))
        );
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}


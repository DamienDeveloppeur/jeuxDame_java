package com.company.configurationjframe.boutton;

import com.company.ConfigManager;

import javax.swing.*;

import static java.lang.Integer.parseInt;

public class ConfigurerBouttonJframeBas implements ConfigurationDimensionFrame {

    @Override
    public void configure(JButton frame) {
        try {
            frame.setBounds(
                    parseInt(ConfigManager.recupererVariableGlobale("bouttonX")),
                    parseInt(ConfigManager.recupererVariableGlobale("bouttonbasY")),
                    parseInt(ConfigManager.recupererVariableGlobale("bouttonLargeur")),
                    parseInt(ConfigManager.recupererVariableGlobale("bouttonhauteur"))
            );
        } catch (Exception e) {
            throw new RuntimeException("Impossible de récupérer une variable globale : "+e);
        }

    }
}

package en.queengame.configurationjframe.boutton;

import en.queengame.ConfigManager;

import javax.swing.*;

import static java.lang.Integer.parseInt;

public class ConfigurerBouttonJframeHaut implements ConfigurationDimensionFrame {
    @Override
    public void configure(JButton frame) {
        try {
            frame.setBounds(
                    parseInt(ConfigManager.recupererVariableGlobale("bouttonX")),
                    parseInt(ConfigManager.recupererVariableGlobale("buttonHautY")),
                    parseInt(ConfigManager.recupererVariableGlobale("bouttonLargeur")),
                    parseInt(ConfigManager.recupererVariableGlobale("bouttonhauteur"))
            );
        } catch (Exception e) {
            throw new RuntimeException("Impossible de récupérer une variable globale : "+e);
        }
    }
}

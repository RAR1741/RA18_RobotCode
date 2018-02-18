package org.redalert1741.robotbase.logging;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class LoggablePdp implements Loggable {
    PowerDistributionPanel pdp;

    public LoggablePdp() {
        pdp = new PowerDistributionPanel(20);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute("voltage");
        logger.addAttribute("current");
        logger.addAttribute("power");
        logger.addAttribute("energy");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log("voltage", pdp.getVoltage());
        logger.log("current", pdp.getTotalCurrent());
        logger.log("power", pdp.getTotalPower());
        logger.log("energy", pdp.getTotalEnergy());
    }

}

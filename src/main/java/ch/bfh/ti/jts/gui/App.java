package ch.bfh.ti.jts.gui;

import java.awt.Graphics2D;

import ch.bfh.ti.jts.data.Net;
import ch.bfh.ti.jts.importer.Importer;
import ch.bfh.ti.jts.simulation.Simulation;

public class App implements Runnable {
    
    private final Net        net;
    private final Window     window;
    private final Simulation simulation;
    
    public App() {
        net = new Importer().importData("src/main/resources/net.net.xml");
        window = new Window(g -> {
            render(g);
        });
        simulation = new Simulation(net);
    }
    
    @Override
    public void run() {
        init();
        while (isRunning() && !Thread.interrupted()) {
            update();
            window.render();
        }
        end();
    }
    
    private void init() {
        window.setVisible(true);
    }
    
    private boolean isRunning() {
        return true;
    }
    
    private void update() {
        simulation.tick();
    }
    
    private void render(final Graphics2D g) {
        net.render(g);
    }
    
    private void end() {
    }
}

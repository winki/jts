package ch.bfh.ti.jts.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListSet;

import ch.bfh.ti.jts.gui.data.PolyShape;

public class Lane extends Element {
    
    public final static int                    LANE_RENDER_LAYER = Edge.EDGE_RENDER_LAYER + 1;
    private final Edge                         edge;
    private final int                          index;
    private final double                       speed;
    private final double                       length;
    private final PolyShape                    polyShape;
    private final Collection<Lane>             lanes;
    /**
     * Skiplist of agents on the line. Key: Position on line, Value: Agent
     */
    private final ConcurrentSkipListSet<Agent> agents;
    /**
     * A comperator for Agents on a Line
     *
     * @author ente
     */
    private class AgentLineComperator implements Comparator<Agent> {
        
        @Override
        public int compare(final Agent a1, final Agent a2) {
            return new Double(a1.getPosition()).compareTo(a2.getPosition());
        }
    }
    
    public Lane(final Edge edge, final int index, final double speed, final double length, final PolyShape polyShape) {
        if (edge == null) {
            throw new IllegalArgumentException("edge is null");
        }
        if (polyShape == null) {
            throw new IllegalArgumentException("polyShape is null");
        }
        this.edge = edge;
        this.index = index;
        this.speed = speed;
        this.length = length;
        this.polyShape = polyShape;
        this.lanes = new LinkedList<Lane>();
        this.agents = new ConcurrentSkipListSet<Agent>(new AgentLineComperator());
    }
    
    public Edge getEdge() {
        return edge;
    }
    
    public int getIndex() {
        return index;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public double getLength() {
        return length;
    }
    
    public Collection<Lane> getLanes() {
        return lanes;
    }
    
    public PolyShape getPolyShape() {
        return polyShape;
    }
    
    public boolean goesTo(final Junction junction) {
        return getEdge().getEnd() == junction;
    }
    
    public boolean comesFrom(final Junction junction) {
        return getEdge().getStart() == junction;
    }
    
    public Lane getLeftLane() {
        return getEdge().getLanes().stream().filter(x -> x.index == index + 1).findAny().orElse(null);
    }
    
    public Lane getRightLane() {
        return getEdge().getLanes().stream().filter(x -> x.index == index - 1).findAny().orElse(null);
    }
    
    @Override
    public int getRenderLayer() {
        return LANE_RENDER_LAYER;
    }
    
    public ConcurrentSkipListSet<Agent> getAgents() {
        return agents;
    }
    
    @Override
    public void render(final Graphics2D g) {
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.WHITE);
        g.draw(polyShape.getShape());
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLACK);
        g.draw(polyShape.getShape());
    }
}

package ch.bfh.ti.jts.utils.layers;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * A data structure which holds its elements in layers.
 * 
 * @author ente
 * @param <T>
 *            the object to hold
 */
public class Layers<T> {
    
    private final Map<Integer, Collection<T>> layers    = new HashMap<Integer, Collection<T>>();
    private final SortedSet<Integer>          layerKeys = new TreeSet<>();
    
    public void addLayerable(final int layer, final T layerable) {
        if (layerable == null) {
            throw new IllegalArgumentException("layerable");
        }
        // does the layer exist?
        if (!layers.containsKey(layer)) {
            // add a new layer
            layers.put(layer, new LinkedList<T>());
            layerKeys.add(layer);
        }
        // add layerable to layer
        layers.get(layer).add(layerable);
    }
    
    public Iterable<Integer> getLayersIterator() {
        return layerKeys;
    }
    
    public Stream<T> getLayerStream(final Integer layer) throws IndexOutOfBoundsException {
        if (!layerKeys.contains(layer)) {
            throw new IndexOutOfBoundsException("layer");
        }
        return layers.get(layer).stream();
    }
}

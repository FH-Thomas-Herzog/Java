package at.fh.ooe.swe4.fx.campina.view.context;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.Node;

public class DefaultContext {

	final Map<Object, Object>	cache	= new ConcurrentHashMap<>();

	public static final String	KEY		= "DEFAULT_CONTEXT";

	

	public Object get(final Object key) {
		return cache.get(key);
	}
}

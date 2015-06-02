package at.fh.ooe.swe4.fx.campina.view.api;

import javafx.scene.Node;

public interface ScenePart<N> extends IdHolder<String> {

	public N create();
	
	public void init();
}

package swe4.gui;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class ScribbleCanvas extends Node {

	@Override
	protected NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean impl_computeContains(double localX, double localY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addLineSegment(Direction down) {
		// TODO Auto-generated method stub

	}

	public void setLineColor(Color value) {
		// TODO Auto-generated method stub

	}

	public void addLineSegment(Point2D p) {
		// TODO Auto-generated method stub

	}

}

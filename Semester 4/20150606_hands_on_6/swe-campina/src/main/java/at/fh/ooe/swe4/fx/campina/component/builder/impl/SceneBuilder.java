package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import at.fh.ooe.swe4.fx.campina.component.builder.exception.DuplicateKeyException;

public class SceneBuilder {

    private Set<Node> nodes;

    public SceneBuilder() {
    }

    public SceneBuilder init() {

	nodes = new HashSet<Node>();
	return this;
    }

    public SceneBuilder end() {

	nodes = null;
	return this;
    }

    public Scene build(final String id) {
	Objects.requireNonNull(id, "");
	return null;
    }

    public SceneBuilder addHBox(final String id) {
	return addHBox(id, null);
    }

    public SceneBuilder addHBox(final String id,
	    final Collection<? extends Node> children) {
	Objects.requireNonNull(id, "Cannot create hbox for null id");

	final HBox hbox = new HBox();
	hbox.setId(id);
	if ((children != null) && (!children.isEmpty())) {
	    hbox.getChildren().addAll(children);
	}

	if (!nodes.add(hbox)) {
	    throw new DuplicateKeyException(
		    "Element with this id already exists in this context");
	}

	return this;
    }

    public Node getNodeById(final String id) {
	if (id != null) {
	    final List<Node> result = nodes.stream()
		    .filter(new Predicate<Node>() {
			@Override
			public boolean test(Node t) {
			    return (t != null) ? id.equals(t.getId())
				    : Boolean.FALSE;
			}
		    }).collect(Collectors.toList());
	    return (!result.isEmpty()) ? result.get(0) : null;
	}
	return null;
    }

    public SceneBuilder removeNodeById(final String id) {
	Objects.requireNonNull(id, "Cannot remove node with null id");

	final Node node = getNodeById(id);
	if (node != null) {
	    nodes.remove(node);
	}

	return this;
    }

    public SceneBuilder addNode(final Node node) {
	nodes.add(node);
	return this;
    }
}

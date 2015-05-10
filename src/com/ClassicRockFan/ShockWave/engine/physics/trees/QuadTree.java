package com.ClassicRockFan.ShockWave.engine.physics.trees;

import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class QuadTree {
    private final QTreeNode node;

    // define a quadtree extends as width and height, define quadtree depth.
    public QuadTree(final float worldExtends, int worldDepth) {
        node = new QTreeNode(0,0,worldExtends, worldDepth);
    }

    // insert a GameObject at the quadtree
    public void insertObject(final Entity obj) {
        node.insertObject(obj, obj.getPhysicsComponent().getCollider());
    }

    // clean the quadtree
    public void clean() {
        node.clean();
    }
}

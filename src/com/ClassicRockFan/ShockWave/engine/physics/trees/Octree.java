package com.ClassicRockFan.ShockWave.engine.physics.trees;

import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class Octree {
    private final OctreeNode node;

    // define a octree extends as width and height, define quadtree depth.
    public Octree(final float worldExtends, int worldDepth) {
        node = new OctreeNode(0, 0, 0, worldExtends, worldDepth);
    }

    // insert a GameObject at the octree
    public void insertObject(final Entity obj) {
        node.insertObject(obj, obj.getPhysicsComponent().getCollider());
    }

    // clean the octree
    public void clean() {
        node.clean();
    }

    public OctreeNode getInitialNode(){
        return node;
    }
}

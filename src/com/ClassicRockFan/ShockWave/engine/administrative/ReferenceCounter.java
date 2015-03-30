package com.ClassicRockFan.ShockWave.engine.administrative;

/**
 * Created by Tyler on 3/19/2015.
 */

public class ReferenceCounter {

    private int refCount;

    public ReferenceCounter() {
        this.refCount = 1;
    }

    public void addReference() {
        refCount++;
    }

    public boolean removeReference() {
        refCount--;
        return refCount == 0;
    }

    public int getRefCount() {
        return refCount;
    }
}


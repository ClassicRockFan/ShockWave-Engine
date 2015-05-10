package com.ClassicRockFan.ShockWave.engine.physics.response;


public class ResponsePriorities {

    //The larger the integer value is, the more priority it has making it more likely to be accomplished over other responses
    //If 2 Colliders have the same value, whichever one is added to the engine first will take priority

    public static final int BASE_RESPONSE_LEVEL = 0;
    public static final int REFLECT_RESPONSE = 1;
    public static final int IMPULSE_BASED_RESPONSE = 2;
    public static final int PERFECTLY_ELASTIC_RESPONSE = 3;

}

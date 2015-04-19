package com.ClassicRockFan.ShockWave.engine.phyics.physicsComponents;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.phyics.Constants;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsObject;

public class Orbit extends EntityComponent {

    private PhysicsObject body1;
    private PhysicsObject body2;
    private Vector3f gravitationConstant;
    private float body1Mass;
    private float body2Mass;
    private boolean body1Immovable = false;
    private boolean body2Immovable = false;

    //Only add this component to one of the two objects.  Otherwise, it will do this twice and waste calculation time.
    public Orbit(PhysicsObject body1, PhysicsObject body2, float gravityScale) {
        this(body1, body2, Constants.UNIVERSAL_GRAVITATION.mul(gravityScale), true);
    }

    public Orbit(PhysicsObject body1, PhysicsObject body2, Vector3f gravityScale) {
        this(body1, body2, gravityScale, true);
    }

    public Orbit(PhysicsObject body1, PhysicsObject body2, float gravityScale, boolean calcOrbitalVelocityOnOBJ1) {
        this(body1, body2, Constants.UNIVERSAL_GRAVITATION.mul(gravityScale), calcOrbitalVelocityOnOBJ1);
    }

    public Orbit(PhysicsObject orbitingBody, PhysicsObject orbitedBody, Vector3f gravitationConstant,  boolean calcOrbitalVelocityOnOrbiting){
        super("orbitComponent");
        this.body1 = orbitingBody;
        this.body2 = orbitedBody;
        this.gravitationConstant = gravitationConstant;

        //Checks the masses and makes sure that they aren't immovable - not completely accurate, but makes it look better
        if(body1.isImmovable()) {
            body1Mass = 10;
            body1Immovable = true;
        }else
            body1Mass = body1.getMass();

        if(body2.isImmovable()) {
            body2Mass = 10;
            body2Immovable = true;
        }else
            body2Mass = body2.getMass();

        if(calcOrbitalVelocityOnOrbiting)
            calcOrbitalVelocity();
    }



    @Override
    public void update(float delta){
        //Calculate the position of the two objects relative to each other;
        Vector3f direction = (body2.getCollider().getCenter().sub(body1.getCollider().getCenter()));
        direction.printVector("Direction");

        //Check if the direction == 0 in any direction and add a lot to avoid having a very pronounced effect
        // This gets corrected later thanks to the booleans
        boolean correctX = false;
        boolean correctY = false;
        boolean correctZ = false;

        if(direction.getX() == 0.0f) {
            direction.setX(1.0f);
            correctX = true;
        }if(direction.getY() == 0.0f) {
            direction.setY(1.0f);
            correctY = true;
        }if(direction.getZ() == 0.0f) {
            direction.setZ(1.0f);
            correctZ = true;
        }

        Vector3f numerator = (gravitationConstant.mul(body1Mass* body2Mass));
        numerator.printVector("Numerator: ");

        //Get the radius and keep the directional features - the direction comes necessary for determining directions of vectors
        Vector3f radius = direction.div(2);
        Vector3f radiusSquared = radius.mul(radius);


        //Calculate the force of gravity - The same for both bodies
        Vector3f force = numerator.div(radiusSquared);
        force.printVector("Force G: ");

        //Calculate the new accelerations of the bodies
        Vector3f body1Accel = force.div(body1Mass);
        Vector3f body2Accel = force.div(body2Mass);


        //Multiply the accelerations by the radius to get the velocity^2
        Vector3f body1Velocity = body1Accel.mul(radius);
        Vector3f body2Velocity = body2Accel.mul(radius.mul(-1));



        //Square root the components of the velocity to adjust it appropriately
        //-- Also checks if velocity > 0 to make sure that we are going to deal with imaginary numbers (See Vector3f class)
        body1Velocity.set(body1Velocity.sqrt());

        /////////////////////////////////////////////////////////
        body2Velocity.set(body2Velocity.sqrt());


        //Fix the velocities if they had to be corrected
        if(correctX){
            body1Velocity.setX(0);
            body2Velocity.setX(0);
        }
        if(correctY){
            body1Velocity.setY(0);
            body2Velocity.setY(0);
        }
        if(correctZ){
            body1Velocity.setZ(0);
            body2Velocity.setZ(0);
        }

        if(!body1Immovable)
            body1.getVelocity().set(body1Velocity.add(body1.getVelocity()));
        else
            body1.setVelocity(new Vector3f(0,0,0));
        if(!body2Immovable)
            body2.getVelocity().set(body2Velocity.add(body2.getVelocity()));
        else
            body2.setVelocity(new Vector3f(0,0,0));
        body1.getVelocity().printVector("Body1's new Velocity");
        body2.getVelocity().printVector("Body2's new Velocity");

        Vector3f projectedPos1 = body1.getPosition();
        projectedPos1 = projectedPos1.add(body1.getVelocity().mul(delta));
        projectedPos1.printVector("Body 1's projected position");

        Vector3f projectedPos2 = body2.getPosition();
        projectedPos2 = projectedPos2.add(body2.getVelocity().mul(delta));
        projectedPos2.printVector("Body 2's projected position");

    }

    private void calcOrbitalVelocity(){
        body1.getVelocity().printVector("Body 1's Orbital Velocity");
        body2.getVelocity().printVector("Body 2's Orbital Velocity");
        float sigmaMass = body1Mass + body2Mass;

        Vector3f direction = body2.getCollider().getCenter().sub(body1.getCollider().getCenter());

        //Check if the direction == 0 in any direction and add a lot to avoid having a very pronounced effect
        // This gets corrected later thanks to the booleans
        boolean correctX = false;
        boolean correctY = false;
        boolean correctZ = false;

        if(direction.getX() == 0.0f) {
            direction.setX(1.0f);
            correctX = true;
        }if(direction.getY() == 0.0f) {
            direction.setY(1.0f);
            correctY = true;
        }if(direction.getZ() == 0.0f) {
            direction.setZ(1.0f);
            correctZ = true;
        }
        Vector3f numerator = gravitationConstant.mul(sigmaMass);
        Vector3f radius = (direction).div(2);

        Vector3f toBeSquared = numerator.div(radius);
        Vector3f velocity = toBeSquared.sqrt();

        //Fix the velocities if they had to be corrected
        if(correctX){
            velocity.setX(0);
        }
        if(correctY){
            velocity.setY(0);
        }
        if(correctZ){
            velocity.setZ(0);
        }

        //Check the velocities against the direction to make sure it doesn't just go straight toward the object
        Vector3f correctedVelocity = new Vector3f(0,0,0);
        if(direction.getX() != 0)
            correctedVelocity.setX(velocity.getZ());
        if(direction.getY() != 0)
            correctedVelocity.setY(velocity.getZ() * -1);
        if(direction.getZ() != 0)
            correctedVelocity.setZ(velocity.getX());

        body1.setVelocity(correctedVelocity);
        body2.setVelocity(new Vector3f(0,0,0));

        CoreEngine.getConsole().addConsoleText(body1.getVelocity().toString("Body 1's Orbital Velocity"));
        CoreEngine.getConsole().addConsoleText(body2.getVelocity().toString("Body 2's Orbital Velocity"));
        body1.getVelocity().printVector("Body 1's Orbital Velocity");
        body2.getVelocity().printVector("Body 2's Orbital Velocity");
    }

    public Vector3f getGravitationConstant() {
        return gravitationConstant;
    }

    public void setGravitationConstant(Vector3f gravitationConstant) {
        this.gravitationConstant = gravitationConstant;
    }
}

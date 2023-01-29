package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Level.class)
public abstract class Level_ {

	public static volatile SingularAttribute<Level, Integer> positionY;
	public static volatile SetAttribute<Level, Boxes> boxes;
	public static volatile SingularAttribute<Level, Integer> positionZ;
	public static volatile SetAttribute<Level, GoalPosition> goalPositions;
	public static volatile SetAttribute<Level, Walls> walls;
	public static volatile SingularAttribute<Level, Boolean> isunlocked;
	public static volatile SingularAttribute<Level, Long> id;
	public static volatile SingularAttribute<Level, Integer> positionX;
	public static volatile SingularAttribute<Level, Player> player;

	public static final String POSITION_Y = "positionY";
	public static final String BOXES = "boxes";
	public static final String POSITION_Z = "positionZ";
	public static final String GOAL_POSITIONS = "goalPositions";
	public static final String WALLS = "walls";
	public static final String ISUNLOCKED = "isunlocked";
	public static final String ID = "id";
	public static final String POSITION_X = "positionX";
	public static final String PLAYER = "player";

}


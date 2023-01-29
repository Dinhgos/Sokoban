package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Map.class)
public abstract class Map_ {

	public static volatile SetAttribute<Map, Boxes> boxes;
	public static volatile SingularAttribute<Map, Integer> playerPositionY;
	public static volatile SingularAttribute<Map, Integer> playerPositionX;
	public static volatile SetAttribute<Map, GoalPosition> goalPositions;
	public static volatile SetAttribute<Map, Walls> walls;
	public static volatile SetAttribute<Map, Save> saves;
	public static volatile SetAttribute<Map, Score> scores;
	public static volatile SingularAttribute<Map, Long> id;
	public static volatile SingularAttribute<Map, Integer> playerPositionZ;

	public static final String BOXES = "boxes";
	public static final String PLAYER_POSITION_Y = "playerPositionY";
	public static final String PLAYER_POSITION_X = "playerPositionX";
	public static final String GOAL_POSITIONS = "goalPositions";
	public static final String WALLS = "walls";
	public static final String SAVES = "saves";
	public static final String SCORES = "scores";
	public static final String ID = "id";
	public static final String PLAYER_POSITION_Z = "playerPositionZ";

}


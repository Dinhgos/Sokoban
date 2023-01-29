package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Save.class)
public abstract class Save_ {

	public static volatile SetAttribute<Save, Boxes> boxes;
	public static volatile SingularAttribute<Save, Integer> playerPositionY;
	public static volatile SingularAttribute<Save, Integer> playerPositionX;
	public static volatile SingularAttribute<Save, Integer> moves;
	public static volatile SingularAttribute<Save, Long> id;
	public static volatile SingularAttribute<Save, Integer> time;
	public static volatile SingularAttribute<Save, Integer> playerPositionZ;
	public static volatile SingularAttribute<Save, Map> map;
	public static volatile SingularAttribute<Save, Player> player;

	public static final String BOXES = "boxes";
	public static final String PLAYER_POSITION_Y = "playerPositionY";
	public static final String PLAYER_POSITION_X = "playerPositionX";
	public static final String MOVES = "moves";
	public static final String ID = "id";
	public static final String TIME = "time";
	public static final String PLAYER_POSITION_Z = "playerPositionZ";
	public static final String MAP = "map";
	public static final String PLAYER = "player";

}


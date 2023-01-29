package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GoalPosition.class)
public abstract class GoalPosition_ {

	public static volatile SingularAttribute<GoalPosition, Integer> positionY;
	public static volatile SingularAttribute<GoalPosition, Integer> positionZ;
	public static volatile SingularAttribute<GoalPosition, Long> id;
	public static volatile SingularAttribute<GoalPosition, Map> map;
	public static volatile SingularAttribute<GoalPosition, Integer> positionX;

	public static final String POSITION_Y = "positionY";
	public static final String POSITION_Z = "positionZ";
	public static final String ID = "id";
	public static final String MAP = "map";
	public static final String POSITION_X = "positionX";

}


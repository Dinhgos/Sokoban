package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Walls.class)
public abstract class Walls_ {

	public static volatile SingularAttribute<Walls, Integer> positionY;
	public static volatile SingularAttribute<Walls, Integer> positionZ;
	public static volatile SingularAttribute<Walls, Long> id;
	public static volatile SingularAttribute<Walls, Map> map;
	public static volatile SingularAttribute<Walls, Integer> positionX;

	public static final String POSITION_Y = "positionY";
	public static final String POSITION_Z = "positionZ";
	public static final String ID = "id";
	public static final String MAP = "map";
	public static final String POSITION_X = "positionX";

}


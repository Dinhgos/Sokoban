package com.mycompany.myapp.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Score.class)
public abstract class Score_ {

	public static volatile SingularAttribute<Score, Instant> date;
	public static volatile SingularAttribute<Score, Long> id;
	public static volatile SingularAttribute<Score, Integer> value;
	public static volatile SingularAttribute<Score, Map> map;
	public static volatile SingularAttribute<Score, Player> player;

	public static final String DATE = "date";
	public static final String ID = "id";
	public static final String VALUE = "value";
	public static final String MAP = "map";
	public static final String PLAYER = "player";

}


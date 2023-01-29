package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Player.class)
public abstract class Player_ {

	public static volatile SingularAttribute<Player, String> password;
	public static volatile SetAttribute<Player, Save> saves;
	public static volatile SingularAttribute<Player, Integer> level;
	public static volatile SetAttribute<Player, Score> scores;
	public static volatile SingularAttribute<Player, String> name;
	public static volatile SingularAttribute<Player, Long> id;

	public static final String PASSWORD = "password";
	public static final String SAVES = "saves";
	public static final String LEVEL = "level";
	public static final String SCORES = "scores";
	public static final String NAME = "name";
	public static final String ID = "id";

}


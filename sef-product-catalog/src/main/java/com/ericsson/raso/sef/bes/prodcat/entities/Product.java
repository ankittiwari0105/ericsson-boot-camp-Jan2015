package com.ericsson.raso.sef.bes.prodcat.entities;

import java.io.Serializable;

import com.ericsson.raso.sef.ruleengine.Rule;

public abstract class Product implements Serializable {
	private static final long serialVersionUID = 2741629131590884038L;
	
	/**
	 * Unique Identifier to the Resource
	 */
	private String name = null;
	
	/**
	 * identifies who is the owner for this resource. From info security context, this concept will allow a stakeholder to access all of his
	 * own resources as well as resources for all stakeholders chained under the current level.
	 */
	private Owner owner = null;
	
	private Rule criteria = null;
	
	public Product(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Rule getCriteria() {
		return criteria;
	}

	public void setCriteria(Rule criteria) {
		this.criteria = criteria;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((criteria == null) ? 0 : criteria.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (criteria == null) {
			if (other.criteria != null)
				return false;
		} else if (!criteria.equals(other.criteria))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	public abstract String toString();

	
	
		
}

package app.model;

import java.util.Objects;

public abstract class Person implements Identifiable {
	protected String id;
	protected String name;

	public Person(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return Objects.equals(id, person.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return name + " (" + id + ")";
	}
}
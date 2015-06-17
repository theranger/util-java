package ee.risk.util;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The Ranger on 16.06.2015.
 */
public class TestSlugGenerator extends TestCase {

	private List<Entity> entities;
	private SlugGenerator<Entity> generator;

	@Override
	public void setUp() {
		entities = new ArrayList<Entity>();
		generator = new SlugGenerator<Entity>(entities);
	}

	public void testAddPlainToEmpty() {
		entities.clear();
		String slug = generator.getSlug(Entity.EntityWithTitle(1, "Estonia"));
		assertEquals("estonia", slug);
	}

	public void testAddDashedStringToEmpty() {
		entities.clear();
		String slug = generator.getSlug(Entity.EntityWithTitle(1, "South-Korea"));
		assertEquals("south-korea", slug);
	}

	public void testAddNumericToEmpty() {
		entities.clear();
		String slug = generator.getSlug(Entity.EntityWithTitle(1, "10"));
		assertEquals("10", slug);
	}

	public void testAddDashedNumericToEmpty() {
		entities.clear();
		String slug = generator.getSlug(Entity.EntityWithTitle(1, "-10"));
		assertEquals("-10", slug);
	}

	public void testAddDashedToEmpty() {
		entities.clear();
		String slug = generator.getSlug(Entity.EntityWithTitle(1, "-"));
		assertEquals("-", slug);
	}

	public void testAddDashedMixedToEmpty() {
		entities.clear();
		String slug = generator.getSlug(Entity.EntityWithTitle(1, "South-Korea 10"));
		assertEquals("south-korea-10", slug);
	}

	public void testAddPlainUnique() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "latvia"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "Estonia"));
		assertEquals("estonia", slug);
	}

	public void testAddDashedUnique() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "latvia"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "South-Korea"));
		assertEquals("south-korea", slug);
	}

	public void testAddNumericUnique() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "latvia"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "-10"));
		assertEquals("-10", slug);
	}

	public void testAddDashedMixedUnique() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "latvia"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "South-Korea 10"));
		assertEquals("south-korea-10", slug);
	}

	public void testAddPlainCollision() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "latvia"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "Latvia"));
		assertEquals("latvia-1", slug);
	}

	public void testAddDashedCollision() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "south-korea"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "South-Korea"));
		assertEquals("south-korea-1", slug);

		entities.add(Entity.EntityWithName(2, "south-korea-1"));
		slug = generator.getSlug(Entity.EntityWithTitle(3, "South-Korea"));
		assertEquals("south-korea-2", slug);
	}

	public void testAddDashCollision() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "-"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "-"));
		assertEquals("--1", slug);
	}

	public void testAddDashedNumericCollision() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "-10"));
		String slug = generator.getSlug(Entity.EntityWithTitle(2, "-10"));
		assertEquals("-10-1", slug);
	}

	public void testUpdateNoChange() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "estonia"));
		entities.add(Entity.EntityWithName(2, "estonia-1"));
		entities.add(Entity.EntityWithName(3, "south-korea"));

		String slug = generator.getSlug(Entity.EntityWithTitle(2, "Estonia"));
		assertEquals("estonia-1", slug);

		slug = generator.getSlug(Entity.EntityWithTitle(1, "Estonia"));
		assertEquals("estonia", slug);

		slug = generator.getSlug(Entity.EntityWithTitle(3, "South-Korea"));
		assertEquals("south-korea", slug);
	}

	public void testUpdateCollision() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "estonia"));
		entities.add(Entity.EntityWithName(2, "estonia-1"));
		entities.add(Entity.EntityWithName(3, "south-korea"));

		String slug = generator.getSlug(Entity.EntityWithTitle(3, "Estonia"));
		assertEquals("estonia-2", slug);

		slug = generator.getSlug(Entity.EntityWithTitle(2, "Estonia"));
		assertEquals("estonia-1", slug);

		slug = generator.getSlug(Entity.EntityWithTitle(1, "Estonia"));
		assertEquals("estonia", slug);
	}

	public void testUpdateUnique() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "estonia"));
		entities.add(Entity.EntityWithName(2, "estonia-1"));
		entities.add(Entity.EntityWithName(3, "south-korea"));

		String slug = generator.getSlug(Entity.EntityWithTitle(2, "Latvia"));
		assertEquals("latvia", slug);
	}

	public void testUpdateAlternatingFromLast() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "estonia"));
		entities.add(Entity.EntityWithName(2, "estonia-1"));
		entities.add(Entity.EntityWithName(3, "south-korea"));

		String slug = generator.getSlug(Entity.EntityWithTitle(2, "South-Korea"));
		assertEquals("south-korea-1", slug);
		entities.set(1, Entity.EntityWithName(2, slug));

		slug = generator.getSlug(Entity.EntityWithTitle(3, "Estonia"));
		assertEquals("estonia-1", slug);
		entities.set(2, Entity.EntityWithName(3, slug));

		slug = generator.getSlug(Entity.EntityWithTitle(1, "South-Korea"));
		assertEquals("south-korea-2", slug);
	}

	public void testUpdateAlternatingMiddle() {
		entities.clear();
		entities.add(Entity.EntityWithName(1, "estonia"));
		entities.add(Entity.EntityWithName(2, "estonia-1"));
		entities.add(Entity.EntityWithName(3, "south-korea"));

		String slug = generator.getSlug(Entity.EntityWithTitle(1, "South-Korea"));
		assertEquals("south-korea-1", slug);
		entities.set(0, Entity.EntityWithName(1, slug));

		slug = generator.getSlug(Entity.EntityWithTitle(3, "Estonia"));
		assertEquals("estonia-2", slug);
		entities.set(2, Entity.EntityWithName(3, slug));

		slug = generator.getSlug(Entity.EntityWithTitle(1, "South-Korea"));
		assertEquals("south-korea-1", slug);
	}
}

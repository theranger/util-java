package ee.risk.util;

import java.util.List;

/**
 * Provides helper to generate user-friendly names (slugs) for objects.
 * Existing slug will be retrieved from Name field.
 * New slug will be generated from string stored in Title field.
 *
 * @param <T> Entity type that slug generator runs on. Entities must implement Sluggable interface.
 */
public class SlugGenerator<T extends Sluggable> {

	private List<T> names;
	private int id;		// Numeric part from last removeSuffix call
	private static final String SEPARATOR = "-";

	/**
	 * @param names List of existing objects to validate new slug against
	 */
	public SlugGenerator(List<T> names) {
		this.names = names;
	}

	/**
	 * Get the user-friendly name (slug) from object's Title field
	 * @param obj Object for which to get the slug
	 * @return 	Returns new unique slug or old slug, if no update required.
	 * 			Returns null if new slug should be generated but Title field is null or empty
	 */
	public String getSlug(T obj) {
		String slug = nameFromTitle(obj);
		if(names.isEmpty()) return slug;

		int maxID = -1;
		for(T t : names) {
			String prefix = getPrefix(t.getName());

			// We have found ourselves. If slug has not changed, get out fast
			if(t.getUuid() == obj.getUuid() && prefix.equalsIgnoreCase(slug)) return t.getName();

			// Collision found, increment max number
			if(prefix.equalsIgnoreCase(slug) && id > maxID) maxID = id;
		}

		if(maxID > -1) return slug + SEPARATOR + ++maxID;
		return slug;
	}

	private String getPrefix(String name) {
		id = 0;
		int pos = name.lastIndexOf(SEPARATOR);
		if(pos < 1) return name;		// No dash found or dash found but without prefix

		try {
			id = Integer.valueOf(name.substring(pos + 1));
			return name.substring(0, pos);
		}
		catch(IllegalArgumentException ex) {
			// Suffix is not numeric
			return name;
		}
	}

	private String nameFromTitle(T obj) {
		if(obj.getTitle() == null || obj.getTitle().isEmpty()) return null;
		return obj.getTitle().replace(" ", SEPARATOR).toLowerCase();
	}
}

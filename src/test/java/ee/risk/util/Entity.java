package ee.risk.util;

/**
 * Created by The Ranger on 16.06.2015.
 */

class Entity implements Sluggable {

	private int uuid;
	private String name;
	private String title;

	public static Entity EntityWithTitle(int uuid, String title) {
		Entity instance = new Entity();
		instance.uuid = uuid;
		instance.title = title;
		return instance;
	}

	public static Entity EntityWithName(int uuid, String name) {
		Entity instance = new Entity();
		instance.uuid = uuid;
		instance.name = name;
		return instance;
	}

	public int getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}
}

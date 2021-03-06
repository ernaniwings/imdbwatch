package se.chalmers.watchmetest.model;

import junit.framework.TestCase;
import se.chalmers.watchme.model.Tag;

/**
 * This class tests methods in se.chalmers.watchme.model.Tag
 * @author mattiashenriksson
 *
 */

public class TagTest extends TestCase {
		
	public TagTest() {
		super();
	}

	public void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test if it's possible to return a tag's id.
	 */
	public void testGetId() {
		Tag action = new Tag("action");
		action.setId(1);
		assertTrue(action.getId() == 1);
	}
	
	/**
	 * Test if it's possible to return a tag's name.
	 */
	public void testGetName() {
		Tag action = new Tag("action");
		assertTrue(action.getName().equals("action"));
	}
	
	/**
	 * Test if it's possible to add a name to a tag and return it as a slug
	 * (lower case only).
	 */
	public void testGetSlug() {
		Tag action = new Tag("AcTiOn");
		assertTrue(action.getSlug().equals("action"));
	}
	
	public void testEquals() {
		
		Tag action = new Tag("action");
		Tag compareObject = null; 
		assertFalse(action.equals(compareObject));
		
		// A class should not be equal to it's subclasses
		class EpicTag extends Tag {
			public EpicTag(String name) {
				super(name);
			}
		}		
		compareObject = new EpicTag("action");
		assertFalse(action.equals(compareObject));
		
		compareObject = new Tag("drama");
		assertFalse(action.equals(compareObject));

		compareObject = new Tag("AcTiOn");
		assertTrue(action.equals(compareObject));
		
	}
		

}

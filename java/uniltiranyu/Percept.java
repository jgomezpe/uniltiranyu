package uniltiranyu;

import java.util.HashMap;

/**
 * <p>Title: Percept</p>
 *
 * <p>Description: Abstract definition of a perception</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class Percept {
	protected HashMap<String,Object> table = new HashMap<String,Object>();
	
	public Percept() {}
	
	public Object get( String code ){ try{ return table.get(code); }catch(Exception e){ return null; } }

	public void set( String key, Object value ){ table.put(key, value); }
}
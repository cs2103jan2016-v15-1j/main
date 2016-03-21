package cs2103.v15_1j.jimjim.model;

public class FloatingTask extends Task implements Comparable<FloatingTask> {
	public FloatingTask(String name) {
	    super(name);
	}
	
	@Override
	public int compareTo(FloatingTask o) {
		String name = this.getName().toLowerCase();
		String otherName = o.getName().toLowerCase();
		
		return name.compareTo(otherName);
	}
}

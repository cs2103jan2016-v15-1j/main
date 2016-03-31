package cs2103.v15_1j.jimjim.model;

public class FloatingTask extends TaskEvent implements Comparable<FloatingTask> {
	public FloatingTask(String name) {
	    super(name);
	}
	
	public FloatingTask(FloatingTask other) {
		this(other.getName());
	}
	
	@Override
	public int compareTo(FloatingTask o) {
		String name = this.getName().toLowerCase();
		String otherName = o.getName().toLowerCase();
		
		return name.compareTo(otherName);
	}
	
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof FloatingTask)) {
			return false;
		}
		FloatingTask other = (FloatingTask) t;
		return this.getName().equals(other.getName());
	}
}

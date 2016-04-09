package cs2103.v15_1j.jolt.model;

public class FloatingTask extends TaskEvent implements Comparable<FloatingTask> {
	public FloatingTask(String name) {
		super(name);
	}

	/* @@author A0124995R */
	public FloatingTask(FloatingTask other) {
		this(other.getName());
	}

	// @@author A0139963N
	@Override
	public int compareTo(FloatingTask o) {
		String name = this.getName().toLowerCase();
		String otherName = o.getName().toLowerCase();

		return name.compareTo(otherName);
	}

	/* @@author A0124995R */
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof FloatingTask)) {
			return false;
		}
		FloatingTask other = (FloatingTask) t;
		return this.getName().equals(other.getName());
	}
}

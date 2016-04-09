package cs2103.v15_1j.jolt.controller;

/* @@author A0124995R */
import java.util.HashMap;
import java.util.Map;

public class Configuration {
	public String savePath = "save_data.json"; // default configurations
	public Map<String, Integer> aliases = new HashMap<>();

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof Configuration)) {
			return false;
		}
		Configuration other = (Configuration) t;
		return this.savePath.equals(other.savePath) && this.aliases.equals(other.aliases);
	}
}

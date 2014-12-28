package earpitch.widget.keyboard;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import earpitch.Pitch;

public class PitchMapper<T> {
	public static class Builder<T> {
		private Map<Character, Function<Integer, T>> registry = new HashMap<>();
		private Function<Pitch, ? extends Comparable<?>> classifier;

		public PitchMapper<T> compile(String sequence) {
			if (classifier == null) {
				classifier = (p) -> p.ordinal();
			}

			return new PitchMapper<T>(toFactorySequence(sequence), groupPitches());
		}

		public Builder<T> groupBy(Function<Pitch, ? extends Comparable<?>> classifier) {
			this.classifier = classifier;

			return this;
		}

		public Builder<T> register(char symbol, Function<Integer, T> factory) {
			registry.put(symbol, factory);
			return this;
		}

		private Collection<List<Pitch>> groupPitches() {
			Map<? extends Comparable<?>, List<Pitch>> result;

			result = new TreeMap<>(Arrays.stream(Pitch.values()).collect(Collectors.groupingBy(classifier)));

			return result.values();
		}

		private List<Function<Integer, T>> toFactorySequence(String sequence) {
			List<Function<Integer, T>> result = new LinkedList<>();
			for (char each : sequence.toCharArray()) {
				Function<Integer, T> factory = registry.get(each);

				if (factory == null) {
					throw new IllegalArgumentException("No factory for symbol " + each);
				}

				result.add(factory);
			}
			return result;
		}
	}

	private List<Function<Integer, T>> factories;
	private Collection<List<Pitch>> pitchGroups;

	private PitchMapper(List<Function<Integer, T>> factories, Collection<List<Pitch>> pitchGroups) {
		this.factories = factories;
		this.pitchGroups = pitchGroups;
	}

	public List<T> map(BiFunction<T, List<Pitch>, T> mapper) {
		Iterator<Function<Integer, T>> iterator = factories.iterator();

		int idx = 0;
		List<T> result = new LinkedList<>();
		for (List<Pitch> each : pitchGroups) {
			if (!iterator.hasNext()) {
				iterator = factories.iterator();
			}

			Function<Integer, T> factory = iterator.next();
			T subject = factory.apply(idx++);
			result.add(mapper.apply(subject, each));
		}

		return result;
	}
}

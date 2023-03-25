package cankaya_c2171651.ceng520.homework1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Hw1Q1Q2 {

	public static void main(String[] args) throws IOException {

		Map<Character, Double> engFreqTable = getEngFreqTable();

		Set<Character> alphabet = engFreqTable.keySet();

		// for if using custom input file
		if (args.length == 1) {

			makeExperiment(args[0], alphabet);
		} else {

			// experimental files
			makeExperiment("ceng520/homework1/data/loremipsum5paragraph.txt", alphabet);
			makeExperiment("ceng520/homework1/data/ayreonTheoryOfEverythingLyrics.txt", alphabet);
			makeExperiment("ceng520/homework1/data/pfWallAll.txt", alphabet);
		}
	}

	private static Map<Character, Double> getEngFreqTable() {

		Map<Character, Double> engFreqTable = new HashMap<>();

		engFreqTable.put('e', 0.111607d);
		engFreqTable.put('a', 0.084966d);
		engFreqTable.put('r', 0.075809d);
		engFreqTable.put('i', 0.075448d);
		engFreqTable.put('o', 0.071635d);
		engFreqTable.put('t', 0.069509d);
		engFreqTable.put('n', 0.066544d);
		engFreqTable.put('s', 0.057351d);
		engFreqTable.put('l', 0.054893d);
		engFreqTable.put('c', 0.045388d);
		engFreqTable.put('u', 0.036308d);
		engFreqTable.put('d', 0.033844d);
		engFreqTable.put('p', 0.031671d);
		engFreqTable.put('m', 0.030129d);
		engFreqTable.put('h', 0.030034d);
		engFreqTable.put('g', 0.024705d);
		engFreqTable.put('b', 0.020720d);
		engFreqTable.put('f', 0.018121d);
		engFreqTable.put('y', 0.017779d);
		engFreqTable.put('w', 0.012899d);
		engFreqTable.put('k', 0.011016d);
		engFreqTable.put('v', 0.010074d);
		engFreqTable.put('x', 0.002902d);
		engFreqTable.put('z', 0.002722d);
		engFreqTable.put('j', 0.001965d);
		engFreqTable.put('q', 0.001962d);

		return engFreqTable;
	}

	private static void makeExperiment(String filePath, Set<Character> alphabet) throws IOException {

		File file = new File(filePath);

		File brotli = new File(filePath.substring(0, filePath.indexOf('.')) + ".brotli");

		Map<Character, Double> freqTable = createFrequencyTable(file, alphabet);
		printFreqTable(freqTable);

		double ent = calculateEngCharsEntropy(freqTable);
		System.out.println("Entropy is: " + ent);

		double bortliEnt = calculateUniqueEntropy(brotli);
		System.out.println("Brotly Entropy is: " + bortliEnt);
	}

	private static double calculateUniqueEntropy(File brotli) throws IOException {

		Map<Character, Double> frequencyMap = new HashMap<>();

		double sum = 0;

		double ent = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(brotli))) {

			char read = (char) br.read();

			while (read != -1 && read != 65535) {

				if (frequencyMap.containsKey(read)) {

					frequencyMap.put(read, frequencyMap.get(read) + 1);

				} else {

					frequencyMap.put(read, 1d);
				}

				sum++;
				read = (char) br.read();

			}
		}

		for (Character key : frequencyMap.keySet()) {

			double unit = frequencyMap.get(key) / sum;
			ent += unit * log2(1 / unit);
		}

		return ent;
	}

	@SuppressWarnings("unused")
	private static void printFreqTable(Map<Character, Double> frequencyMap) {

		System.out.println("Frequency Results:");

		Object[] alphabet = frequencyMap.keySet().toArray();

		for (int i = 0; i <= alphabet.length / 2; i++) {

			if (i < alphabet.length / 2) {

				System.out.println(String.format("%c: %3.3f\t%c: %3.3f", alphabet[i], frequencyMap.get(alphabet[i]),
						alphabet[i + alphabet.length / 2], frequencyMap.get(alphabet[i + alphabet.length / 2])));
			} else {

				System.out.println(String.format("%c: %3.3f ", alphabet[i], frequencyMap.get(alphabet[i])));
			}
		}
	}

	private static double calculateEngCharsEntropy(Map<Character, Double> freqTable) {

		double h = 0;

		for (char key : freqTable.keySet()) {

			double unit = freqTable.get(key);

			if (unit != 0) {

				h += unit * log2(1 / unit);
			}
		}

		return h;
	}

	private static double log2(double d) {

		return Math.log(d) / Math.log(2);
	}

	private static Map<Character, Double> createFrequencyTable(File file, Set<Character> alphabet) throws IOException {

		Map<Character, Double> frequencyMap = new HashMap<>();

		// init map
		for (char c : alphabet) {

			frequencyMap.put(c, 0d);
		}

		System.out.println("Input file(" + file + ") is parsing...");

		double sum = 0;

		BufferedReader br = new BufferedReader(new FileReader(file));

		char read = (char) br.read();

		while (read != -1 && read != 65535) {

			// lowercase conversion
			if (64 < read && read < 91) {

				read += 32;
			}

			if (frequencyMap.containsKey(read)) {

				frequencyMap.put(read, frequencyMap.get(read) + 1);

				sum++;
			}

			read = (char) br.read();
		}

		for (char c : alphabet) {

			double freq = frequencyMap.get(c) / sum;
			frequencyMap.put(c, freq);
		}

		return frequencyMap;
	}
}
public class PercolationStats {
	private double tTimes[];

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		validate(N, T);
		tTimes = new double[T];

		for (int j = 0; j < T; j++) {
			Percolation p = new Percolation(N);
			while (!p.percolates()) {
				int x = StdRandom.uniform(1, N + 1);
				int y = StdRandom.uniform(1, N + 1);
				System.out.println("open(" + x + "," + y + ")");
				p.open(x, y);
			}
			double totalSites = N * N;
			tTimes[j] = p.count() / totalSites;
			System.out
					.println(j
							+ "----------------------------------------percolation count: "
							+ p.count());
		}
	}

	public void validate(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(tTimes);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(tTimes);
	}

	public static void main(String[] args) {
		int N = 200;
		int T = 100;
		PercolationStats ps = new PercolationStats(N, T);
		double mean = ps.mean();
		System.out.println("mean  =" + mean);
		double stddev = ps.stddev();
		System.out.println("stddev  =" + stddev);
		double d1 = mean - ((1.96 * stddev)) / Math.sqrt(T);
		double d2 = mean + ((1.96 * stddev)) / Math.sqrt(T);
		System.out.println("95% confidence interval  =" + d1 + " , " + d2);
	}
}
import java.awt.Font;

public class PercolationVisualizer {

	// delay in miliseconds (controls animation speed)
	private static final int DELAY = 100;

	// draw N-by-N percolation system
	public static void draw(Percolation perc, int N) {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setXscale(0, N);
		StdDraw.setYscale(0, N);
		StdDraw.filledSquare(N / 2.0, N / 2.0, N / 2.0);

		// draw N-by-N grid
		int opened = 0;
		for (int row = 1; row <= N; row++) {
			for (int col = 1; col <= N; col++) {
				if (perc.isFull(row, col)) {
					StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
					opened++;
				} else if (perc.isOpen(row, col)) {
					StdDraw.setPenColor(StdDraw.WHITE);
					opened++;
				} else
					StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
			}
		}

		// write status text
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(.25 * N, -N * .025, opened + " open sites");
		if (perc.percolates()) {
			StdDraw.text(.75 * N, -N * .025, "percolates");
			System.out.println("percolates count:" + perc.count());
			return;
		} else
			StdDraw.text(.75 * N, -N * .025, "does not percolate");

	}

	public static void main(String[] args) {
		int N = 200;
		StdDraw.show(0);

		Percolation perc = new Percolation(N);
		draw(perc, N);
		while (!perc.percolates()) {
			int x = StdRandom.uniform(1, N + 1);
			int y = StdRandom.uniform(1, N + 1);
			perc.open(x, y);
			draw(perc, N);
			StdDraw.show(DELAY);
			System.out.println("x:" + x + "  y:" + y);
		}
		System.out.println("Program end");
	}

	public static void main(String[] args, int ij) {
		In in = new In(args[0]); // input file
		int N = in.readInt(); // N-by-N percolation system

		// turn on animation mode
		StdDraw.show(0);

		// repeatedly read in sites to open and draw resulting system
		Percolation perc = new Percolation(N);
		draw(perc, N);
		StdDraw.show(DELAY);
		while (!in.isEmpty()) {
			int i = in.readInt();
			int j = in.readInt();
			perc.open(i, j);
			draw(perc, N);
			StdDraw.show(DELAY);
		}
	}

}
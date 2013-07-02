public class Percolation {

	int N;
	static WeightedQuickUnionUF uf;
	int vUpperLayer = 0;
	int VLowerLayer;
	boolean sites[];
	int openSites;

	public Percolation(int N) {
		this.N = N;
		int arraySize = N * N + 2;
		uf = new WeightedQuickUnionUF(arraySize);
		sites = new boolean[arraySize];
		VLowerLayer = arraySize - 1;

		connectFirstRowToVirtualUpper();
		connectLastRowToVirtualLower();
	}

	private void connectFirstRowToVirtualUpper() {
		for (int i = 1; i <= N; i++)
			uf.union(vUpperLayer, i);
	}

	private void connectLastRowToVirtualLower() {
		for (int i = VLowerLayer; i >= VLowerLayer - N; i--)
			uf.union(VLowerLayer, i);
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		validate(i, j);
		int k = xyTo1D(i, j);
		if (!sites[k]) {
			openSites++;
			sites[k] = true;

			if (i > 1) {
				if (isOpen(i - 1, j)) {
					int l = xyTo1D(i - 1, j);
					uf.union(l, k);
				}
			}

			if (i < N) {
				if (isOpen(i + 1, j)) {
					int r = xyTo1D(i + 1, j);
					uf.union(r, k);
				}
			}

			if (j > 1) {
				if (isOpen(i, j - 1)) {
					int u = xyTo1D(i, j - 1);
					uf.union(u, k);
				}
			}

			if (j < N) {
				if (isOpen(i, j + 1)) {
					int d = xyTo1D(i, j + 1);
					uf.union(d, k);
				}
			}
		}
	}

	public boolean isFull(int i, int j) {
		validate(i, j);
		return isOpen(i, j) && uf.connected(vUpperLayer, xyTo1D(i, j));
	}

	protected int xyTo1D(int i, int j) {
		validate(i, j);
		return (i - 1) * N + j;
	}

	public boolean isOpen(int i, int j) {
		validate(i, j);
		int k = xyTo1D(i, j);
		return sites[k];
	}

	public int count() {
		return openSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return uf.connected(vUpperLayer, VLowerLayer);
	}

	public void validate(int i, int j) {
		if (i > N || j > N || i < 1 || j < 1) {
			throw new IndexOutOfBoundsException("invalid value::" + i + ":" + j);
		}
	}

	public static void main(String[] args) {
		int N = StdIn.readInt();
		Percolation pc = new Percolation(N);

		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			pc.open(p, q);
			if (pc.percolates())
				continue;
			StdOut.println(p + " " + q);
		}
		StdOut.println(uf.count() + " components");
	}
}
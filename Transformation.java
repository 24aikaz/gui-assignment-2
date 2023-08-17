
public class Transformation {
	
	public static double[][] multiplyMatrix(double[][] A, double[][] B) {
		
		double[][] result = new double[A.length][B[0].length];
		
		for (int i=0; i<A.length; i++) {
			for (int j=0; j<B[0].length; j++) {
				result[i][j] = 0;
				for (int k=0; k<A[0].length; k++) {
					result[i][j] += (A[i][k] * B[k][j]);
				}
			}
		}
		return result;
		
	}
	
	public static double[][] initPointsMatrix(int x,int y) {
		
		double[][] points = new double[3][1];
		
		points[0][0]=x;
		points[1][0]=y;
		points[2][0]=1;
		
		return points;
		
	}
	
	public static void display(double[][] points) {
        System.out.println("Matrix: ");
        for(double[] row : points) {
            for (double column : row) {
                System.out.print(column + "\t");
            }
            System.out.println();
        }
    }
	
}

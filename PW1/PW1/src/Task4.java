import java.lang.reflect.Array;

public class Task4 {
    public static Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }

    public static Object createMatrix(Class<?> type, int rows, int cols) {
        return Array.newInstance(type, rows, cols);
    }

    public static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = Array.getLength(oldArray);
        Class<?> elementType = oldArray.getClass().getComponentType();
        Object newArray = Array.newInstance(elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray;
    }

    public static Object resizeMatrix(Object oldMatrix, int newRows, int newCols) {
        int oldRows = Array.getLength(oldMatrix);
        int oldCols = oldRows > 0 ? Array.getLength(Array.get(oldMatrix, 0)) : 0;
        Class<?> elementType = oldMatrix.getClass().getComponentType().getComponentType();
        Object newMatrix = Array.newInstance(elementType, newRows, newCols);
        int preserveRows = Math.min(oldRows, newRows);
        int preserveCols = Math.min(oldCols, newCols);
        for (int i = 0; i < preserveRows; i++) {
            System.arraycopy(Array.get(oldMatrix, i), 0, Array.get(newMatrix, i), 0, preserveCols);
        }
        return newMatrix;
    }

    public static String arrayToString(Object array) {
        StringBuilder sb = new StringBuilder();
        int length = Array.getLength(array);
        sb.append(array.getClass().getComponentType().getName()).append("[").append(length).append("] = {");
        for (int i = 0; i < length; i++) {
            sb.append(Array.get(array, i));
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static String matrixToString(Object matrix) {
        StringBuilder sb = new StringBuilder();
        int rows = Array.getLength(matrix);
        int cols = rows > 0 ? Array.getLength(Array.get(matrix, 0)) : 0;
        sb.append(matrix.getClass().getComponentType().getComponentType().getName()).append("[").append(rows).append("][").append(cols).append("] = {");
        for (int i = 0; i < rows; i++) {
            sb.append("{");
            for (int j = 0; j < cols; j++) {
                sb.append(Array.get(Array.get(matrix, i), j));
                if (j < cols - 1) {
                    sb.append(", ");
                }
            }
            sb.append("}");
            if (i < rows - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        Object array = Task4.createArray(Integer.class, 5);
        for (int i = 0; i < 5; i++) {
            Array.set(array, i, i * 2);
        }
        System.out.println(Task4.arrayToString(array));

        Object resizedArray = Task4.resizeArray(array, 8);
        System.out.println(Task4.arrayToString(resizedArray));

        Object matrix = Task4.createMatrix(Double.class, 3, 3);
        double value = 0.5;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Array.set(Array.get(matrix, i), j, value);
                value += 0.5;
            }
        }
        System.out.println(Task4.matrixToString(matrix));

        Object resizedMatrix = Task4.resizeMatrix(matrix, 4, 4);
        System.out.println(Task4.matrixToString(resizedMatrix));
    }
}

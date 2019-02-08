public class BinarySearch {

    /**
     * <p>Searches searchElement in arr and returns the index where it's found or -1 if not found.</p>
     *
     * <p>The given array must be already sorted otherwise the results are undefined.</p>
     *
     * <p>If there a duplicate elements, there is no guarantee which one will be found.</p>
     *
     * <p>This implementation avoids a possible overflow while calculating the index for the middle element.
     * @see <a href="https://research.googleblog.com/2006/06/extra-extra-read-all-about-it-nearly.html">Extra, Extra -
     * Read All About It: Nearly All Binary Searches and Mergesorts are Broken</a></p>
     *
     * @param arr
     * @param searchElement
     * @return
     */

    // array: array de busqueda
    // searchElement: elemento a buscar
    // valor de retorno: indice en el array si hemos encontrado el elemento
    //                   -1 si no hemos encontrado el elemento
    public static int binarySearch(int[] arr, int searchElement) {
        // left y right son los extremos que vamos a buscar
        int left = 0;
        int right = arr.length - 1;
        // solución iterativa: en cada vuelta del bucle vemos cual son los nuevos valores de right y left.
        while (left <= right) {
            // calculamos el elemento del medio. Hacemos división entera previamente
            int mid = left + (right - left) / 2; // identical to (left + right) / 2 but avoids overflow


            // Si encontramos el elemento en el medio ya está!
            if (arr[mid] == searchElement) { // Element found
                return mid;
            }
            // En caso de que el elemento sea mayor subimos el indice izquierdo
            if (arr[mid] < searchElement) { // Look in right half
                left = mid + 1;
            // En el otro caso subimos el indice derecho
            } else { // Look in left half
                right = mid - 1;
            }
        }

        return -1;        // Element not found
    }

    public static void main(String[] args) {

        int[] arr = new int[] {1, 5, 35, 112, 258, 324};

        int[] searchArr = new int[] {1, 35, 112, 324, 67};
        int pos;
        for (int i = 0; i < searchArr.length; i++) {
            pos = binarySearch(arr, searchArr[i]);  //search key and get poistion
            if (pos >= 0) { 
                System.out.println(searchArr[i] + "-> found at index : " + pos);
            } else {
                System.out.println(searchArr[i] + "-> not found");
            }
        }
    }
}

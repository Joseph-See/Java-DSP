import java.io.*;

/*
*
*  Contains methods for both a recursive moving average filter and a standard moving average filter.
*  Outputs the results as .dat files
*
 */
public class Main {

    public static void main(String[] args) {

        // Creating input and output signal arrays
        Signals inputSignal = new Signals();
        double[] Output_MA = new double [inputSignal.InputSignal_f32_1kHz_15kHz.length];
        double[] Output_RMA = new double [inputSignal.InputSignal_f32_1kHz_15kHz.length];


        // Call the moving average filter method
        moving_average(inputSignal.InputSignal_f32_1kHz_15kHz,
                Output_MA,
                inputSignal.InputSignal_f32_1kHz_15kHz.length,
                11);

        recursive_moving_average(inputSignal.InputSignal_f32_1kHz_15kHz,
                Output_RMA,
                inputSignal.InputSignal_f32_1kHz_15kHz.length,
                11);

        // Write the outputs to two separate .dat files
        try {
            write("moving_average.dat", Output_MA);
            write("recursive_moving_average.dat", Output_RMA);
            write("input_signal.dat", inputSignal.InputSignal_f32_1kHz_15kHz);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *
    *  Moving average filter method. Takes as arguments an input signal array, an output array, the signal length and
    * the number of points for the filter.
    *
     */
    public static void moving_average(double[] sig_src_arr, double[] sig_out_arr, int signal_length, int filter_pts) {
        int i, j;

        for (i = (int)Math.floor(filter_pts/2); i < (signal_length - (int)Math.floor(filter_pts/2)) - 1; i++) {
            sig_out_arr[i] = 0;
            for (j = (int)-Math.floor(filter_pts/2); j < (int)Math.floor(filter_pts/2); j++) {
                sig_out_arr[i] = sig_out_arr[i] + sig_src_arr[i+j];
            }
            sig_out_arr[i] = sig_out_arr[i]/filter_pts;
        }
    }

    /*
    * Recursive moving average filter
     */

    public static void recursive_moving_average(double[] sig_src_arr, double[] sig_out_arr, int signal_length, int filter_pts) {

        int i;
        double acc = 0.0;
        for (i = 0; i < filter_pts -1; i++) {
            acc = acc + sig_src_arr[i];
        }

        sig_out_arr[(filter_pts - 1 )/ 2] = acc / filter_pts;
        for (i = (int)Math.ceil(filter_pts/2); i < signal_length - (int)Math.ceil(filter_pts/2) - 1; i++) {
            acc = acc + sig_src_arr[i + ((int)Math.ceil(filter_pts - 1) / 2)] - sig_src_arr[i - (int)Math.ceil(filter_pts/2)];

            sig_out_arr[i] = acc/filter_pts;
        }
    }

    /*
    *
    *  Function for writing results to files that can then be plotted.
    *
     */
    public static void write(String filename, double[] x) throws IOException {

        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < x.length; i++) {
            outputWriter.write(Double.toString(x[i]));
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }
}
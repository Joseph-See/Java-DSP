import java.io.*;

/*
*
*  A moving average filter that takes an input array and outputs a filtered array.
*  Writes the input and output to two separate .dat files.
*  The moving average filter is useful for smoothing of noisy signals.
*
 */
public class Main {

    public static void main(String[] args) {

        // Creating input and output signal arrays
        Signals inputSignal = new Signals();
        double[] OutputSignal = new double [inputSignal.InputSignal_f32_1kHz_15kHz.length];

        // Call the moving average filter method
        moving_average(inputSignal.InputSignal_f32_1kHz_15kHz,
                OutputSignal,
                inputSignal.InputSignal_f32_1kHz_15kHz.length,
                11);

        // Write the outputs to two separate .dat files
        try {
            write("output_signal.dat", OutputSignal);
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